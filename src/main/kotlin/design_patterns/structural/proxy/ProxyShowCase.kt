package design_patterns.structural.proxy

/**
 * proxy არის დიზაინ პატერნი, რომელიც გვეხმარება, რომ ჩავატაროთ რაიმე ოპერაცია ობიექტთან ურთიერთობამდე ან ურთიერთობის
 * შემდეგ.
 *
 * მაგალითად, თუ გვაქვს აპლიკაცია, რომლის საშუალებითაც იუზერი დისკზე არსებულ ფაილებს ხსნის, შეგვიძლია შევქმნათ
 * FileProxy ობიექტი, რომელიც ფაილის გახსნის შემდეგ მოახდენს ამ ფაილის ქეშირებას, ხოლო გახსნამდე შეამოწმებს
 * უკვე არის თუ არა ქეშირებული მოცემული ფაილი, რომ დააჩქაროს გახსნის პროცესი.
 *
 * მოცემული პატერნი ძალიან გავს ფასადის პატერნს, მაგრამ განსხვავება ისაა, რომ პროქსის ობიექტი აიმპლემენტირებს
 * იმ ობიექტის ინტერფეისს, რომლის მენეჯმენტსაც ახდენს. ანუ შეგვიძლია პროქსის ობიექტი გამოვიყენოთ რეალური ობიექტის
 * ნაცვლად აბსტრაქციის საშუალებით.
 * */

interface ChatApi {
    fun sendMessage(message: String)
}

class ChatApiImpl: ChatApi {
    override fun sendMessage(message: String) = println("Message sent")
}

interface Chat {
    fun sendMessage(message: String)
}

class RealChat(private val chatApi: ChatApi): Chat {
    override fun sendMessage(message: String) = chatApi.sendMessage(message)
}

class ChatProxy(chatApi: ChatApi): Chat {
    private val realChat by lazy { RealChat(chatApi) }

    override fun sendMessage(message: String) {
        if (message.isBlank()) println("You can't send blank messages")
        else realChat.sendMessage(message)
    }
}

class User(private val chat: Chat) {
    fun sendMessage(message: String) {
        chat.sendMessage(message)
    }
}

/**
 * შედეგად მივიღეთ ობიექტი ChatProxy, რომელიც ახდენს RealChat ობიექტის მენეჯმენტს და მესიჯის გაგზავნის ლოგიკაში
 * ამატებს მესიჯის შემოწმების ფუნქციონალს, მაგრამ თვითონაც Chat ინტერფეისის იმპლემენტაციას წარმოადგენს, ამიტომ
 * იუზერს შეგვიძლია თავისუფლად ჩავაწოდოთ ორივე.
 *
 * მოცემული პატერნი გამოსადეგია, როდესაც ობიექტში, მაგალითად ჩატში, გვინდა, რომ მხოლოდ მესიჯის გაგზავნის ლოგიკა
 * ეწერეოს, ყველა სხვა დამატებითი ფუნქციონალის გატანა კი პროქსის ობიექტში შეგვიძლია.
 *
 * ამ ყველაფრის შედეგად მივიღებთ ერთი პასუხისმგებლობის მქონე ობიექტს, რომელიც არ იქნება დაბინძურებული ლოგიკით,
 * შესაბამისად თავისუფლად გამოვიყენებთ სხვადასხვა ფუნქციონალში.
 *
 * შესაძლებელია ერთი ობიექტის ირგვლივ რამდენიმე, სხვადასხვა დანიშნულების, პროქსის შექმნა.
 * */
fun main() {
    val api = ChatApiImpl()
    val chatProxy = ChatProxy(api)
    val realChat = RealChat(api)
    val userWithRealChat = User(realChat)
    val userWithProxyChat = User(chatProxy)
    userWithRealChat.sendMessage("")
    userWithProxyChat.sendMessage("")
}
