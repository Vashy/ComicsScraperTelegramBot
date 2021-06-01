package it.vashykator.scraper

import org.jsoup.nodes.Element
import java.net.URL

data class Comic(val webSite: WebSite, val url: URL, val name: String) {
    fun isAvailable(dom: Element): Boolean = webSite.isAvailable(dom)
}
