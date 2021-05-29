package it.vashykator.scraper

import java.net.URL

val testComics = listOf(
    Comic(WebSite.STAR_COMICS, URL("https://www.starcomics.com/fumetto/young-301-one-piece-90"), "One Piece 90"), // available
    Comic(WebSite.STAR_COMICS, URL("https://www.starcomics.com/fumetto/action-208-vinland-saga-7"), "Vinland Saga 7"), // not available
    Comic( // available
        WebSite.PANINI_COMICS,
        URL("https://www.panini.it/shp_ita_it/buonanotte-punpun-3-masan006isbnr2-it08.html"),
        "Buonanotte Punpun 3"
    ),
    Comic( // not available
        WebSite.PANINI_COMICS,
        URL("https://www.panini.it/shp_ita_it/homunculus-1-mmgno023isbnr4-it08.html"),
        "Homunculus 1"
    ),
)
