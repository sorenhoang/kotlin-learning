package creational.builder

data class User(
    val name: String,
    val age: Int,
    val email: String?,
    val address: String?,
)

class UserBuilder {
    private var name: String = ""
    private var age: Int = 0
    private var email: String? = null
    private var address: String? = null

    fun setName(name: String) =
        apply {
            this.name = name
        }

    fun setAge(age: Int) =
        apply {
            this.age = age
        }

    fun setEmail(email: String?) =
        apply {
            this.email = email
        }

    fun setAddress(address: String?) =
        apply {
            this.address = address
        }

    fun build(): User {
        require(name.isNotBlank()) {
            "Name is required"
        }

        require(age > 0) {
            "Age must > 0"
        }

        return User(
            name = name,
            age = age,
            email = email,
            address = address,
        )
    }
}

fun main() {
    println("Hello Builder Design Pattern")

    val user =
        UserBuilder()
            .setName("Binh")
            .setAge(25)
            .setEmail("binh@gmail.com")
            .setAddress("Ho Chi Minh")
            .build()

    println(user)
}
