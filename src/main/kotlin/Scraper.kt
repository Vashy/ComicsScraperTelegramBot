package it.vashykator.scraper

import org.jsoup.Jsoup
import org.jsoup.nodes.Element

const val timeout = 500L

class Scraper(private val logger: AppLogger) {
    fun scrap(comics: List<Comic>, fireNotification: (Comic) -> Unit) {
        logger.info("Set timeout to: ${timeout}ms")
        comics
            .onEach { Thread.sleep(timeout) }
            .forEach { comic -> scrap(comic, fireNotification, comic.webSite.checkComicAvailabilityCallback()) }
    }

    private fun scrap(comic: Comic, fireNotification: (Comic) -> Unit, isComicAvailable: (Element) -> Boolean) {
        val dom = Jsoup.connect(comic.url.toString()).get().body()
        if (isComicAvailable(dom)) {
            fireNotification(comic)
        } else {
            logUnavailability(comic)
        }
    }

    private fun logUnavailability(comic: Comic) {
        logger.info("${comic.name}: not available")
    }
}
