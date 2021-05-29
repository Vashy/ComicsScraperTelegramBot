import got from 'got';
import jsdom from 'jsdom';
import getComicAvailability from './comic_availabilities.js'
const { JSDOM } = jsdom;

const pause = 500;

export const scrapComics = async (watchingComics, fireNotification) => {
    console.log();
    console.log(`Set timeout to: ${pause}ms`);
    for (const comic of watchingComics) {
        await sleep(pause);
        const comicAvailabilityStrategy = getComicAvailability(comic);
        scrap(comic, fireNotification, comicAvailabilityStrategy);
    }
}

function scrap(comic, fireNotification, isComicAvailable) {
    got(comic.url)
        .then(response => {
            const dom = new JSDOM(response.body);
            if (isComicAvailable(dom)) {
                fireNotification(comic);
            } else {
                logUnavailability(comic);
            }
        }).catch(err => {
            console.error(err);
        });
}

function logUnavailability(comic) {
    console.log(`${comic.name}: not available`);
}

function sleep(ms) {
    return new Promise(resolve => setTimeout(resolve, ms));
}
