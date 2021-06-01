package it.vashykator.scraper

fun fireNotificationOnEach(comics: Sequence<Comic>, fireNotification: FireNotification) {
    comics.forEach(fireNotification)
}

