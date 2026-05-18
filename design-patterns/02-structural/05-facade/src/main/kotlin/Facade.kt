package structural.facade

// ========================================================
// 1. COMPLEX SUBSYSTEMS (The hidden components)
// ========================================================
class TheaterLights {
    fun dim(level: Int) = println("💡 TheaterLights: Dimming to $level%")

    fun on() = println("💡 TheaterLights: Turning fully ON")
}

class Projector {
    fun on() = println("📹 Projector: Powering ON...")

    fun setWidescreenMode() = println("📹 Projector: Setting aspect ratio to widescreen (16:9)")

    fun off() = println("📹 Projector: Powering OFF")
}

class SoundSystem {
    fun on() = println("🔊 SoundSystem: Powering ON...")

    fun setVolume(level: Int) = println("🔊 SoundSystem: Adjusting volume level to $level")

    fun off() = println("🔊 SoundSystem: Powering OFF")
}

class DvdPlayer {
    fun play(movie: String) = println("💿 DvdPlayer: Now playing movie [ $movie ]")

    fun stop() = println("💿 DvdPlayer: Streaming stopped.")
}

// ========================================================
// 2. THE FACADE CLASS (The unified entry point)
// ========================================================
class HomeTheaterFacade(
    private val lights: TheaterLights,
    private val projector: Projector,
    private val soundSystem: SoundSystem,
    private val dvdPlayer: DvdPlayer,
) {
    // Expose a single clean function to wrap 6 structural calls
    fun watchMovie(movie: String) {
        println("🎬 [Facade] Preparing the theater setup for you...")
        lights.dim(10)
        projector.on()
        projector.setWidescreenMode()
        soundSystem.on()
        soundSystem.setVolume(40)
        dvdPlayer.play(movie)
        println("🍿 [Facade] Theater is ready! Enjoy your movie.")
    }

    // Clean up everything when done
    fun endMovie() {
        println("\n🛑 [Facade] Shutting down the theater system...")
        lights.on()
        projector.off()
        soundSystem.off()
        dvdPlayer.stop()
        println("✨ [Facade] System safely turned off.")
    }
}

// ========================================================
// MAIN EXECUTION FUNCTION (Client Code)
// ========================================================
fun main() {
    // 1. Initialize the complex hardware components
    val lights = TheaterLights()
    val projector = Projector()
    val soundSystem = SoundSystem()
    val dvdPlayer = DvdPlayer()

    // 2. Wrap them inside our Facade controller
    val smartRemote = HomeTheaterFacade(lights, projector, soundSystem, dvdPlayer)

    // 3. The Client only interacts with the clean Facade interface
    smartRemote.watchMovie("Interstellar (2014)")

    // 4. Shut down when finished
    smartRemote.endMovie()
}
