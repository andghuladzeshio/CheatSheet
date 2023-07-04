package design_patterns.structural.adapter

/**
 * ადაპტერ პატერნი გამოიყენება ორი სხვადასხვა კომპონენტის/ფუნქციონალის ერთმანეთთან დასაკავშირებლად
 *
 * ადაპტერი "თარგმნის" ჩვენს ფუნქციონალს და გვაძლევს საშუალებას ვიურთიერთოდ ცალკე მდგომ კომპონენტებთან
 *
 * გამოსადეგია, როდესაც ამ კომპონენტებში კოდის შეცვლა არ გვსურს ან არ შეგვიძლია ჩვენი დაწერილი კომპონენტის
 * ფუნქციონალზე მოსარგებად. ასევე არც ჩვენი დაწერილი კომპონენტი გვინდა რომ პირდაპირ მოვარგოთ მოცემულ ცალკე
 * მდგომ კომპონენტს.
 * */

// 3rd party კოდი
class BoxedListPrinter {
    fun print(list: List<String>) {
        val maxLength = list.maxBy { it.length }.length
        print(" ")
        printHorizontalWall(maxLength + 2)
        print("\n")

        list.forEach {
            print("| ")
            val endSpaceCount = maxLength - it.length
            print(it)
            (1..endSpaceCount).forEach { _ ->
                print(" ")
            }
            print(" |\n")
        }

        print(" ")
        printHorizontalWall(maxLength + 2)
    }

    private fun printHorizontalWall(length: Int) = (1..length).forEach { _ ->
        print("-")
    }
}
//-------------------------------------------------------------------------------------
data class Person(val firstName: String, val lastName: String)

class PersonsDataSource {
    fun getPersonsList() = listOf(
        Person("შიო", "ანდღულაძე"),
        Person("გიო", "მამაგულაშვილი"),
        Person("გიო", "ქემოკლიძე"),
        Person("სანდრო", "გალორე-ბარამიძე")
    )
}

/**
 * მოცემულ მაგალითში ადაპტერი უბრალო მაპინგის ფუნქციონალს იღებს თავის თავზე, მაგრამ პრინციპი იგივე რჩება -
 * ჩვენ გარკვეული ფუნქციონალი არ მივაბით მოდელს და ისე შევძელით დაგვეკავშირებინა გარე მდგომ ლაიბრერისთან
 * */
class BoxedListPrinterAdapter {
    private val printer = BoxedListPrinter()

    fun printBoxedFullNames(persons: List<Person>) {
        val mappedNames = persons.map { "${it.firstName} ${it.lastName}" }
        printer.print(mappedNames)
    }

}

fun main() {
    val dataSource = PersonsDataSource()
    val persons = dataSource.getPersonsList()
    val adapter = BoxedListPrinterAdapter()
    adapter.printBoxedFullNames(persons)
}