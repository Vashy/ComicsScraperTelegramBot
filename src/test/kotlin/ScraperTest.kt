import fakes.FakeAppLogger
import fakes.FakeFireNotificator
import it.vashykator.scraper.*
import kotlinx.coroutines.runBlocking
import org.jsoup.Jsoup
import org.jsoup.nodes.Element
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.Test
import java.lang.IllegalArgumentException
import java.net.URL

internal class ScraperTest {
    private val testLogger = FakeAppLogger()
    private val fireNotificator = FakeFireNotificator()

    @Test
    internal fun `should notify only when a comic from Panini Comics is available`() {
        val paniniUnavailable = Comic(PaniniComics, URL("http://panini.unavailable.com"), "Manga 2")
        val paniniAvailable = Comic(PaniniComics, URL("http://panini.available.com"), "Manga 1")
        val comics = listOf(
            paniniUnavailable,
            paniniAvailable,
        )

        callScraper(comics, fakeGetHtmlPaniniComicsFrom())

        assertTrue(testLogger.hasInfoBeenLogged("Set timeout to: ${timeout}ms"), "Should have logged timeout")
        assertTrue(fireNotificator.hasBeenNotified(paniniAvailable), "Should have notified: $paniniAvailable")
        assertTrue(
            fireNotificator.hasNotBeenNotified(paniniUnavailable),
            "Should have not notified: $paniniUnavailable"
        )
        assertTrue(
            testLogger.hasInfoBeenLogged("${paniniUnavailable.name}: not available"),
            "Should have logged unavailability"
        )
    }

    @Test
    internal fun `should notify when a comic from Star Comics is available`() {
        val starComics = Comic(StarComics, URL("http://star-comics.available.com"), "Manga 1")
        val comics = listOf(starComics)

        callScraper(comics, fakeGetHtmlStarComicsFrom())

        assertTrue(fireNotificator.hasBeenNotified(starComics), "Should have notified: $starComics")
    }

    @Test
    internal fun `should not notify when a comic from Star Comics is unavailable`() {
        val starComics = Comic(StarComics, URL("http://star-comics.unavailable.com"), "Manga 1")
        val comics = listOf(starComics)

        callScraper(comics, fakeGetHtmlStarComicsFrom())

        assertTrue(fireNotificator.hasNotBeenNotified(starComics), "Should have notified: $starComics")
    }

    private fun callScraper(comics: List<Comic>, getHtmlFrom: GetHtmlFrom) {
        runBlocking {
            fireNotificationOnEach(
                Scraper(testLogger).scrapAvailables(comics, getHtmlFrom),
                fireNotificator.fireNotification()
            )
        }
    }
}

private fun fakeGetHtmlPaniniComicsFrom(): GetHtmlFrom = ::fakePaniniComicsHtml

private fun fakeGetHtmlStarComicsFrom(): GetHtmlFrom = ::fakeStarComicsHtml

private fun fakePaniniComicsHtml(url: String): Element = when (url) {
    "http://panini.available.com" -> Jsoup.parse("""<div class="box-tocart"></div>""").body()
    "http://panini.unavailable.com" -> Jsoup.parse("""<div class="box-tocartNotPresent"></div>""").body()
    else -> throw IllegalArgumentException("Unexpected URL $url")
}

private fun fakeStarComicsHtml(url: String): Element = when (url) {
    "http://star-comics.available.com" -> Jsoup.parse(htmlStarComicsAvailable()).body()
    "http://star-comics.unavailable.com" -> Jsoup.parse(htmlStarComicsUnavailable()).body()
    else -> throw IllegalArgumentException("Unexpected URL $url")
}

private fun htmlStarComicsAvailable() = """<div class="dettaglio-fumetto">
        |<div></div>
        |<div>
        |   <div></div>
        |   <div>
        |       <div></div>
        |       <div>
        |           <div>
        |               <div class="not-text-muted"></div>
        |           </div>
        |       </div>
        |   </div>
        |</div>
        |</div>""".trimMargin()

private fun htmlStarComicsUnavailable() = """<div class="dettaglio-fumetto">
        |<div></div>
        |<div>
        |   <div></div>
        |   <div>
        |       <div></div>
        |       <div>
        |           <div>
        |               <div class="text-muted"></div>
        |           </div>
        |       </div>
        |   </div>
        |</div>
        |</div>""".trimMargin()
