package structural.bridge

interface Device {
    var isEnabled: Boolean
    var volume: Int

    fun printStatus()
}

class TV : Device {
    override var isEnabled = false
    override var volume = 30

    override fun printStatus() {
        println("📺 TV is ${if (isEnabled) "On" else "Off"} | Volume: $volume%")
    }
}

class Radio : Device {
    override var isEnabled = false
    override var volume = 15

    override fun printStatus() {
        println("📻 Radio is ${if (isEnabled) "On" else "Off"} | Volume: $volume%")
    }
}

// Abstraction
open class RemoteControl(protected val device: Device) {
    fun togglePower() {
        device.isEnabled = !device.isEnabled
        println("⚡ Remote: click to enable power button")
    }

    fun volumeDown() {
        if (device.volume > 0) device.volume -= 10
        println("🔉 Remote: click to volume down button")
    }

    fun volumeUp() {
        if (device.volume < 100) device.volume += 10
        println("🔊 Remote: click to volume up button")
    }
}

class AdvancedRemoteControl(device: Device) : RemoteControl(device) {
    fun mute() {
        device.volume = 0
        println("🔉 Remote: click to mute button")
    }
}

fun main() {
    println("Hello Bridge Design Pattern")

    val tv = TV()
    val basicRemote = RemoteControl(tv)

    basicRemote.togglePower()
    basicRemote.volumeUp()
    tv.printStatus()

    println("Try with advance remote control")

    val radio = Radio()
    val advancedRemote = AdvancedRemoteControl(radio)

    advancedRemote.togglePower()
    advancedRemote.mute()
    radio.printStatus()
}
