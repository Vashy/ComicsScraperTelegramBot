package it.vashykator.scraper

import it.vashykator.scraper.WebSite.STAR_COMICS
import java.net.URL
import java.nio.file.Files
import java.nio.file.Path

val USERS_PATH = "src/main/resources/USERS"
val userIds: List<Long> = Files.readAllLines(Path.of(USERS_PATH)).map { it.toLong() }

const val tokenEnvKey = "MANGA_NOTIFIER_BOT_TOKEN"

fun main() {
    scrapComics(comics, ::fireNotification)
}

val comics = listOf(
    Comic(STAR_COMICS, URL("https://www.starcomics.com/fumetto/action-250-vinland-saga-13"), "Vinland Saga 13")
)
