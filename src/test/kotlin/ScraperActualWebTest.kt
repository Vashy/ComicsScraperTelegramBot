import fakes.FakeFireNotificator
import fakes.SwallowLogsLogger
import it.vashykator.scraper.*
import it.vashykator.scraper.WebSite.PANINI_COMICS
import it.vashykator.scraper.WebSite.STAR_COMICS
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.condition.EnabledIfSystemProperty
import java.net.URL

private val onePiece =
    Comic(STAR_COMICS, URL("https://www.starcomics.com/fumetto/young-301-one-piece-90"), "One Piece 90")
private val vinlandSaga =
    Comic(STAR_COMICS, URL("https://www.starcomics.com/fumetto/action-208-vinland-saga-7"), "Vinland Saga 7")
private val chainsawMan = Comic(
    PANINI_COMICS,
    URL("https://www.panini.it/shp_ita_it/chainsaw-man-4-mmost014-it08.html"),
    "Chainsaw Man 4"
)
private val homunculus = Comic(
    PANINI_COMICS,
    URL("https://www.panini.it/shp_ita_it/homunculus-1-mmgno023isbnr4-it08.html"),
    "Homunculus 1"
)

@EnabledIfSystemProperty(named = "webTest", matches = "true")
internal class ScraperActualWebTest {
    private val fireNotificator = FakeFireNotificator()

    @Test
    internal fun `actual web scraping should match expected available comics`() {
        val testComics = listOf(onePiece, vinlandSaga, chainsawMan, homunculus)

        fireNotificationOnEach(
            Scraper(SwallowLogsLogger).scrapAvailables(testComics, AppContext().getHtmlFrom),
            fireNotificator.fireNotification(),
        )

        assertTrue(fireNotificator.hasBeenNotified(onePiece))
        assertTrue(fireNotificator.hasBeenNotified(chainsawMan))
        assertTrue(fireNotificator.hasNotBeenNotified(vinlandSaga))
        assertTrue(fireNotificator.hasNotBeenNotified(homunculus))
    }
}
