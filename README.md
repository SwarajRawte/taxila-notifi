# BITS Taxila Notification Bot

A 24×7 Java automation bot that logs into the BITS Taxila portal, checks for new notifications every 10 minutes, and sends alerts to a Telegram bot.

## Features
- Headless Selenium WebDriver automation
- JSoup for HTML parsing
- Telegram Bot API integration
- Cloud-deployable (Render background worker)
- Logging and error handling

## Setup Instructions

### 1. Prerequisites
- Java 17+
- Maven
- Chrome browser (for Selenium)

### 2. Clone and Build
```
git clone <repo-url>
cd bits_elern
mvn clean package
```

### 3. Set Environment Variables
Set the following environment variables (locally or in Render dashboard):
- `TAXILA_USERNAME` — Your Taxila portal username
- `TAXILA_PASSWORD` — Your Taxila portal password
- `TELEGRAM_BOT_TOKEN` — Your Telegram bot token
- `TELEGRAM_CHAT_ID` — Your Telegram chat ID

#### Example (Windows Command Prompt):
```
set TAXILA_USERNAME=your_username
set TAXILA_PASSWORD=your_password
set TELEGRAM_BOT_TOKEN=your_bot_token
set TELEGRAM_CHAT_ID=your_chat_id
```

### 4. Run Locally
```
mvn exec:java -Dexec.mainClass="com.bits.taxilabot.Main"
```

### 5. Deploy on Render
- Add a new **Background Worker** service
- Use this repo as the source
- Set the environment variables in the Render dashboard
- The included `Procfile` will run the bot automatically

## How to Add Telegram Bot Token
- Create a Telegram bot via [@BotFather](https://t.me/BotFather)
- Copy the token and set it as `TELEGRAM_BOT_TOKEN`
- Get your chat ID (e.g., via [@userinfobot](https://t.me/userinfobot)) and set as `TELEGRAM_CHAT_ID`

## Headless Mode
- The bot runs in headless mode by default (see `TaxilaUtil.java`)

## Logging
- Logs are printed to the console and can be viewed in Render logs

## Code Structure
- `Main.java` — Entry point
- `util/NotificationChecker.java` — Main loop and notification logic
- `util/TaxilaUtil.java` — Selenium + JSoup scraping
- `util/TelegramUtil.java` — Telegram API integration

## Notes
- All credentials are read from environment variables for security
- For any issues, check logs for error messages

---
*Beginner friendly. Each step is commented in code for clarity.*
