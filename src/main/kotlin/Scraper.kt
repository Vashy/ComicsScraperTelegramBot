package it.vashykator.scraper

import org.jsoup.nodes.Element

const val timeout = 500L

class Scraper(private val logger: AppLogger) {
    fun scrap(comics: List<Comic>, fireNotification: FireNotification, getHtmlFrom: GetHtmlFrom) {
        logger.info("Set timeout to: ${timeout}ms")
        comics
            .onEach { Thread.sleep(timeout) }
            .forEach { comic ->
                scrap(
                    comic,
                    fireNotification,
                    comic.webSite.checkComicAvailabilityCallback(),
                    getHtmlFrom
                )
            }
    }

    private fun scrap(
        comic: Comic,
        fireNotification: FireNotification,
        isComicAvailable: (Element) -> Boolean,
        getHtmlFrom: GetHtmlFrom,
    ) {
        val dom = getHtmlFrom(comic.url.toString())
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
