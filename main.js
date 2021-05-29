import fs from 'fs';
import { scrap_all } from './scraper.js';

const watching_items_json = JSON.parse(fs.readFileSync('watching_items.json'));

scrap_all(watching_items_json, fire_notification);


function fire_notification(item) {
    console.log(`${item.name}: item available! --> ${item.url}`);
}
