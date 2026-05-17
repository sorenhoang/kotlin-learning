package creational.prototype

interface Prototype<T> {
    fun clone(): T
}

data class User(
    var name: String,
    var age: Int,
) : Prototype<User> {
    override fun clone(): User {
        return copy()
    }
}

fun main() {
    println("Hello Prototype System Design")

    val user =
        User(
            name = "Soren",
            age = 25,
        )

    // deep copy
    val userCopy = user.clone()
    userCopy.name = "Soren-changed"
    userCopy.age = 26

    println(user)
    println(userCopy)
}
