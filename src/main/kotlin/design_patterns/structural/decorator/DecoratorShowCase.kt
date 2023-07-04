package design_patterns.structural.decorator

/**
 * დეკორატორი არის დიზაინ პატერნი, რომელიც საშუალებას გვაძლევს დავამატოთ ფუნქციონალი უკვე არსებულ ობიექტებს გარედან -
 * მათი კოდის შეცვლის გარეშე
 *
 * შენიშვნა: ამ პატერნის გამოყენებისას ყურადღებით უნდა ვიყოთ, რომ არ დავარღვიოთ ლისკოვ საბსთითუშენის პრინციპი,
 * ანუ ჩვენ არ უნდა შევცვალოთ ობიექტში არსებული ლოგიკა, ჩვენი დეკორატორი უნდა იმეორებდეს უკვე არსებულ ლოგიკას და
 * უბრალოდ ამატებდეს რაიმეს
 * */

interface PacketSender{
    fun sendPacket(packet: String, address: String)
}

open class TCPPacketSender: PacketSender {
    override fun sendPacket(packet: String, address: String) {
        println("Sending packet - $packet\non address - $address")
    }
}

interface MessageSender {
    fun sendMessage(message: String, address: String)
}

// ზოგადი დეკორატორი (თავსებადი ნებისმიერ PacketSender-თან)
class MessageSenderImpl(packetSender: PacketSender): PacketSender by packetSender, MessageSender {

    override fun sendMessage(message: String, address: String) {
        val packets = splitMessageIntoPackets(message)
        packets.forEach {
            sendPacket(it.toString(), address)
        }
    }

    private fun splitMessageIntoPackets(message: String): List<Char> = message.toList()

}

// კონკრეტული დეკორატორი (თავსებადი მხოლოდ TCPPacketSender-თან)
class TCPLoggedMessageSender: MessageSender, TCPPacketSender() {

    override fun sendPacket(packet: String, address: String) {
        super.sendPacket(packet, address)
        // მშობელი კლასის ფუნქციონალის გაფართოება
        println("Logging sent packet")
    }

    override fun sendMessage(message: String, address: String) {
        val packets = splitMessageIntoPackets(message)
        packets.forEach {
            sendPacket(it.toString(), address)
        }
    }

    private fun splitMessageIntoPackets(message: String): List<Char> = message.toList()
}

/**
 * შედეგად გავაფართოვეთ პაკეტსენდერის ფუნქციონალი და მივიღეთ [MessageSenderImpl], რომლის საშუალებითაც შეგვიძლია გავაგზავნოთ
 * როგორც პაკეტები, ასევე მესიჯები. მოცემული ობიექტი სრულიად თავსებადია თავდაპირველ [PacketSender]-ის ობიექტთან და ზუსტად
 * იმეორებს მის ლოგიკას, ამიტომ პრობლემა არ იქნება, რომ ჩავანაცვლოთ [PacketSender] [MessageSenderImpl]-ით ნებისმიერ ადგილას.
 *
 * როგორც ზოგადი, ასევე თავსებადი დეკორატორი არის სწორი მიდგომა და ამოცანიდან გამომდინარე შეგვიძლია გადავწყვიტოთ, თუ
 * რომელი გამოვიყენოთ.
 * */
fun main() {
    val packetSender = TCPPacketSender()
    val messageSender = MessageSenderImpl(packetSender)
    messageSender.sendPacket("packet", "192.168.0.1")
    messageSender.sendMessage("message", "192.168.0.1")

    val tcpLoggedMessageSender = TCPLoggedMessageSender()
    tcpLoggedMessageSender.sendPacket("packet", "192.168.0.1")
    tcpLoggedMessageSender.sendMessage("message", "192.168.0.1")
}

