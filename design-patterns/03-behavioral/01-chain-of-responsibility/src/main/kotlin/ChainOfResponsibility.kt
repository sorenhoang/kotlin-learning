package behavioral.chainofresponsibility

// ========================================================
// 1. THE REQUEST OBJECT
// ========================================================

class LoginRequest(
    val username: String,
    val role: String,
    val requestCountPerSecond: Int,
)

// ========================================================
// 2. THE BASE HANDLER (Abstract Class)
// ========================================================
abstract class Handler {
    private var nextHandler: Handler? = null

    // Helper method to link handlers together to form a chain
    fun setNextHandler(nextHandler: Handler): Handler {
        this.nextHandler = nextHandler
        return nextHandler
    }

    // Template method to process the request or pass it forward
    fun handle(request: LoginRequest): Boolean {
        if (!process(request)) {
            return false
        }

        return nextHandler?.handle(request) ?: true
    }

    protected abstract fun process(loginRequest: LoginRequest): Boolean
}

class CredentialHandler : Handler() {
    override fun process(loginRequest: LoginRequest): Boolean {
        if (loginRequest.username.isBlank()) {
            println("❌ CredentialHandler: Username cannot be blank!")
            return false
        }
        println("✅ CredentialHandler: Credentials format checked successfully.")
        return true
    }
}

// Step 2: Check rate limit / spamming
class ThrottlingHandler : Handler() {
    override fun process(loginRequest: LoginRequest): Boolean {
        if (loginRequest.requestCountPerSecond > 5) {
            println("❌ ThrottlingHandler: Too many requests! Critical spam detected.")
            return false
        }
        println("✅ ThrottlingHandler: Request rate within safe limit.")
        return true
    }
}

// Step 3: Check role permissions
class RoleHandler : Handler() {
    override fun process(loginRequest: LoginRequest): Boolean {
        if (loginRequest.role != "ADMIN") {
            println("❌ RoleHandler: Access Denied! Only ADMIN role can access this resource.")
            return false
        }
        println("✅ RoleHandler: Access Granted for ADMIN role.")
        return true
    }
}

fun main() {
    println("Hello Chain Of Responsibility Design Pattern")

    // 1. Build the chain of security handlers
    val credentialFilter = CredentialHandler()
    val throttlingFilter = ThrottlingHandler()
    val roleFilter = RoleHandler()

    // Link them together: Credential -> Throttling -> Role
    credentialFilter.setNextHandler(throttlingFilter).setNextHandler(roleFilter)

    println("=== TEST 1: BAD CREDENTIAL REQUEST ===")
    val badRequest = LoginRequest("", "USER", 1)
    credentialFilter.handle(badRequest)

    println("\n=== TEST 2: SPAM REQUEST FROM NORMAL USER ===")
    val spamRequest = LoginRequest("bob_99", "USER", 12)
    credentialFilter.handle(spamRequest)

    println("\n=== TEST 3: VALID ADMIN REQUEST ===")
    val successRequest = LoginRequest("alex_admin", "ADMIN", 2)
    val isAuthorized = credentialFilter.handle(successRequest)
    println("Final Request Status: Authorized = $isAuthorized")
}
