package it.vashykator.scraper

import kotlinx.coroutines.runBlocking

fun main() {
    runBlocking {
        fireNotificationOnEach(
            Scraper(AppContext().logger).scrapAvailables(AppContext().findComics(), AppContext().getHtmlFrom),
            AppContext().fireNotification,
        )
    }
}
