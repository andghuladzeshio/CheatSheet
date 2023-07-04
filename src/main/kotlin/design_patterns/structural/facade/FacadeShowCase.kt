package design_patterns.structural.facade

/**
 * ფასადის პატერნი გამოიყენება რთული, კომპლექსური ლოგიკისთვის მარტივი ინტერფეისის შესაქმნელად.
 *
 * ინტერფეისი შეიძლება გაგებული იყოს, როგორც პირდაპირ, ასევე შეიძლება აღნიშნავდეს ერთ ან რამოდენიმე კლასს, რომელიც
 * ეხმარება იუზერს გამოიყენოს კომპლექსური ლოგიკა მარტივად
 *
 * ამ შემთხვევაში ფასადი ფარავს იუზერის სახელისა და პაროლის კოდირების ლოგიკას და ძალიან მარტივად მხოლოდ იუზერნეიმისა და
 * პაროლის გადაცემით გვაძლევს საშუალებას ვაწარმოოთ აუთენთიფიკაციის პროცესი.
 *
 * დაფარული ლოგიკები რეალურ იუზქეისებში საკმაოდ ვრცელი არის ხოლმე
 * */

class DBConnection{
    fun open() = Unit

    fun doSomething(someParam: Any) = Unit

    fun getSomeString() = "some string"

    fun commit() = Unit

    fun close() = Unit
}

class StringCoder {
    fun encode(string: String) = "let's pretend it's encoded string"

    fun decode(string: String) = "I don't know what the hell that it"
}

class UserAuthenticator {
    private val connection = DBConnection()

    fun authenticateUser(userName: String, password: String) {
        connection.open()
        connection.doSomething(userName)
        connection.doSomething(password)
        connection.commit()
        connection.close()
    }

    fun getCurrentUserName(): String {
        connection.open()
        val name = connection.getSomeString()
        connection.commit()
        connection.close()
        return name
    }

    // წარმოვიდგინოთ, რომ აქ საკმაოდ დიდი, კომპლექსური ფუნქციონალი წერია, რომელიც გარედან შეგვიძლია გამოვიყენოთ
}

class UserAuthenticationFacade {
    private val coder = StringCoder()
    private val authenticator = UserAuthenticator()

    fun authenticateUser(userName: String, password: String) {
        val codedPassword = coder.encode(password)
        val codedName = coder.encode(userName)
        authenticator.authenticateUser(codedName, codedPassword)
    }

    fun getCurrentUserName() = coder.decode(authenticator.getCurrentUserName())
}

fun main() {
    val facade = UserAuthenticationFacade()
    facade.authenticateUser("shio", "password")
    val currentUser = facade.getCurrentUserName()
    println(currentUser)
}
