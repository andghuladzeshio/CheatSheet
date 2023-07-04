package design_patterns.structural.bridge

/**
 * Bridge დიზაინ პატერნი გვადგება, როდესაც გვაქვს ერთი და იგივე ობიექტი მრავალი თვისებითა და შესაბამისად იმპლემენტაციებით
 *
 * ამ დროს კლასის თვისებები უნდა დაიშალოს და ამ თვისებებს შორის უნდა დამყარდეს კავშირი
 * */

// არასწორი მიდგომა
interface ColoredShape

class RedRectangle: ColoredShape
class BlueRectangle: ColoredShape
class PinkRectangle: ColoredShape
class RedCircle: ColoredShape
class BlueCircle: ColoredShape
class PinkCircle: ColoredShape

// მიდგომა Bridge პატერნის გამოყენებით
interface Color
interface Shape{
    // ჩვენი ხიდი (Bridge) ColoredShape კლასის ორ თვისებას Shape-სა და Color-ს შორის
    val color: Color
}

class RedColor: Color
class BlueColor: Color
class PinkColor: Color

class Rectangle(override val color: Color): Shape
class Circle(override val color: Color): Shape

/**
 * 2 ფორმისა და 3 ფერის შემთხვევაში პატერნის გამოყენების გარეშე გვქონდა 6 განსხვავებული ობიექტი
 *
 * 3 ფორმისა და 3 ფერის შემთხვევაში ეს რიცხვი 9-მდე გაიზრდებოდა, ხოლო პატერნის გამოყენებით მხოლოდ ერთი კლასის (ფორმის)
 * აღწერა იქნებოდა საჭირო
 * */
