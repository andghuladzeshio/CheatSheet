package design_patterns.behavioural.observer

/**
 * ობზერვერ პატერნი გვეხმარება one to many ურთიერთობების დამყარებაში ობიექტებს შორის.
 *
 * პატერნი მოიცავს event, observer და event manager მცნებებს
 *
 * observer - ობზერვერი არის ობიექტი, რომელიც ელოდება ივენთ მენეჯერში ივენთების მოსვლას, რაიმე ფუნქციონალის შესასრულებლად
 * event - ივენთი განასახიერებს რაიმე ქმედებას, რომელიც რეგისტრირდება event manager-ის ობიექტში, ობზერვერებთან შემდგომი
 * დისტრიბუციისთვის
 * event manager - ივენთ მენეჯერი არის ობიექტი, რომელიც საშუალებას აძლევს ობზერვერებს "უსმინონ" კონკრეტულ ივენთებს და
 * მათი მოსვლის შემთხვევაში განახორციელონ ქმედება
 * */

interface GameEventListener {
    fun onGameEvent(text: String)
}

enum class GameEventType(val text: String) {
    WIN("You win!"),
    LOSE("You lose!"),
    DRAW("It's a draw!")
}

class GameEventManager {
    private val events = hashMapOf<GameEventType, MutableList<GameEventListener>>()

    init {
        GameEventType.values().forEach { events[it] = mutableListOf() }
    }

    fun subscribe(type: GameEventType, listener: GameEventListener) =
        events[type]?.add(listener)

    fun unsubscribe(type: GameEventType, listener: GameEventListener) =
        events[type]?.remove(listener)

    fun sendEvent(type: GameEventType) =
        events[type]?.forEach { it.onGameEvent(type.text) }
}

fun main() {
    val eventManager = GameEventManager()

    val gameTextPrintListener: GameEventListener = object : GameEventListener {
        override fun onGameEvent(text: String) {
            println(text)
        }
    }

    val gameRestartListener = object : GameEventListener {
        override fun onGameEvent(text: String) {
            println("You could not beat the level. Restarting...")
        }
    }

    val gameNextLevelListener = object : GameEventListener {
        override fun onGameEvent(text: String) {
            println("Congratulations! Loading next level...")
        }
    }

    // ჩვენ შეგვიძლია დავუობზერვდეთ ჩვენს ივენთ მენეჯერს სხვადასხვა კომპონენტიდან ერთდროულად
    eventManager.subscribe(GameEventType.WIN, gameTextPrintListener)
    eventManager.subscribe(GameEventType.LOSE, gameTextPrintListener)
    eventManager.subscribe(GameEventType.DRAW, gameTextPrintListener)
    eventManager.subscribe(GameEventType.DRAW, gameRestartListener)
    eventManager.subscribe(GameEventType.LOSE, gameRestartListener)
    eventManager.subscribe(GameEventType.WIN, gameNextLevelListener)

    // ჩვენ შეგვიძლია გავუგზავნოთ ივენთი ივენთმენეჯერს სხვადასხვა კომპონენტიდან, რომელიც, თავის მხრივ, გამოიძახებს
    // დაობზერვებული კომპონენტების ივენთ ლისენერებს
    eventManager.sendEvent(GameEventType.WIN)
    eventManager.sendEvent(GameEventType.LOSE)
    eventManager.sendEvent(GameEventType.DRAW)
}

