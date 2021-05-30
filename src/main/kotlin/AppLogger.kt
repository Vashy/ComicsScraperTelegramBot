package it.vashykator.scraper

import java.time.LocalDateTime
import java.time.ZoneId

interface AppLogger {
    fun info(message: String)
    fun error(message: String)
}

fun Logger(): AppLogger = ConsoleAppLogger()


private class ConsoleAppLogger : AppLogger {
    override fun info(message: String) = println("${timestamp()} INFO $message")

    override fun error(message: String) = System.err.println("${timestamp()} ERROR $message")
}

private fun timestamp() = LocalDateTime.now(ZoneId.systemDefault())
