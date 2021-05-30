package it.vashykator.scraper

typealias FindUsers = () -> List<User>
typealias FireNotification = (Comic) -> Unit
typealias FindComics = () -> List<Comic>

interface AppContext {
    val fireNotification: FireNotification
    val findComics: FindComics
    val findUsers: FindUsers
    val logger: AppLogger
}

fun AppContext(): AppContext {
    return AppContextInstance
}

private object AppContextInstance : AppContext {
    override val fireNotification = ::fireTelegramMessages
    override val findComics = { findHardcodedComics() }
    override val findUsers = ::getUsersFromFileSystem
    override val logger = Logger()
}
