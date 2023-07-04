package design_patterns.behavioural.chain_of_responsibility

/**
 * Chain of responsibility პატერნი გამოიყენება რამდენიმე, ერთმანეთზე დამოკიდებული, ფუნქციონალის სინქრონულად
 * მუშაობისთვის.
 *
 * მოცემულ პატერნში გვაქვს ჯაჭვი, რომელიც შედგენილია ცალკეული, სხვადასხვა დანიშნულების, კომპონენტებისგან.
 * */

interface HTMLRequestChain {
    var next: HTMLRequestChain?
    fun createRequest(url: String): String
}

/**
 * მოცემულ შემთხვევაში ჯაჭვის ყველა ობიექტში გვაქვს მსგავსი ლოგიკა, მაგრამ ეს პატერნი არ ითვალისწინებს მხოლოდ
 * ასეთ შემთხვევებს და ზოგადად ნებისმიერი ჯაჭვურ პროცესზე შეგვიძლია მოვარგოთ.
 * */
class ContentTypeHeader(private val contentType: String): HTMLRequestChain {
    override var next: HTMLRequestChain? = null

    override fun createRequest(url: String) =
        "$url?contentType=$contentType".let {
            next?.createRequest(it) ?: it
        }
}

class TokenHeader(private val token: String) : HTMLRequestChain {
    override var next: HTMLRequestChain? = null

    override fun createRequest(url: String) =
        "$url?token=$token".let {
            next?.createRequest(it) ?: it
        }
}

class EnvironmentHeader(private val environment: String) : HTMLRequestChain {
    override var next: HTMLRequestChain? = null

    override fun createRequest(url: String) =
        "$url?environment=$environment".let {
            next?.createRequest(it) ?: it
        }
}

fun main() {
    val contentTypeHeader = ContentTypeHeader("JSON")
    val tokenHeader = TokenHeader("a2asd2##@!")
    val environmentHeader = EnvironmentHeader("dev")

    val baseUrl = "https://google.com"
    contentTypeHeader.next = tokenHeader
    tokenHeader.next = environmentHeader

    val url = contentTypeHeader.createRequest(baseUrl)
    println(url)
}
