package it.vashykator.scraper

import java.time.LocalDateTime
import java.time.ZoneId

interface Logger {
    fun info(message: String)
    fun error(message: String)
}

val logger: Logger = ConsoleLogger()


private class ConsoleLogger : Logger {
    override fun info(message: String) {
        println("${timestamp()} $message")
    }

    override fun error(message: String) {
        System.err.println("${timestamp()} $message")
    }
}

private fun timestamp() = LocalDateTime.now(ZoneId.systemDefault())
