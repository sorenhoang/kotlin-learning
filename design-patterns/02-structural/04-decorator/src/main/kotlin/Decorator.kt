package structural.decorator

interface Coffee {
    fun getCost(): Double

    fun getDescription(): String
}

class SimpleCoffee : Coffee {
    override fun getCost(): Double = 5.0

    override fun getDescription(): String = "This is the simple coffee"
}

open class CoffeeDecorator(protected val coffee: Coffee) : Coffee by coffee

class SugarDecorator(coffee: Coffee) : CoffeeDecorator(coffee) {
    override fun getCost(): Double = super.getCost() + 0.5

    override fun getDescription(): String = super.getDescription() + " + Sugar"
}

class MilkDecorator(coffee: Coffee) : CoffeeDecorator(coffee) {
    override fun getCost(): Double = super.getCost() + 1

    override fun getDescription(): String = super.getDescription() + " + Milk"
}

fun main() {
    println("Hello Decorator Design Pattern")

    val coffee1: Coffee = SimpleCoffee()
    println("Food: ${coffee1.getDescription()} | Price: \$${coffee1.getCost()}")

    var coffee2: Coffee = SimpleCoffee()
    coffee2 = MilkDecorator(coffee2)
    coffee2 = SugarDecorator(coffee2)
    println("Food: ${coffee2.getDescription()} | Price: \$${coffee2.getCost()}")
}
