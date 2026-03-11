package com.bits.taxilabot.util;

import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.TimeUnit;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class NotificationChecker {
    private static final Logger logger = LoggerFactory.getLogger(NotificationChecker.class);
    private static final int INTERVAL_MINUTES = 10;
    private final Set<String> seenNotifications = new HashSet<>();

    public void runBot() {
        while (true) {
            try {
                logger.info("Checking for new notifications...");
                var notifications = TaxilaUtil.fetchNotifications();
                for (var notif : notifications) {
                    if (!seenNotifications.contains(notif.getId())) {
                        logger.info("New notification found: {}", notif.getTitle());
                        TelegramUtil.sendNotification(notif);
                        seenNotifications.add(notif.getId());
                    }
                }
            } catch (Exception e) {
                logger.error("Error during notification check", e);
            }
            try {
                TimeUnit.MINUTES.sleep(INTERVAL_MINUTES);
            } catch (InterruptedException ie) {
                logger.warn("Sleep interrupted", ie);
                Thread.currentThread().interrupt();
            }
        }
    }
}
