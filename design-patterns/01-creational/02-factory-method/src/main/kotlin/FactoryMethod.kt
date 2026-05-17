package creational.factorymethod

interface Notification {
    fun sendNotification()
}

class EmailNotification : Notification {
    override fun sendNotification() {
        println("This notification was sent by Email")
    }
}

class SMSNotification : Notification {
    override fun sendNotification() {
        println("This notification was sent by SMS")
    }
}

enum class NotificationType {
    EMAIL,
    SMS,
}

object NotificationFactory {
    fun createNotification(type: NotificationType): Notification {
        return when (type) {
            NotificationType.EMAIL -> EmailNotification()
            NotificationType.SMS -> SMSNotification()
        }
    }
}

fun main(args: Array<String>) {
    println("Hello Factory Method Design Pattern")

    val emailChannel = NotificationFactory.createNotification(NotificationType.EMAIL)
    val smsChannel = NotificationFactory.createNotification(NotificationType.SMS)

    emailChannel.sendNotification()
    smsChannel.sendNotification()
}
