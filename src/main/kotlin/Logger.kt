package it.vashykator.scraper

import java.time.LocalDateTime
import java.time.ZoneId

object Logger {
    fun info(message: String) {
        println("${timestamp()} $message")
    }

    fun error(message: String) {
        System.err.println("${timestamp()} $message")
    }
}

private fun timestamp() = LocalDateTime.now(ZoneId.systemDefault())
