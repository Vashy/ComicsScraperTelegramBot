# ComicsScraperTelegramBot

## Token

Define an environment variable as follow:

    export MANGA_NOTIFIER_BOT_TOKEN='1842390258:AAGFgXLI20yXdVDD-nmzW0wBdKusqBL208k'

Or in the crontab:

    crontab -e
    
    ...

    # Add the following line
    MANGA_NOTIFIER_BOT_TOKEN='TOKEN'

Create a file `users.json` containing the ids list:

```json
[
  111,
  222
]
```

Start the scraper by running:

    npm start
