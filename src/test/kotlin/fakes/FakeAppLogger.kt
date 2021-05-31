package fakes

import it.vashykator.scraper.AppLogger

internal class FakeAppLogger : AppLogger {
    private val loggedInfos = mutableListOf<String>()
    private val loggedErrors = mutableListOf<String>()

    override fun info(message: String) {
        loggedInfos.add(message)
    }

    override fun error(message: String) {
        loggedErrors.add(message)
    }

    fun hasInfoBeenLogged(message: String): Boolean = loggedInfos.contains(message)
    fun hasErrorBeenLogged(message: String): Boolean = loggedErrors.contains(message)
}
