package structural.proxy

// ========================================================
// 1. COMPONENT INTERFACE - Common interface for Real & Proxy
// ========================================================
interface Video {
    fun play()
}

// ========================================================
// 2. REAL SUBJECT - The heavy object that we want to control
// ========================================================
class RealVideo(private val fileName: String) : Video {
    init {
        loadFromDisk()
    }

    private fun loadFromDisk() {
        println("💾 Loading heavy video file: '$fileName' from disk...")
        // Simulating loading delay
        Thread.sleep(1000)
    }

    override fun play() {
        println("▶️ Playing video: '$fileName'")
    }
}

// ========================================================
// 3. PROXY SUBJECT - Intercepts requests and manages Real Subject
// ========================================================

class ProxyVideo(private val fileName: String) : Video {
    // The real heavy object is not created yet (remains null)
    private var realVideo: RealVideo? = null

    override fun play() {
        if (realVideo == null) {
            println("🛡️ Proxy: Real video is not loaded yet. Initializing now...")
            realVideo = RealVideo(fileName)
        } else {
            println("🛡️ Proxy: Real video already cached. Reusing existing instance.")
        }

        // Forward the action to the real object
        realVideo?.play()
    }
}

fun main() {
    println("Hello Proxy Design Pattern")

    println("=== 1. INITIALIZING VIDEO OBJECT ===")
    // Creating the proxy object is instantaneous
    // because it DOES NOT load the heavy file yet
    val video: Video = ProxyVideo("design_patterns_tutorial_4k.mp4")
    println("Status: Proxy video reference created.")

    println("\n=== 2. FIRST TIME CALLING PLAY() ===")
    // The real heavy video file will be loaded into RAM here for the first time
    video.play()

    println("\n=== 3. SECOND TIME CALLING PLAY() ===")
    // The video is already loaded, so it skips the loading process entirely (Caching)
    video.play()
}
