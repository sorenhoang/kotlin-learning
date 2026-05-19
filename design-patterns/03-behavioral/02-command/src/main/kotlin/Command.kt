package behavioral.command

// ========================================================
// 1. THE RECEIVER - The object that knows how to perform the actual work
// ========================================================
class Light(private val roomName: String) {
    fun turnOn() = println("💡 Light: The lights in the $roomName are now ON.")

    fun turnOff() = println("🌑 Light: The lights in the $roomName are now OFF.")
}

// ========================================================
// 2. THE COMMAND INTERFACE
// ========================================================
interface Command {
    fun execute()

    fun undo() // Every command must know how to reverse itself
}

// ========================================================
// 3. CONCRETE COMMANDS - Encapsulate actions into objects
// ========================================================
class LightOnCommand(private val light: Light) : Command {
    override fun execute() = light.turnOn()

    override fun undo() = light.turnOff() // Undo of ON is OFF
}

class LightOffCommand(private val light: Light) : Command {
    override fun execute() = light.turnOff()

    override fun undo() = light.turnOn() // Undo of OFF is ON
}

// ========================================================
// 4. THE INVOKER - The trigger that asks the command to execute
// ========================================================
class RemoteControl {
    private var lastCommand: Command? = null

    fun submitCommand(command: Command) {
        println("🎮 Remote: Activating command...")
        command.execute()
        lastCommand = command // Keep track of history for undo feature
    }

    fun pressUndo() {
        if (lastCommand != null) {
            println("⏪ Remote: Pressing UNDO button...")
            lastCommand?.undo()
            lastCommand = null // Clear history after single undo
        } else {
            println("⚠️ Remote: Nothing to undo.")
        }
    }
}

// ========================================================
// MAIN EXECUTION FUNCTION (Client Code)
// ========================================================
fun main() {
    // 1. Create the Receiver (the actual device)
    val livingRoomLight = Light("Living Room")

    // 2. Create the Commands and bind them to the Receiver
    val lightOn = LightOnCommand(livingRoomLight)
    val lightOff = LightOffCommand(livingRoomLight)

    // 3. Create the Invoker (the remote control)
    val remote = RemoteControl()

    println("=== TEST 1: TURNING LIGHTS ON & UNDO ===")
    remote.submitCommand(lightOn)
    remote.pressUndo()

    println("\n=== TEST 2: TURNING LIGHTS OFF & UNDO ===")
    remote.submitCommand(lightOff)
    remote.pressUndo()

    println("\n=== TEST 3: ATTEMPTING DOUBLE UNDO ===")
    remote.pressUndo()
}
