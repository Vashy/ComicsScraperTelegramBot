import got from 'got';
import jsdom from 'jsdom';
const { JSDOM } = jsdom;

const pause = 2000;

export const scrap_all = async (watching_items, fire_notification) => {
    for (const item of watching_items) {
        console.log('Starting timeout: ' + pause);
        await sleep(pause);
        scrap(item, fire_notification);
    }
}

export function scrap(item, fire_notification) {
    got(item.url)
        .then(response => {
            const dom = new JSDOM(response.body);
            if (is_item_available(dom)) {
                fire_notification(item);
            } else {
                console.log(`${item.name}: not available`)
            }
        }).catch(err => {
            console.log(err);
        });
}

export function is_item_available(dom) {
    return !dom
        .window
        .document
        .getElementsByClassName("fumetto-card")[0]
        .children[2]
        .children[0]
        .children[1]
        .children[0]
        .children[1]
        .children[0]
        .classList.contains('text-muted');
}

export function sleep(ms) {
    return new Promise(resolve => setTimeout(resolve, ms));
}