# ComicsScraperTelegramBot

## Token

Define an environment variable as follow:

    export MANGA_NOTIFIER_BOT_TOKEN='1842390258:AAGFgXLI20yXdVDD-nmzW0wBdKusqBL208k'

Create a file `USERS` in the `src/main/resources` folder containing the ids list:

```
111
222
```

Start the scraper by running:

    ./gradlw run

The job needs to be crontabbed to persist over time. For example:

    crontab -e

    ...

    # Add the following lines
    MANGA_NOTIFIER_BOT_TOKEN='YOUR TOKEN'
    
    # Runs the script every 10 minutes
    # you can configure the path to app.log as you wish
    */10 * * * * `/path/to/ComicsScraperTelegramBot/gradlew -p /path/to/ComicsScraperTelegramBot run --warning-mode none  --quiet >> /path/to/ComicsScraperTelegramBot/app.log 2>&1`
