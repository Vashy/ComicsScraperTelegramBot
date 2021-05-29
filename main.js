import fs from 'fs';
import { scrapComics } from './scraper.js';

const comics = JSON.parse(fs.readFileSync('watching_comics.json'));

scrapComics(comics, fireNotification);

function fireNotification(comic) {
    console.log(`${comic.name}: item available! --> ${comic.url}`);
}
