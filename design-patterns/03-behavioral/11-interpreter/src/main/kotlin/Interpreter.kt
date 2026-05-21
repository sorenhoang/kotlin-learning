package behavioral.interpreter

// ========================================================
// 1. THE ABSTRACT EXPRESSION
// ========================================================
interface Expression{
    fun interpret(): Int
}

// ========================================================
// 2. TERMINAL EXPRESSION - Represents numbers (Leaf nodes)
// ========================================================
class NumberExpression(private val number: Int) : Expression {
    override fun interpret(): Int {
        return number
    }
}

// ========================================================
// 3. NON-TERMINAL EXPRESSIONS - Represents operations (Parent nodes)
// ========================================================

// Concrete Operation: Addition (+)
class AddExpression(private val leftExpression: Expression, private val rightExpression: Expression) : Expression {
    override fun interpret(): Int {
        // Recursively interpret left and right branches, then add them
        return leftExpression.interpret() + rightExpression.interpret()
    }
}

// Concrete Operation: Subtraction (-)
class SubtractExpression(private val leftExpression: Expression, private val rightExpression: Expression) : Expression {
    override fun interpret(): Int {
        // Recursively interpret left and right branches, then subtract them
        return leftExpression.interpret() - rightExpression.interpret()
    }
}

// ========================================================
// MAIN EXECUTION FUNCTION (Client Code)
// ========================================================
fun main() {
    // Let's manually represent the syntax tree for the mathematical expression: (10 + 5) - 3
    println("=== BUILDING SYNTAX TREE FOR: (10 + 5) - 3 ===")

    // Step 1: Create terminal expressions for numbers 10, 5, and 3
    val num10 = NumberExpression(10)
    val num5 = NumberExpression(5)
    val num3 = NumberExpression(3)

    // Step 2: Create the non-terminal expression for (10 + 5)
    val sumExpression = AddExpression(num10, num5)

    // Step 3: Create the root non-terminal expression for: sumExpression - 3
    val rootExpression = SubtractExpression(sumExpression, num3)

    // Step 4: Execute the interpreter recursively
    println("🔄 Interpreter: Evaluating expression trees...")
    val finalResult = rootExpression.interpret()

    println("🎯 Final Evaluated Result: $finalResult")
}