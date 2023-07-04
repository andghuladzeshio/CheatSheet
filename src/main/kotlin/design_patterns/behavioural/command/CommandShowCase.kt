package design_patterns.behavioural.command

import java.util.LinkedList
import java.util.Queue

/**
 * Command დიზაინ პატერნს შემოაქვს ერთგვარი რექვესთის მცნება. მოცემული ქომანდები გროვდება პროცესორში, Queue ტიპის
 * მონაცემთა სტრუქტურაში და შეიძლება ნებისმიერ დროს FIFO თანმიმდევრობით შესრულდეს. შესრულების შემდგომ დაგროვებული
 * ქომანდები იშლება, მაგრამ გვაქვს ახლის დამატებისა და პროცესის გამეორების საშუალება.
 *
 * მოცემული დიზაინ პატერნი საკმაოდ გამოსადეგია ისეთ პირობებში, როდესაც გვინდა რაიმე პერიოდულობით დავაგროვოთ და შესაბამის
 * დროს ,თანმიმდევრულად, გავუშვათ ფუნქციონალი.
 * */

interface Command {
    fun execute()
}

data class Order(val tableNumber: Int, val order: String, val totalAmount: Float)

class AddOrderCommand(private val order: Order): Command {
    override fun execute() {
        println("Added order:\n${order.order}\nFor table ${order.tableNumber}")
        // DB connection
    }
}

class PayTableCommand(private val tableNumber: Int): Command {
    override fun execute() {
        println("Table with number - $tableNumber is payed")
        // DB connection
    }
}

class OrderProcessor {
    // Queue-ს ეფექტის მიღწევა ლისტითაც შეიძლება შესაბამისი ფუნქციონალის განსაზღვრით processCommands ფუნქციაში
    private val commands: Queue<Command> = LinkedList()

    fun addCommand(command: Command) = apply {
        commands.add(command)
    }

    fun processCommands() = apply {
        var command = commands.poll()
        while (command != null) {
            command.execute()
            command = commands.poll()
        }
    }
}

fun main() {
    val processor = OrderProcessor()

    // საყურადღებოა, რომ არ აქვს აზრი რამდენჯერმე მოვახდენთ მოცემული ქომანდების პროცესინგს, თუ მხოლოდ ბოლოში გავაკეთებთ
    // ამას - შედეგი იდენტურია.
    processor.addCommand(AddOrderCommand(Order(1, "some order", 20f)))
    processor.addCommand(AddOrderCommand(Order(1, "some other order", 30f)))
    processor.processCommands()
    processor.addCommand(AddOrderCommand(Order(2, "some order", 62f)))
    processor.addCommand(AddOrderCommand(Order(3, "some order", 12f)))
    processor.addCommand(PayTableCommand(1))
    processor.addCommand(PayTableCommand(2))
    processor.processCommands()
    processor.addCommand(PayTableCommand(3))
    processor.processCommands()

}
