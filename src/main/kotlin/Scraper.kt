package it.vashykator.scraper

import org.jsoup.Jsoup
import org.jsoup.nodes.Element

const val timeout = 500L

fun scrapComics(comics: List<Comic>, fireNotification: (Comic) -> Unit) {
    logger.info("Set timeout to: ${timeout}ms")
    comics
        .onEach { Thread.sleep(timeout) }
        .forEach { scrap(it, fireNotification, it.webSite.checkComicAvailabilityCallback()) }
}

fun scrap(comic: Comic, fireNotification: (Comic) -> Unit, isComicAvailable: (Element) -> Boolean) {
    val dom = Jsoup.connect(comic.url.toString()).get().body()
    if (isComicAvailable(dom)) {
        fireNotification(comic)
    } else {
        logUnavailability(comic)
    }
}

fun logUnavailability(comic: Comic) {
    logger.info("${comic.name}: not available")
}
