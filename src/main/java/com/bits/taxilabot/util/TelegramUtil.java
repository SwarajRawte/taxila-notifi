package com.bits.taxilabot.util;

import java.nio.charset.StandardCharsets;

import org.apache.hc.client5.http.classic.methods.HttpPost;
import org.apache.hc.client5.http.impl.classic.CloseableHttpClient;
import org.apache.hc.client5.http.impl.classic.HttpClients;
import org.apache.hc.core5.http.io.entity.StringEntity;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class TelegramUtil {
    private static final Logger logger = LoggerFactory.getLogger(TelegramUtil.class);
    private static final String BOT_TOKEN = System.getenv("TELEGRAM_BOT_TOKEN");
    private static final String CHAT_ID = System.getenv("TELEGRAM_CHAT_ID");

    public static void sendNotification(TaxilaUtil.Notification notif) {
        String message = String.format("\uD83D\uDCE2 *New Notification*\n*Title:* %s\n*Time:* %s", notif.getTitle(), notif.getTime());
        sendTelegramMessage(message);
    }

    public static void sendTelegramMessage(String message) {
        String url = String.format("https://api.telegram.org/bot%s/sendMessage", BOT_TOKEN);
        String payload = String.format("{\"chat_id\":\"%s\",\"text\":\"%s\",\"parse_mode\":\"Markdown\"}", CHAT_ID, message.replace("\"", "\\\""));
        try (CloseableHttpClient client = HttpClients.createDefault()) {
            HttpPost post = new HttpPost(url);
            post.setEntity(new StringEntity(payload, StandardCharsets.UTF_8));
            post.setHeader("Content-type", "application/json");
            var response = client.execute(post);
            var status = response.getCode();
            var entity = response.getEntity();
            String responseBody = entity != null ? new String(entity.getContent().readAllBytes(), StandardCharsets.UTF_8) : "";
            logger.info("Telegram API response: status={}, body={}", status, responseBody);
        } catch (Exception e) {
            logger.error("Failed to send Telegram message", e);
        }
    }
}
