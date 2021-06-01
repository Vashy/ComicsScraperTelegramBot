package it.vashykator.scraper

import org.jsoup.nodes.Element

const val timeout = 500L

class Scraper(private val logger: AppLogger) {
    fun scrapAvailables(comics: List<Comic>, getHtmlFrom: GetHtmlFrom): Sequence<Comic> {
        logger.info("Set timeout to: ${timeout}ms")
        return comics
            .asSequence()
            .onEach { Thread.sleep(timeout) }
            .mapNotNull { comic ->
                scrap(comic, comic.webSite.checkComicAvailabilityCallback(), getHtmlFrom)
            }
    }

    private fun scrap(
        comic: Comic,
        isComicAvailable: (Element) -> Boolean,
        getHtmlFrom: GetHtmlFrom,
    ): Comic? {
        val dom = getHtmlFrom(comic.url.toString())
        return when {
            isComicAvailable(dom) -> comic
            else -> {
                logUnavailability(comic)
                null
            }
        }
    }

    private fun logUnavailability(comic: Comic) {
        logger.info("${comic.name}: not available")
    }
}
