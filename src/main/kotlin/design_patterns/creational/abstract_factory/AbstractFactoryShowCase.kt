package design_patterns.creational.abstract_factory

import java.lang.IllegalArgumentException

/**
 * აბსტრაქტული ფექტორი არის ფექტორი მეთოდის მსგავსი მიდგომა, მაგრამ დამატებულია აქვს აბსტრაქციის შრე
 *
 * აბსტრაქტული ფექტორი, ფექტორი მეთოდისგან განსხვავებით, ტრადიციულად არ არის მორგებული რაიმე დატაზე დამოკიდებულებით
 * ობიექტის შექმნაზე, ის მორგებულია კომპონენტების მიწოდებაზე, მისაწოდებელი კომპონენტის ტიპის მიხედვით
 *
 * ყველა მისაწოდებელ კომპონენტს აქვს თავისი საკუთარი ფექტორი, რომელთა ინსტანსები იქმნება ფექტორი მეთოდის პატერნით
 * */

interface ViewHelper {
    fun help()
}

class HomeViewHelper: ViewHelper {
    override fun help() {
        println("Helping home")
    }
}

class ProfileViewHelper: ViewHelper {
    override fun help() {
        println("Helping profile")
    }
}

// კლასიკური მიდგომა, რომელიც გვიბრუნებს გადაცემული ტიპის ფექტორის
abstract class ViewHelperFactoryClassic {
    abstract fun createViewHelper(): ViewHelper

    companion object {
        inline fun <reified T: ViewHelper> createFactory() = when(T::class) {
            HomeViewHelperFactory::class -> HomeViewHelperFactory()
            ProfileViewHelperFactory::class -> ProfileViewHelperFactory()
            else -> throw IllegalArgumentException("Invalid type of helper - ${T::class}")
        }
    }
}

//  მიდგომა, რომელიც პირდაპირ გადაცემული ტიპის ინსტანსს აბრუნებს
abstract class ViewHelperFactory {
    abstract fun createViewHelper(): ViewHelper

    companion object {
        inline fun <reified T: ViewHelper> createFactory(): T = when(T::class) {
            HomeViewHelperFactory::class -> HomeViewHelperFactory().createViewHelper()
            ProfileViewHelperFactory::class -> ProfileViewHelperFactory().createViewHelper()
            else -> throw IllegalArgumentException("Invalid type of helper - ${T::class}")
        } as T
    }
}

class HomeViewHelperFactory: ViewHelperFactoryClassic() {
    override fun createViewHelper(): ViewHelper = HomeViewHelper()
}

class ProfileViewHelperFactory: ViewHelperFactoryClassic() {
    override fun createViewHelper(): ViewHelper = ProfileViewHelper()
}

fun main() {
    // კლასიკური
    val homeViewHelperFactory = ViewHelperFactoryClassic.createFactory<HomeViewHelper>()
    val homeViewHelper = homeViewHelperFactory.createViewHelper()
    homeViewHelper.help()

    // ქასთომ
    val profileViewHelper = ViewHelperFactory.createFactory<ProfileViewHelper>()
    profileViewHelper.help()
}
