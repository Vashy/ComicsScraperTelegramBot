package it.vashykator.scraper

import org.jsoup.nodes.Element
import java.lang.IllegalArgumentException

sealed interface WebSite {
    fun isAvailable(dom: Element): Boolean

    companion object {
        fun valueOf(string: String): WebSite = when (string) {
            "PaniniComics" -> PaniniComics
            "StarComics" -> StarComics
            else -> throw IllegalArgumentException("Unrecognized '$string' argument for WebSite factory method")
        }
    }
}

object PaniniComics : WebSite {
    override fun isAvailable(dom: Element): Boolean {
        return dom.getElementsByClass("box-tocart").size > 0
    }
}

object StarComics : WebSite {
    override fun isAvailable(dom: Element): Boolean {
        return !dom.getElementsByClass("dettaglio-fumetto")[0]
            .children()[1]
            .children()[1]
            .children()[1]
            .children()[0]
            .children()[0]
            .hasClass("text-muted")
    }
}
