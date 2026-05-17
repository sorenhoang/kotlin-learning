package creational.singleton


object AppSetting{
    init {
        println("-> AppSettings: Init the first time and only once!")
    }

    var theme : String = "Light"
    const val language : String = "English"

    fun showConfig()
    {
        println("-> AppSettings: Theme: $theme, Language $language")
    }
}

class DatabaseConnection private constructor(val connectionUrl: String){
    init{
        println("-> DatabaseConnection init")
    }

    fun executeQuery(sql : String){
        println("-> DatabaseConnection Query: $sql")
    }

    companion object {
         private var instance: DatabaseConnection? = null

        fun getInstance(url: String): DatabaseConnection {
            return instance ?: DatabaseConnection(url).also { instance = it }
        }
    }
}

fun main() {
    println("=== Test Singleton 01===")

    AppSetting.showConfig()

    AppSetting.theme = "Dark"

    AppSetting.showConfig()

    println("=== Test Singleton 02===")

    val conn1 = DatabaseConnection.getInstance("jdbc:mysql://localhost:3306/mydb")
    conn1.executeQuery("SELECT * FROM users")

    val conn2 = DatabaseConnection.getInstance("jdbc:mysql://localhost:3306/hacked_db")
    conn2.executeQuery("SELECT * FROM products")

    println("conn1 and conn2 are one? ${conn1 === conn2}")
}