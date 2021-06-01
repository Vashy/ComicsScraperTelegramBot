import fakes.FakeFireNotificator
import fakes.SwallowLogsLogger
import it.vashykator.scraper.*
import kotlinx.coroutines.runBlocking
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.condition.EnabledIfSystemProperty
import java.net.URL

@EnabledIfSystemProperty(named = "webTest", matches = "true")
internal class ScraperActualWebTest {
    private val fireNotificator = FakeFireNotificator()

    @Test
    internal fun `actual web scraping should match expected available comics`() {
        val testComics = listOf(onePiece, vinlandSaga, chainsawMan, homunculus)

        runBlocking {
            fireNotificationOnEach(
                Scraper(SwallowLogsLogger).scrapAvailables(testComics, AppContext().getHtmlFrom),
                fireNotificator.fireNotification(),
            )
        }

        with(fireNotificator) {
            assertTrue(hasBeenNotified(onePiece))
            assertTrue(hasBeenNotified(chainsawMan))
            assertTrue(hasNotBeenNotified(vinlandSaga))
            assertTrue(hasNotBeenNotified(homunculus))
        }
    }
}

private val onePiece =
    Comic(StarComics, URL("https://www.starcomics.com/fumetto/young-301-one-piece-90"), "One Piece 90")
private val vinlandSaga =
    Comic(StarComics, URL("https://www.starcomics.com/fumetto/action-208-vinland-saga-7"), "Vinland Saga 7")
private val chainsawMan = Comic(
    PaniniComics,
    URL("https://www.panini.it/shp_ita_it/chainsaw-man-4-mmost014-it08.html"),
    "Chainsaw Man 4"
)
private val homunculus = Comic(
    PaniniComics,
    URL("https://www.panini.it/shp_ita_it/homunculus-1-mmgno023isbnr4-it08.html"),
    "Homunculus 1"
)
