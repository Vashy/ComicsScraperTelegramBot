import fakes.FakeFireNotificator
import fakes.SwallowLogsLogger
import it.vashykator.scraper.*
import kotlinx.coroutines.runBlocking
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.condition.EnabledIfSystemProperty
import java.lang.IllegalArgumentException
import java.nio.file.Path

const val TEST_RESOURCES = "src/test/resources"

@EnabledIfSystemProperty(named = "webTest", matches = "true")
internal class ScraperActualWebTest {
    private val fireNotificator = FakeFireNotificator()

    @Test
    internal fun `actual web scraping should match expected available comics`() {
        val testComics = findComicsFromCsv(Path.of(TEST_RESOURCES, "comics.csv"))

        runBlocking {
            fireNotificationOnEach(
                Scraper(SwallowLogsLogger).scrapAvailables(testComics, AppContext().getHtmlFrom),
                fireNotificator.fireNotification(),
            )
        }

        with(fireNotificator) {
            assertTrue(hasBeenNotified(testComics.findByName("One Piece 90")))
            assertTrue(hasBeenNotified(testComics.findByName("Chainsaw Man 4")))
            assertTrue(hasNotBeenNotified(testComics.findByName("Vinland Saga 7")))
            assertTrue(hasNotBeenNotified(testComics.findByName("Homunculus 1")))
        }
    }
}

private fun List<Comic>.findByName(name: String) =
    find { comic -> comic.name == name }
        ?: throw IllegalArgumentException("Comic $name not found in (${this.joinToString(";")})")
