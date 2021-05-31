package it.vashykator.scraper

fun main() {
    println()

    Scraper(AppContext().logger)
        .scrap(
            AppContext().findComics(),
            AppContext().fireNotification,
            AppContext().getHtmlFrom,
        )
}
