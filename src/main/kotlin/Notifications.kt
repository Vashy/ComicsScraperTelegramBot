package it.vashykator.scraper

import com.github.kotlintelegrambot.bot
import com.github.kotlintelegrambot.entities.ChatId
import com.github.kotlintelegrambot.entities.ParseMode
import java.net.URL

val Bot = bot {
    token = System.getenv(tokenEnvKey)
        ?: throw IllegalArgumentException("Please Provide a valid bot token via Env: $tokenEnvKey")
}

fun fireNotification(comic: Comic) {
    println("${comic.name}: comic available! --> ${comic.url}")
    userIds
        .forEach {
            Bot.sendMessage(ChatId.fromId(it), formatMessage(comic), parseMode = ParseMode.MARKDOWN_V2)
        }
}

private fun formatMessage(comic: Comic): String {
    return "*${comic.name}* \\> Item available\\!\n\n Check it out [here](${escaped(comic.url)})\\!"
}


private fun escaped(url: URL): String = url.toString()
    .replace(Regex("""([_*\[\]()~`>#+\-=|{}.!])"""), "$1")
