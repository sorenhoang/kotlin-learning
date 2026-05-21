package behavioral.visitor

// ========================================================
// 1. THE VISITOR INTERFACE - Defines visit methods for each element type
// ========================================================
interface ComputerPartVisitor {
    fun visit(keyboard: Keyboard)
    fun visit(monitor: Monitor)
}

// ========================================================
// 2. THE ELEMENT INTERFACE - Defines the entry point for visitors
// ========================================================
interface ComputerPart {
    fun accept(visitor: ComputerPartVisitor)
}

// ========================================================
// 3. CONCRETE ELEMENTS - Stable structures that rarely change
// ========================================================
class Keyboard(val brand: String, val price: Double) : ComputerPart {
    // Double Dispatch technique: The element guides the visitor to the right method
    override fun accept(visitor: ComputerPartVisitor) {
        visitor.visit(this)
    }
}

class Monitor(val sizeInInches: Int, val price: Double) : ComputerPart {
    override fun accept(visitor: ComputerPartVisitor) {
        visitor.visit(this)
    }
}

// ========================================================
// 4. CONCRETE VISITORS - Encapsulate the algorithms/behaviors
// ========================================================

// Visitor 1: For displaying the specifications of parts
class ComputerPartDisplayVisitor : ComputerPartVisitor {
    override fun visit(keyboard: Keyboard) {
        println("⌨️  Display: Keyboard brand is '${keyboard.brand}' cost \$${keyboard.price}")
    }

    override fun visit(monitor: Monitor) {
        println("🖥️  Display: Monitor size is ${monitor.sizeInInches} inches cost \$${monitor.price}")
    }
}

// Visitor 2: For calculating tax (Keyboards have 5% tax, Monitors have 10% tax)
class ComputerPartTaxVisitor : ComputerPartVisitor {
    private var totalTax = 0.0

    override fun visit(keyboard: Keyboard) {
        val tax = keyboard.price * 0.05
        totalTax += tax
        println("💸 TaxEngine: Keyboard tax calculated: \$$tax")
    }

    override fun visit(monitor: Monitor) {
        val tax = monitor.price * 0.10
        totalTax += tax
        println("💸 TaxEngine: Monitor tax calculated: \$$tax")
    }

    fun getTotalCalculatedTax(): Double = totalTax
}

// ========================================================
// MAIN EXECUTION FUNCTION (Client Code)
// ========================================================
fun main() {
    // Create a fixed structure of computer parts (The Elements)
    val computerParts: List<ComputerPart> = listOf(
        Keyboard("Logitech G Pro", 120.0),
        Monitor(27, 350.0),
        Keyboard("Razer BlackWidow", 150.0)
    )

    println("=== TOUR 1: PROCESSING WITH DISPLAY VISITOR ===")
    val displayVisitor = ComputerPartDisplayVisitor()
    for (part in computerParts) {
        part.accept(displayVisitor) // The element accepts the visitor execution
    }

    println("\n=== TOUR 2: PROCESSING WITH TAX VISITOR ===")
    val taxVisitor = ComputerPartTaxVisitor()
    for (part in computerParts) {
        part.accept(taxVisitor)
    }
    println("📈 Total Tax to pay for the setup: \$${taxVisitor.getTotalCalculatedTax()}")
}