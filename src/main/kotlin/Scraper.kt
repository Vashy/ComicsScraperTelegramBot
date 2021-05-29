package it.vashykator.scraper

import org.jsoup.Jsoup
import org.jsoup.nodes.Element

const val timeout = 500L

fun scrapComics(watchingComics: List<Comic>, fireNotification: (Comic) -> Unit) {
    println()
    println("Set timeout to: ${timeout}ms")
    watchingComics
        .onEach { Thread.sleep(timeout) }
        .forEach { scrap(it, fireNotification, it.site.comicAvailability()) }
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
    println("${comic.name}: not available")
}
