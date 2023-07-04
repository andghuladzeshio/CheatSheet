package design_patterns.creational.factory_method

/**
 * გამოიყენება ერთი და იგივე ტიპის ინსტანსების შესაქმენლად, როდესაც გვინდა, რომ მოცემული ინსტანსების
 * შექმნის ლოგიკა იყოს იზოლირებული.
 *
 * ინსტანსების შექმნის ლოგიკა გატანილია სტატიკურ მეთოდში.
 *
 * მუშაობს დამოკიდებულებიდან გამომდინარე, ანუ, როგორც წესი, ფექტორის ფუნქცია იღებს პარამეტრს, რომელზე დაყრდნობითაც
 * ქმნის ინსტანსებს
 *
 * კარგია ხიდის შესაქმნელად ორ სხვადასხვა ობიექტებს შორის
 * */

enum class Country(val countryName: String) {
    GEORGIA("Georgia"),
    USA("United States of America"),
    GREECE("Greece")
}

data class Currency(val code: String)

object CurrencyMethodFactory {

    fun currencyForCountry(country: Country) = when(country) {
        Country.GEORGIA -> Currency("GEL")
        Country.USA -> Currency("USD")
        Country.GREECE -> Currency("EUR")
    }

}

fun main() {
    val country = Country.GREECE
    val currency = CurrencyMethodFactory.currencyForCountry(country)
    println("Currency for ${country.countryName} is ${currency.code}")
}



