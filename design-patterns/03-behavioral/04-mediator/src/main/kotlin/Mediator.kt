package behavioral.mediator

// ========================================================
// 1. THE MEDIATOR INTERFACE
// ========================================================
interface ChatRoomMediator {
    fun sendMessage(
        message: String,
        user: User,
    )

    fun addUser(user: User)
}

// ========================================================
// 2. THE COLLEAGUE (Base Class for participants)
// ========================================================
abstract class User(protected val mediator: ChatRoomMediator, val name: String) {
    abstract fun send(message: String)

    abstract fun receive(
        message: String,
        fromUser: String,
    )
}

class ChatRoom : ChatRoomMediator {
    private val users = mutableListOf<User>()

    override fun addUser(user: User) {
        users.add(user)
    }

    override fun sendMessage(
        message: String,
        user: User,
    ) {
        for (u in users) {
            if (u != user) {
                u.receive(message, user.name)
            }
        }
    }
}

class ChatUser(mediator: ChatRoomMediator, name: String) : User(mediator, name) {
    override fun send(message: String) {
        println("💬 [${this.name}] sends: '$message'")
        // Delegate the sending task to the mediator
        mediator.sendMessage(message, this)
    }

    override fun receive(
        message: String,
        fromUser: String,
    ) {
        println("📥 [${this.name}] received from [$fromUser]: \"$message\"")
    }
}

// ========================================================
// MAIN EXECUTION FUNCTION (Client Code)
// ========================================================
fun main() {
    println("Hello Mediator Design Pattern")

    // 1. Initialize the Mediator (The Chat Room Hub)
    val chatRoom: ChatRoomMediator = ChatRoom()

    // 2. Create users and bind them to the same Mediator
    val alice = ChatUser(chatRoom, "Alice")
    val bob = ChatUser(chatRoom, "Bob")
    val charlie = ChatUser(chatRoom, "Charlie")

    // 3. Register users to the mediator
    chatRoom.addUser(alice)
    chatRoom.addUser(bob)
    chatRoom.addUser(charlie)

    // 4. Users communicate via the Mediator smoothly
    println("=== TEST 1: ALICE SENDS A MESSAGE ===")
    alice.send("Hi everyone! Good morning.")

    println("\n=== TEST 2: BOB REPLIES ===")
    bob.send("Hey Alice! Long time no see.")
}
