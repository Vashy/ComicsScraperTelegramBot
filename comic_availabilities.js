
export default function getComicAvailability(comic) {
    switch (comic.site) {
        case 'Panini Comics':
            return planetMangaAvailability;
        case 'Star Comics':
            return starComicsAvailability;
        default:
            throw Error('Not recognizeed site: ' + comic.site);
    }
}

function planetMangaAvailability(dom) {
    return dom.window.document.getElementsByClassName('box-tocart').length > 0;
}

function starComicsAvailability(dom) {
    return !dom
        .window
        .document.getElementsByClassName('dettaglio-fumetto')[0]
        .children[1]
        .children[1]
        .children[1]
        .children[0]
        .children[0]
        .classList
        .contains('text-muted');
}
