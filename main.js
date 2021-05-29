import fs from 'fs';
import { scrapComics } from './scraper.js';
import TelegramBot from 'node-telegram-bot-api';

const comics = JSON.parse(fs.readFileSync('watching_comics.json'));
const bot = new TelegramBot(process.env.MANGA_NOTIFIER_BOT_TOKEN);
const userIds = JSON.parse(fs.readFileSync('users.json'));

scrapComics(comics, fireNotification);

function fireNotification(comic) {
    console.log(`${comic.name}: item available! --> ${comic.url}`);
    userIds.forEach(userId => {
        bot.sendMessage(userId, formatMessage(comic), { parse_mode: 'MarkdownV2' });
    })
}

function formatMessage(comic) {
    const message = `*${comic.name}* \\> Item available\\!\n\n Check it hout [here](${escaped(comic.url)})\\!`;
    return message
}

function escaped(str) {
    const regexp = new RegExp(/([\_\*\[\]\(\)\~\`\>\#\+\-\=\|\{\}\.\!])/g);
    return str.replace(regexp, '\\$1');
}