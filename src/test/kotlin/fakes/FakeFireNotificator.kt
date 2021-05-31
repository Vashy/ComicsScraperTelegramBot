import it.vashykator.scraper.Comic
import it.vashykator.scraper.FireNotification

internal class FakeFireNotificator {
    private val notifiedMangas = mutableListOf<Comic>()

    fun fireNotification(): FireNotification = {
        notifiedMangas.add(it)
    }

    fun hasBeenNotified(comic: Comic): Boolean = notifiedMangas.contains(comic)
    fun hasNotBeenNotified(comic: Comic): Boolean = !notifiedMangas.contains(comic)
}
