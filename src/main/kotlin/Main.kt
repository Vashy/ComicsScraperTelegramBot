package it.vashykator.scraper

fun main() {
    println()

    fireNotificationOnEach(
        Scraper(AppContext().logger).scrapAvailables(AppContext().findComics(), AppContext().getHtmlFrom),
        ::fireTelegramMessages
    )
}
