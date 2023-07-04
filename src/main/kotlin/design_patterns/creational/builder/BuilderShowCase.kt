package design_patterns.creational.builder

/**
 * ბილდერ პატერნი გვადგება, როდესაც დასაინიციალიზებელ ობიექტს გააჩნია მრავალი პარამეტრი/ფილდი შეცვლადი დეფოლტ ველიუებით
 *
 * ბილდერ პატერნი ქმნის ბილდერ ობიექტის მცნებას, რომელიც ზრუნავს საჭირო ფილდების ინიციალიზაციაზე და ბოლოს აბრუნებს
 * შესაქმნელ ობიექტს (როგორც წესი build() ფუნქციის საშუალებით)
 *
 * ბილდერ პატერნი ასევე მოსახერხებელია როდესაც გვსურს ობიექტის პარამეტრების კონფიგურაცია ორი სხვადასხვა ადგილიდან (ან ეტაპობრივად)
 * */

class Car private constructor(builder: Builder) {
    val fuelCapacity: Float = builder.fuelCapacity
    val doorCount: Int = builder.doorCount
    val engineCapacity: Float = builder.engineCapacity

    class Builder {
        var fuelCapacity: Float = 37.5f
            private set
        var doorCount: Int = 4
            private set
        var engineCapacity: Float = 2.0f
            private set

        fun fuelCapacity(capacity: Float) = apply { fuelCapacity = capacity }

        fun doorCount(count: Int) = apply { doorCount = count }

        fun engineCapacity(capacity: Float) = apply { engineCapacity = capacity }

        fun build() = Car(this)
    }
}

fun main() {
    val car = Car
        .Builder()
        .doorCount(2)
        .engineCapacity(4.0f)
        .build()

    println(
        "Fuel capacity - ${car.fuelCapacity}\nDoor count - ${car.doorCount}\nEngine capacity - ${car.engineCapacity}"
    )
}
