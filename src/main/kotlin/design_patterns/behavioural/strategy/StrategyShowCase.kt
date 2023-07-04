package design_patterns.behavioural.strategy

/**
 * Strategy დიზაინ პატერნი განკუთვნილია ისეთი შემთხვევებისთვის, როდესაც გვსურს, რომ ობიექტში ლოგიკის შეცვლა რანთაიმში
 * იყოს შესაძლებელი.
 *
 * ნახსენები შესაძლებლობა ძალიან მოქნილს ხდის ობიექტს და გვაძლევს საშუალებას გამოვიყენოთ ის მრავალჯერადად
 *
 * ამ დიზაინის გამოყენებით ობიექტში გვრჩება მხოლოდ მისი core ფუნქციონალი, ხოლო პერიფერიული ლოგიკა მიეწოდება სტრატეგიის
 * ობიექტის საშუალებით
 * */

interface TextFormatterStrategy {
    fun format(text: String): String
}

class TextUppercaseFormatterStrategy: TextFormatterStrategy {
    override fun format(text: String) = text.uppercase()
}

class TextLowerCaseFormatterStrategy: TextFormatterStrategy {
    override fun format(text: String) = text.lowercase()
}

class Printer {

    // შესაძლებელია სტრატეგიის ობიექტი ჩამოეწოდოს კონსტრუქტორში, ფუნქციის პარამეტრის ნაცვლად
    fun printMessage(message: String, formatterStrategy: TextFormatterStrategy? = null) {
        println(formatterStrategy?.format(message) ?: message)
    }

}

fun main() {
    val printer = Printer()

    val lowerCaseFormatterStrategy = TextLowerCaseFormatterStrategy()
    val uppercaseFormatterStrategy = TextUppercaseFormatterStrategy()

    printer.printMessage("some message WITHOUT FORMATTER")
    printer.printMessage("uppercase message", uppercaseFormatterStrategy)
    printer.printMessage("LOWERCASE MESSAGE", lowerCaseFormatterStrategy)
}

