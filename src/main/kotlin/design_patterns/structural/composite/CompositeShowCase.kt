package design_patterns.structural.composite

/**
 * კომპოზიტი არის დიზაინ პატერნი, რომელიც გვეხმარება იერარქიული ობიექტების მენეჯმენტში
 * არ იგულისხმება მემკვიდრეობითი იერარქია, იგულისხმება ერთმანეთთან დაკავშირებული ობიექტების იერარქიული ნაკრები
 *
 * მაგალითად განვიხილოთ კომპიუტერის მაგალითი
 *
 * ამ შემთხვევაში გვაქვს შემდეგი იერარქია
 *
 * კომპიუტერი -> (ვიდეობარათი, პროცესორი, მეხსიერება -> (RAM, ROM))
 *
 * მოცემული კომპონენტები ქმნიან 3 საფეხურიან ხეს (Tree) და სწორედ ასეთ სიტუაციებში არის ხელსაყრელი კომპოზიტის პატერნით
 * სარგებლობა
 * */

interface Component{
    val name: String
    val price: Float
}

interface Composite: Component {

    val subComponents: List<Component>

    override val price: Float get() = subComponents.map { it.price }.sum()

}

class Computer(override val subComponents: List<Component>): Composite {
    override val name: String = "Computer"
}

class Memory(override val subComponents: List<Component>): Composite {
    override val name: String = "Memory"
}

class GraphicsCard: Component {
    // რა თქმა უნდა სტატიკური მნიშვნელობები ზოგადი კლასისთვის არ არის სწორი, მაგრამ ემ ეტაპზე არ აქვს მნიშვნელობა
    override val name: String = "Graphics Card"
    override val price: Float = 1200f
}

class Processor: Component {
    override val name: String = "Processor"
    override val price: Float = 700f
}

class RAM: Component {
    override val name: String = "RAM"
    override val price: Float = 200f
}

class ROM: Component {
    override val name: String = "ROM"
    override val price: Float = 100f
}

/**
 * ამ მაგალითში კომპოზიტს მხოლოდ კომპონენტების ფასის შეჯამების ლოგიკა განვუსაზღვრეთ, მაგრამ შეგვიძლია ნებისმიერი
 * მიზნით გამოვიყენოთ კომპონენტების მენეჯმენტის ჭრილში
 * */
fun main() {
    val ram = RAM()
    val rom = ROM()
    val memory = Memory(listOf(ram, rom))

    val processor = Processor()
    val graphicsCard = GraphicsCard()

    val computer = Computer(listOf(memory, processor, graphicsCard))

    println(computer.price)
}
