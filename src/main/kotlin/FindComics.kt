package it.vashykator.scraper

import java.net.URL
import java.nio.file.Files
import java.nio.file.Path
import kotlin.text.Charsets.UTF_8

fun findHardcodedComics(): List<Comic> =
    listOf(
        Comic(
            WebSite.STAR_COMICS,
            URL("https://www.starcomics.com/fumetto/action-250-vinland-saga-13"),
            "Vinland Saga 13"
        )
    )

fun findComicsFromCsv(path: Path): List<Comic> {
    return Files.readAllLines(path, UTF_8)
        .map { it.split(",") }
        .onEach { require(it.size == 3) { "Invalid Csv Format!" } }
        .map { Comic(WebSite.valueOf(it[0]), URL(it[1]), it[2]) }
}
