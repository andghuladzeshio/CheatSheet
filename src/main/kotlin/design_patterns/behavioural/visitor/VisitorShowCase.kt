package design_patterns.behavioural.visitor

/**
 * Visitor პატერნი გვეხმარება სხვადასხვა ტიპის ობიექტების მენეჯმენტში, ანუ მოცემული მიდგომა გვაძლევს საშუალებას დავჰენდლოთ
 * ობიექტები მაშინაც კი, თუ მათ ერთმანეთისგან განსხვავებული პარამეტრები გადაეწოდებათ და საერთო არაფერი არ აქვთ acceptVisitor
 * ფუნქციის გარდა, რაც ჩვეულებრივი აბსტრაქციის გამოყენებით შეუძლებელია.
 *
 * იმის მაგივრად, რომ რაიმე ფუნქციონალი (ამ შემთხვევაში თვიური და წლიური გადასახადის დათვლა) ჩავტვირთოთ თვითონ ობიექტში,
 * ამ მიდგომის გამოყენებისას ჩვენ ეს ფუნქციონალი visitor-ში გაგვაქვს.
 *
 * visitor-ის ინტერფეისი ძალიან მჭირდორ არის დაკავშირებული იმ ობიექტებთან, რომლებსაც ის ჰენდლავს. ჩვენ სათითაოდ უნდა
 * შევქმნათ ფუნქცია მათი ყველა ტიპისთვის.
 *
 * მოცემული მიდგომა გამოსადეგია მაშინ, როდესაც გვინდა ერთდროულად დავჰენდლოთ რამდენიმე ობიექტი და აბსტრაქციის გამოყენებით
 * ამის მიღწევა შეუძლებელია, რადგან მათ სხვადასხვა ლოგიკა ესაჭიროებათ სასურველი ფუნქციონალის დასაიმპლემენტირებლად
 * (მაგალითად ამ შემთხვევაში ვხედავთ, რომ [FixedPriceProject]-სა და [TimePricedProject]-ს ვერ დავუთვლით ფასს ერთი
 * ,უნივერსალური, მეთოდის გამოყენებით, რადგან პარამეტრები, რომლებზე დაყრდნობითაც უნდა გამოვიანგარიშოთ ფასი, სრულიად
 * განსხვავებული აქვთ)
 * */

interface ProjectVisitor<out T> {
    fun visit(project: FixedPriceProject): T
    fun visit(project: TimePricedProject): T
}

interface Project {
    fun <T> acceptVisitor(visitor: ProjectVisitor<T>): T
}

class FixedPriceProject(val pricePerMonth: Float): Project {
    override fun <T> acceptVisitor(visitor: ProjectVisitor<T>): T = visitor.visit(this)
}

class TimePricedProject(val hoursPerDay: Float, val daysPerWeek: Int, val pricePerHour: Float): Project {
    override fun <T> acceptVisitor(visitor: ProjectVisitor<T>): T = visitor.visit(this)
}

class MonthlyPriceVisitor: ProjectVisitor<Float> {
    override fun visit(project: FixedPriceProject) = project.pricePerMonth
    override fun visit(project: TimePricedProject) = project.pricePerHour * project.hoursPerDay * project.daysPerWeek * 4
}

class YearlyPriceVisitor: ProjectVisitor<Float> {
    override fun visit(project: FixedPriceProject) = project.pricePerMonth * 12
    override fun visit(project: TimePricedProject) = project.pricePerHour * project.hoursPerDay * project.daysPerWeek * 48
}

fun main() {
    val fixedPriceProject = FixedPriceProject(1000f)
    val timePricedProject = TimePricedProject(8f, 5, 30f)

    val monthlyPriceVisitor = MonthlyPriceVisitor()
    val yearlyPriceVisitor = YearlyPriceVisitor()

    val monthlyPayment = fixedPriceProject.acceptVisitor(monthlyPriceVisitor)
    val yearlyPayment = fixedPriceProject.acceptVisitor(yearlyPriceVisitor)

    val monthlyPayment1 = timePricedProject.acceptVisitor(monthlyPriceVisitor)
    val yearlyPayment1 = timePricedProject.acceptVisitor(yearlyPriceVisitor)

    println(monthlyPayment)
    println(yearlyPayment)

    println(monthlyPayment1)
    println(yearlyPayment1)
}
