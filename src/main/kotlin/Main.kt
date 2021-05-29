package it.vashykator.scraper

import it.vashykator.scraper.WebSite.STAR_COMICS
import java.net.URL

val userIds = listOf(38883960L)

const val tokenEnvKey = "MANGA_NOTIFIER_BOT_TOKEN"

fun main() {
    scrapComics(comics, ::fireNotification)
}

val comics = listOf(
    Comic(STAR_COMICS, URL("https://www.starcomics.com/fumetto/action-250-vinland-saga-13"), "Vinland Saga 13")
)
