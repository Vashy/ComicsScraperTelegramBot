package it.vashykator.scraper

import org.jsoup.Jsoup
import org.jsoup.nodes.Element
import java.nio.file.Path

typealias FindUsers = () -> List<User>
typealias FireNotification = (Comic) -> Unit
typealias FindComics = () -> List<Comic>
typealias GetHtmlFrom = (url: String) -> Element

const val RESOURCES = "src/main/resources"

interface AppContext {
    val fireNotification: FireNotification
    val findComics: FindComics
    val findUsers: FindUsers
    val getHtmlFrom: GetHtmlFrom
    val logger: AppLogger
}

fun AppContext(): AppContext {
    return AppContextInstance
}

private object AppContextInstance : AppContext {
    override val fireNotification = ::fireTelegramMessages
    override val findComics = { findComicsFromCsv(Path.of(RESOURCES, "comics.csv")) }
    override val findUsers = ::readUsersFromFileSystem
    override val getHtmlFrom = ::getHtmlFromWithJsoup
    override val logger = Logger()
}

private fun getHtmlFromWithJsoup(url: String): Element = Jsoup.connect(url).get().body()
