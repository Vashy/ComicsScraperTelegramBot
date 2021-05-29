package it.vashykator.scraper

import org.jsoup.nodes.Element
import java.net.URL

data class Comic(val site: Site, val url: URL, val name: String)

enum class Site {
    PANINI_COMICS,
    STAR_COMICS;

    fun comicAvailability(): (Element) -> Boolean = when (this) {
        PANINI_COMICS -> ::planetMangaAvailability
        STAR_COMICS -> ::starComicsAvailability
    }
}

private fun planetMangaAvailability(dom: Element): Boolean {
    return dom.getElementsByClass("box-tocart").size > 0
}

private fun starComicsAvailability(dom: Element): Boolean {
    return dom.getElementsByClass("dettaglio-fumetto")[0]
        .children()[1]
        .children()[1]
        .children()[1]
        .children()[0]
        .children()[0]
        .hasClass("text-muted")
}
