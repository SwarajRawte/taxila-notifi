package com.bits.taxilabot;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.bits.taxilabot.util.NotificationChecker;

public class Main {
    private static final Logger logger = LoggerFactory.getLogger(Main.class);

    public static void main(String[] args) {
        com.bits.taxilabot.util.DotenvLoader.loadEnv();
        logger.info("Starting BITS Taxila Notification Bot");
        com.bits.taxilabot.util.TelegramUtil.sendTelegramMessage("🚀 BITS Taxila Notification Bot started and is now running.");
        NotificationChecker checker = new NotificationChecker();
        checker.runBot();
    }
}
