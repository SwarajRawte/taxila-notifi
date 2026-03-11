package com.bits.taxilabot.util;

import java.util.ArrayList;
import java.util.List;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import io.github.bonigarcia.wdm.WebDriverManager;

public class TaxilaUtil {
    private static final Logger logger = LoggerFactory.getLogger(TaxilaUtil.class);
    private static final String TAXILA_URL = "https://taxila-aws.bits-pilani.ac.in/my/";
    private static final String USERNAME = System.getenv("TAXILA_USERNAME");
    private static final String PASSWORD = System.getenv("TAXILA_PASSWORD");

    public static List<Notification> fetchNotifications() throws Exception {
        WebDriverManager.chromedriver().setup();
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--headless=new", "--no-sandbox", "--disable-gpu", "--window-size=1920,1080");
        WebDriver driver = new ChromeDriver(options);
        List<Notification> notifications = new ArrayList<>();
        try {
            driver.get(TAXILA_URL);
            logger.info("Opened Taxila login page");
            WebElement usernameInput = driver.findElement(By.id("username"));
            WebElement passwordInput = driver.findElement(By.id("password"));
            WebElement loginBtn = driver.findElement(By.id("loginbtn"));
            usernameInput.sendKeys(USERNAME);
            passwordInput.sendKeys(PASSWORD);
            loginBtn.click();
            Thread.sleep(3000);
            driver.get("https://taxila-aws.bits-pilani.ac.in/my/notifications.php");
            Thread.sleep(2000);
            String pageSource = driver.getPageSource();
            Document doc = Jsoup.parse(pageSource);
            var notifElements = doc.select(".notification .notification-title");
            for (var elem : notifElements) {
                String title = elem.text();
                String time = elem.parent().selectFirst(".notification-time").text();
                String id = title + time;
                notifications.add(new Notification(id, title, time));
            }
            logger.info("Fetched {} notifications", notifications.size());
        } finally {
            driver.quit();
        }
        return notifications;
    }

    public static class Notification {
        private final String id;
        private final String title;
        private final String time;
        public Notification(String id, String title, String time) {
            this.id = id;
            this.title = title;
            this.time = time;
        }
        public String getId() { return id; }
        public String getTitle() { return title; }
        public String getTime() { return time; }
    }
}
