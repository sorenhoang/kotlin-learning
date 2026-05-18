package structural.flyweight

// ========================================================
// 1. FLYWEIGHT - Contains Intrinsic State (Shared data)
// ========================================================
class BulletType(
    val name: String,
    val color: String,
    val texture: ByteArray,
) {
    fun draw(
        x: Int,
        y: Int,
        velocity: Int,
    ) {
        // Extrinsic states (x, y, velocity) are passed as arguments
        println("🔫 Rendering '$name' [$color] at ($x, $y) with speed ${velocity}m/s")
    }
}

// ========================================================
// 2. FLYWEIGHT FACTORY - Ensures Flyweights are cached & reused
// ========================================================
object BulletFactory {
    private val bulletTypes = mutableMapOf<String, BulletType>()

    fun getBulletType(
        name: String,
        color: String,
    ): BulletType {
        val key = "$name-$color"

        // If the bullet type doesn't exist, create it. Otherwise, reuse it!
        return bulletTypes.getOrPut(key) {
            println("🏭 Factory: Creating NEW heavy BulletType for '$name' ($color)...")
            val fakeHeavyTexture = ByteArray(1024 * 1024) // Simulating 1MB texture
            BulletType(name, color, fakeHeavyTexture)
        }
    }

    fun getFactorySize(): Int = bulletTypes.size
}

// ========================================================
// 3. CONTEXT - Contains Extrinsic State (Unique data)
// ========================================================
class Bullet(
    private val x: Int,
    private val y: Int,
    private val velocity: Int,
    private val type: BulletType,
) {
    fun fire() {
        type.draw(x, y, velocity)
    }
}

fun main() {
    println("Hello Flyweight Design Pattern")

    val bullets = mutableListOf<Bullet>()

    println("=== 1. PLAYER FIRES MULTIPLE BULLETS ===")

    // Simulating firing 5 AK-47 bullets at different positions
    val akType = BulletFactory.getBulletType("7.62mm", "Gold")
    bullets.add(Bullet(10, 20, 715, akType))
    bullets.add(Bullet(12, 22, 713, akType))
    bullets.add(Bullet(15, 25, 710, akType))

    // Simulating firing 2 Pistol bullets
    val pistolType = BulletFactory.getBulletType("9mm", "Silver")
    bullets.add(Bullet(5, 5, 380, pistolType))
    bullets.add(Bullet(6, 7, 378, pistolType))

    println("\n=== 2. RENDERING ALL BULLETS ON SCREEN ===")
    for (bullet in bullets) {
        bullet.fire()
    }

    println("\n=== 3. MEMORY SAVING STATISTICS ===")
    println("Total bullets fired in game: ${bullets.size}")
    println("Total heavy BulletType objects created in RAM: ${BulletFactory.getFactorySize()}")
}
