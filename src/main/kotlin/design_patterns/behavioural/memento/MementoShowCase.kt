package design_patterns.behavioural.memento

/**
 * Memento დიზაინ პატერნი გვეხმარება სიტუაციებში, როდესაც გვინდა ობიექტის სტეიტის დამახსოვრებ ან წინა/უკანა სტეიტის
 * მდგომარეობაზე დაბრუნება.
 *
 * მოცემულ დიზაინ პატერნს შემოაქვს Memento, Originator და CareTaker მცნებები.
 *
 * Memento - წარმოადგენს სტეიტს, რომელსაც ვიმახსოვრებთ
 * Originator - წარმოადგენს ობიექტს, რომელსაც ყოველთვის ერთი კონკრეტული სტეიტი უჭირავს
 * CareTaker - წარმოადგენს ობიექტს, რომელიც შეიცავს სტეიტების შენახვა/აღდგენის ფუნქციონალს
 * */

data class Memento(val state: String)

class Originator(var state: String) {
    fun createMemento() = Memento(state)

    fun restoreMemento(memento: Memento) {
        state = memento.state
    }
}

class CareTaker {
    private val mementos = mutableListOf<Memento>()
    private var currentStateIndex = -1

    fun saveMemento(memento: Memento) {
        currentStateIndex++
        mementos.add(memento)
    }

    fun undo(): Memento {
        currentStateIndex--
        return mementos[currentStateIndex]
    }

    fun redo(): Memento {
        currentStateIndex++
        return mementos[currentStateIndex]
    }
}

fun main() {
    val originator = Originator("initial state")
    val careTaker = CareTaker()

    // ვიმახსოვრებთ სტეიტს
    careTaker.saveMemento(originator.createMemento())

    // მოცემული სტეიტი არ დავიმახსოვრეთ, ამიტომ ვერ აღვადგენთ
    originator.state = "State 0"

    originator.state = "State 1"
    careTaker.saveMemento(originator.createMemento())

    originator.state = "State 2"
    careTaker.saveMemento(originator.createMemento())

    println(originator.state) // State 2
    originator.restoreMemento(careTaker.undo())
    println(originator.state) // State 1
    originator.restoreMemento(careTaker.undo())
    println(originator.state) // initial state
    originator.restoreMemento(careTaker.redo())
    println(originator.state) // State 1
    originator.restoreMemento(careTaker.redo())
    println(originator.state) // State 2
}
