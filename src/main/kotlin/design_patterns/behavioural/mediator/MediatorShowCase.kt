package design_patterns.behavioural.mediator

/**
 * Mediator დიზაინ პატერნში შემოდის მედიატორის ობიექტის მცნება. მედიატორი აკავშირებს აპლიკაციის კომპონენტებს ერთმანეთთან,
 * რაც ამცირებს დამოკიდებულებას მათ შორის.
 *
 * ამ დიზაინ პატერნის გამოყენებისას კომპონენტებს აღარ უწევთ, რომ გაიგონ ერთმანეთზე, ან დაარეგულირონ ერთმანეთთან კომუნიკაციის პროცესი,
 * მოცემული ლოგიკა მედიატორის ობიექტში არის გატანილი.
 *
 * მედიატორის დიზაინ პატერნის გამოყენება ძალიან მოსახერხებელია მაშინ, როდესაც გვაქვს მრავალი ისეთი კომპონენტი (ერთი ან სხვადასხვა ტიპის)
 * , რომლებიც ხშირად ეკონტაქტებიან ერთმანეთს ინფორმაციის გაცვლისთვის, ან სხვა ნებისმიერი ფუნქციონალის განსახორციელებლად.
 * */

class ChatUser(private val mediator: GroupChatMediator, private val name: String) {

    fun sendMessage(message: String) {
        println("user - $name | sending message - $message")
        mediator.sendMessage(message, this)
    }

    fun receiveMessage(message: String) {
        println("user - $name | receiving message - $message")
    }

}

class GroupChatMediator {
    private val users = mutableListOf<ChatUser>()

    fun sendMessage(message: String, currentUser: ChatUser) = users.forEach {
        if (it != currentUser) it.receiveMessage(message)
    }

    fun addUser(user: ChatUser) = users.add(user)
}

fun main() {
    val mediator = GroupChatMediator()
    val user1 = ChatUser(mediator, "user1")
    val user2 = ChatUser(mediator, "user2")
    val user3 = ChatUser(mediator, "user3")
    val user4 = ChatUser(mediator, "user4")
    val user5 = ChatUser(mediator, "user5")
    mediator.addUser(user1)
    mediator.addUser(user2)
    mediator.addUser(user3)
    mediator.addUser(user4)
    mediator.addUser(user5)

    user2.sendMessage("Test message")
    user4.sendMessage("Test message 4")
}
