package behavioral.strategy

// ========================================================
// 1. THE STRATEGY INTERFACE - Defines the common contract for all algorithms
// ========================================================
interface PaymentStrategy{
    fun collectPaymentDetails()
    fun pay(amount: Double): Boolean
}

// ========================================================
// 2. CONCRETE STRATEGIES - Implementing specific algorithms
// ========================================================

// Strategy A: Credit Card Payment
class CreditCardPayment(private val cardNumber: String, private val cvv: String): PaymentStrategy{
    override fun collectPaymentDetails() {
         println("💳 CreditCard: Verifying card number '$cardNumber' and checking CVV...")
    }

    override fun pay(amount: Double): Boolean {
        println("💳 CreditCard: Successfully charged \$$amount using Credit Card.")
        return true
    }
}

// Strategy B: PayPal / MoMo Payment
class PayPalPayment(private val email: String) : PaymentStrategy {
    override fun collectPaymentDetails() {
        println("📱 PayPal: Redirecting to PayPal login for account: $email...")
    }

    override fun pay(amount: Double): Boolean {
        println("📱 PayPal: Successfully approved \$$amount transfer via PayPal secure gateway.")
        return true
    }
}

// ========================================================
// 3. THE CONTEXT - The object that uses the strategy
// ========================================================

class ShoppingCart {
    private var totalAmount: Double = 0.0
    // The cart maintains a reference to a Strategy interface
    private var paymentStrategy: PaymentStrategy? = null

    fun addItemPrice(price: Double) {
        totalAmount += price
    }

    // Client can dynamically set or change the strategy at runtime
    fun setPaymentMethod(strategy: PaymentStrategy) {
        this.paymentStrategy = strategy
    }

    fun checkout() {
        val strategy = paymentStrategy
        if (strategy == null) {
            println("⚠️ Cart: Cannot checkout! Please select a payment method first.")
            return
        }

        println("🛒 Cart: Processing your total bill of \$$totalAmount...")
        strategy.collectPaymentDetails()

        val isSuccess = strategy.pay(totalAmount)
        if (isSuccess) {
            println("🎉 Cart: Order placed successfully! Thank you for shopping.")
            totalAmount = 0.0 // Reset cart
        } else {
            println("❌ Cart: Payment failed. Please try again.")
        }
    }
}

// ========================================================
// MAIN EXECUTION FUNCTION (Client Code)
// ========================================================
fun main() {
    // Initialize the shopping cart
    val cart = ShoppingCart()
    cart.addItemPrice(120.50)
    cart.addItemPrice(15.00) // Total bill = 135.50

    println("=== TEST 1: ATTEMPT CHECKOUT WITHOUT STRATEGY ===")
    cart.checkout()

    println("\n=== TEST 2: CHECKOUT USING CREDIT CARD STRATEGY ===")
    // Client injects the Credit Card Strategy into Context
    cart.setPaymentMethod(CreditCardPayment("1234-5678-9876", "555"))
    cart.checkout()

    println("\n=== TEST 3: NEW ORDER USING PAYPAL STRATEGY ===")
    cart.addItemPrice(450.00) // New order bill = 450.00
    // Client switches the strategy to PayPal seamlessly
    cart.setPaymentMethod(PayPalPayment("alex_dev@gmail.com"))
    cart.checkout()
}