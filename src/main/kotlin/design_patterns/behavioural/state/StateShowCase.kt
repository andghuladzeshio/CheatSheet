package design_patterns.behavioural.state

/**
 * State დიზაინ პატერნი გვეხმარება დავწეროთ ისეთი აპლიკაცა, რომელსაც ექნება შესაძლებლობა შეიცვალოს ქცევა მის შიგნით არსებული
 * state-ს მიხედვით.
 *
 * ნებისმიერ დროს სტეიტების ჩამონათვალი უნდა იყოს სასრული ,ანუ ზუსტად უნდა ვიცოდეთ და აღწერილი გვქონდეს
 * ყველა ის მდგომარეობა, რომელშიც აპლიკაცია შეიძლება გადავიდეს
 *
 * სტეიტი/მდგომარეობა შეიძლება წარმოდგენილი იყოს რაიმე ობიექტის სახით
 *
 * მოცემული მიდგომა ძალიან გვადგება ზუსტი/შემოსაზღვრული ამოცანების გაკეთებაში. რა თქმა უნდა, პატერნი შეიძლება
 * ზოგადი ამოცანების გადაწყვეტაში გამოვიყენოთ, მაგრამ, რაც უფრო კონკრეტულია დავალება, მით უფრო მარტივი და მოსახერხებელია
 * მოცემული მიდგომის დაიმპლემენტირება.
 * */

sealed class FineState {
    data class Open(val dueDate: String): FineState()
    object Appealed: FineState()
    data class Payed(val payDate: String): FineState()
    object Dismissed: FineState()
}

class Fine(val description: String, val amountToPay: Float) {
    private var state: FineState = FineState.Open("current date + 30 days")

    fun payTicket() {
        state = FineState.Payed("Current date")
    }

    fun appealTicket() {
        state = FineState.Appealed
    }

    fun makeTicketOpen() {
        state = FineState.Open("First ticket open date + 30 days")
    }

    fun dismissTicket() {
        state = FineState.Dismissed
    }

    fun getInformation() = when(state) { // როგორც წესი when ბლოკში ქასთი არ უნდა დაგვჭირდეს, მაგრამ ამ შემთხვევაში სმარტ ქასთი არ მუშაობს
        is FineState.Open -> "Ticket is open, you have to pay till ${(state as FineState.Open).dueDate}"
        FineState.Appealed -> "Ticket is appealed, we will reach out to you for further information."
        is FineState.Payed -> "Ticket is payed on ${(state as FineState.Payed).payDate}"
        FineState.Dismissed -> "Your ticket is dismissed!"
    }

}

fun main() {
    val fine = Fine("speeding ticket", 200f)

    println(fine.getInformation())
    fine.appealTicket()
    println(fine.getInformation())
    fine.makeTicketOpen()
    println(fine.getInformation())
    fine.payTicket()
    println(fine.getInformation())
    fine.dismissTicket()
    println(fine.getInformation())
}
