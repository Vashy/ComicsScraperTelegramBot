import fs from 'fs';
import { scrapComics } from './scraper.js';
import TelegramBot from 'node-telegram-bot-api';

const comics = JSON.parse(fs.readFileSync('watching_comics.json'));
const bot = new TelegramBot('TOKEN');

scrapComics(comics, fireNotification);

function fireNotification(comic) {
    console.log(`${comic.name}: item available! --> ${comic.url}`);
    bot.sendMessage(ID, format(comic),
        { parse_mode: 'MarkdownV2' });
}

function format(comic) {
    const message = `*${comic.name}* \\> Item available\\!\n\n${escaped(comic.url)}`;
    return message
}

function escaped(str) {
    const regexp = new RegExp(/([\_\*\[\]\(\)\~\`\>\#\+\-\=\|\{\}\.\!])/g);
    console.log('Normal: ' + str);
    console.log('Escaped:' + str.replace(regexp, '\\$1'));
    return str.replace(regexp, '\\$1');
}