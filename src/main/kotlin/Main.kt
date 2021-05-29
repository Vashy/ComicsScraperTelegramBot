package it.vashykator.scraper

import com.github.kotlintelegrambot.bot
import com.github.kotlintelegrambot.entities.ChatId
import com.github.kotlintelegrambot.entities.ParseMode.MARKDOWN_V2
import it.vashykator.scraper.Site.PANINI_COMICS
import it.vashykator.scraper.Site.STAR_COMICS
import java.net.URL

val userIds = listOf(38883960L)

const val tokenKey = "MANGA_NOTIFIER_BOT_TOKEN"

val Bot = bot {
    token = System.getenv(tokenKey)
        ?: throw IllegalArgumentException("Please Provide a valid bot token via Env: $tokenKey")
}

fun fireNotification(comic: Comic) {
    println("${comic.name}: item available! --> ${comic.url}")
    userIds
        .forEach {
            Bot.sendMessage(ChatId.fromId(it), formatMessage(comic), parseMode = MARKDOWN_V2)
        }
}

fun formatMessage(comic: Comic): String {
    return "*${comic.name}* \\> Item available\\!\n\n Check it out [here](${escaped(comic.url)})\\!"
}

fun escaped(url: URL): String = url.toString()
    .replace(Regex("""([_*\[\]()~`>#+\-=|{}.!])"""), "$1")


fun main() {
    scrapComics(comics, ::fireNotification)
}

val comics = listOf(
    Comic(STAR_COMICS, URL("https://www.starcomics.com/fumetto/young-301-one-piece-90"), "One Piece 90"),
    Comic(STAR_COMICS, URL("https://www.starcomics.com/fumetto/action-208-vinland-saga-7"), "Vinland Saga 7"),
    Comic(
        PANINI_COMICS,
        URL("https://www.panini.it/shp_ita_it/buonanotte-punpun-3-masan006isbnr2-it08.html"),
        "Buonanotte Punpun 3"
    ),
    Comic(
        PANINI_COMICS,
        URL("https://www.panini.it/shp_ita_it/homunculus-1-mmgno023isbnr4-it08.html"),
        "Homunculus 1"
    ),
)
