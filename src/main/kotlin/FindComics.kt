package it.vashykator.scraper

import java.net.URL

fun findHardcodedComics(): List<Comic> =
    listOf(
        Comic(
            WebSite.STAR_COMICS,
            URL("https://www.starcomics.com/fumetto/action-250-vinland-saga-13"),
            "Vinland Saga 13"
        )
    )
