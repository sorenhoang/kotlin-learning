package creational.abstractfactory

// interface
interface Button {
    fun render()
}

interface Checkbox {
    fun check()
}

// Group Mac

class MacButton : Button {
    override fun render() {
        println("This is Mac Button")
    }
}

class MacCheckbox : Checkbox {
    override fun check() {
        println("This is Mac Checkbox")
    }
}

// Group Window

class WindowsButton : Button {
    override fun render() {
        println("This is Windows Button")
    }
}

class WindowsCheckbox : Checkbox {
    override fun check() {
        println("This is Windows Checkbox")
    }
}

// UI Factory

interface UIFactory {
    fun createButton(): Button

    fun createCheckbox(): Checkbox
}

object WindowsFactory : UIFactory {
    override fun createButton(): Button = WindowsButton()

    override fun createCheckbox(): Checkbox = WindowsCheckbox()
}

object MacFactory : UIFactory {
    override fun createButton(): Button = MacButton()

    override fun createCheckbox(): Checkbox = MacCheckbox()
}

class Application(private val factory: UIFactory) {
    private lateinit var button: Button
    private lateinit var checkbox: Checkbox

    fun createUI() {
        button = factory.createButton()
        checkbox = factory.createCheckbox()
    }

    fun paint() {
        button.render()
        checkbox.check()
    }
}

fun main() {
    println("Hello Abstract Factory Design Pattern")

    // init Win UI
    val winApp = Application(WindowsFactory)
    winApp.createUI()
    winApp.paint()

    val macApp = Application(MacFactory)
    macApp.createUI()
    macApp.paint()
}
