import fakes.FakeAppLogger
import fakes.FakeFireNotificator
import it.vashykator.scraper.*
import it.vashykator.scraper.WebSite.PANINI_COMICS
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
        val paniniUnavailable = Comic(PANINI_COMICS, URL("http://panini.unavailable.com"), "Manga 2")
        val paniniAvailable = Comic(PANINI_COMICS, URL("http://panini.available.com"), "Manga 1")
        val comics = listOf(
            paniniUnavailable,
            paniniAvailable,
        )

        runBlocking {
            fireNotificationOnEach(
                Scraper(testLogger).scrapAvailables(comics, fakeGetHtmlFrom()),
                fireNotificator.fireNotification()
            )
        }

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
}

fun fakeGetHtmlFrom(): GetHtmlFrom = ::fakePaniniComicsHtmlUnavailable

fun fakePaniniComicsHtmlUnavailable(url: String): Element = when (url) {
    "http://panini.available.com" -> Jsoup.parse("""<div class="box-tocart"></div>""").body()
    "http://panini.unavailable.com" -> Jsoup.parse("""<div class="box-tocartNotPresent"></div>""").body()
    else -> throw IllegalArgumentException("Unexpected URL $url")
}
