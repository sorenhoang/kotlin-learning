package behavioral.state

// ========================================================
// 1. THE STATE INTERFACE - Defines behaviors for all states
// ========================================================
interface State {
    fun insertCoin()
    fun ejectCoin()
    fun pressButton()
    fun dispense()
}

// ========================================================
// 2. THE CONTEXT - The main machine holding the current state
// ========================================================
class VendingMachine(var count: Int) {
    // Define all possible state instances
    val noCoinState: State = NoCoinState(this)
    val hasCoinState: State = HasCoinState(this)
    val soldState: State = SoldState(this)
    val outOfStockState: State = OutOfStockState(this)

    // Set the initial state based on inventory
    var currentState: State = if (count > 0) noCoinState else outOfStockState

    fun insertCoin() = currentState.insertCoin()
    fun ejectCoin() = currentState.ejectCoin()
    fun pressButton() {
        currentState.pressButton()
        // Internal action triggered automatically after pressing button
        currentState.dispense()
    }

    fun releaseBall() {
        if (count > 0) {
            println("🥤 VendingMachine: A drink is rolling out the slot...")
            count--
        }
    }
}

// ========================================================
// 3. CONCRETE STATES - Implementing state-specific behaviors
// ========================================================

// State A: Machine has no coin inserted
class NoCoinState(private val machine: VendingMachine) : State {
    override fun insertCoin() {
        println("🪙 State [NoCoin]: You inserted a coin.")
        machine.currentState = machine.hasCoinState // Transition state
    }
    override fun ejectCoin() = println("❌ State [NoCoin]: You haven't inserted a coin yet.")
    override fun pressButton() = println("❌ State [NoCoin]: You pressed the button, but there's no coin.")
    override fun dispense() = println("❌ State [NoCoin]: You need to pay first.")
}

// State B: Machine has a coin inside
class HasCoinState(private val machine: VendingMachine) : State {
    override fun insertCoin() = println("⚠️ State [HasCoin]: You can't insert another coin.")
    override fun ejectCoin() {
        println("🔄 State [HasCoin]: Coin returned.")
        machine.currentState = machine.noCoinState // Transition back
    }
    override fun pressButton() {
        println("🎯 State [HasCoin]: Button pressed...")
        machine.currentState = machine.soldState // Transition to processing
    }
    override fun dispense() = println("❌ State [HasCoin]: No drink dispensed yet.")
}

// State C: Machine is dispensing the drink
class SoldState(private val machine: VendingMachine) : State {
    override fun insertCoin() = println("⚠️ State [Sold]: Please wait, we are already giving you a drink.")
    override fun ejectCoin() = println("❌ State [Sold]: Sorry, you already pressed the button.")
    override fun pressButton() = println("⚠️ State [Sold]: Pressing twice won't give you another drink.")
    override fun dispense() {
        machine.releaseBall()
        if (machine.count > 0) {
            machine.currentState = machine.noCoinState
        } else {
            println("🛑 State [Sold]: Oops, out of drinks!")
            machine.currentState = machine.outOfStockState
        }
    }
}

// State D: Machine is completely empty
class OutOfStockState(private val machine: VendingMachine) : State {
    override fun insertCoin() = println("❌ State [OutOfStock]: Cannot insert coin. Machine is empty.")
    override fun ejectCoin() = println("❌ State [OutOfStock]: You haven't inserted a coin.")
    override fun pressButton() = println("❌ State [OutOfStock]: You pressed, but there are no drinks left.")
    override fun dispense() = println("❌ State [OutOfStock]: No drinks to dispense.")
}

// ========================================================
// MAIN EXECUTION FUNCTION (Client Code)
// ========================================================
fun main() {
    // Initialize a vending machine with 2 drinks in stock
    val machine = VendingMachine(2)

    println("=== ROUND 1: SUCCESSFUL PURCHASE ===")
    machine.insertCoin()
    machine.pressButton()

    println("\n=== ROUND 2: TRYING TO CHEAT ===")
    machine.pressButton() // Pressing button without inserting coin

    println("\n=== ROUND 3: INSERT AND CANCEL ===")
    machine.insertCoin()
    machine.ejectCoin()   // Take coin back

    println("\n=== ROUND 4: BUYING THE LAST DRINK ===")
    machine.insertCoin()
    machine.pressButton()

    println("\n=== ROUND 5: MACHINE IS NOW OUT OF STOCK ===")
    machine.insertCoin()