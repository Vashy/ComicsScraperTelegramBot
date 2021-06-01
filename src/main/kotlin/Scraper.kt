package it.vashykator.scraper

import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.delay
import org.jsoup.nodes.Element

const val timeout = 500L

class Scraper(private val logger: AppLogger) {
    suspend fun scrapAvailables(comics: List<Comic>, getHtmlFrom: GetHtmlFrom): Sequence<Comic> {
        logger.info("Set timeout to: ${timeout}ms")

        return comics
            .groupBy { it.webSite }
            .flatMap { entry ->
                coroutineScope {
                    entry.value
                        .mapNotNull { comic -> scrap(comic, getHtmlFrom) }
                        .onEach { delay(timeout) }
                        .onEach { logger.info("Context: $coroutineContext") }
                }
            }
            .asSequence()
    }

    private fun scrap(comic: Comic, getHtmlFrom: GetHtmlFrom): Comic? {
        val dom = getHtmlFrom(comic.url.toString())
        return when {
            comic.isAvailable(dom) -> comic
            else -> {
                logUnavailability(comic)
                null
            }
        }
    }

    private fun Comic.isAvailable(dom: Element) = webSite.checkComicAvailabilityCallback()(dom)

    private fun logUnavailability(comic: Comic) {
        logger.info("${comic.name}: not available")
    }
}
