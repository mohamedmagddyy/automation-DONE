package com.DoneProject.utils;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.Duration;

public class WaitUtils {

    private static final Logger logger = LoggerFactory.getLogger(WaitUtils.class);

    // ✅ منع إنشاء instance — Utility class
    private WaitUtils() {}

    // ================= Visible =================
    public static void waitForElementToBeVisible(WebDriver driver, By locator, int timeoutSeconds) {
        logger.debug("⏳ انتظار ظهور العنصر: {}", locator);
        new WebDriverWait(driver, Duration.ofSeconds(timeoutSeconds))
                .until(ExpectedConditions.visibilityOfElementLocated(locator));
    }

    // ================= Clickable =================
    public static WebElement waitForElementToBeClickable(WebDriver driver, By locator, int timeoutSeconds) {
        logger.debug("⏳ انتظار قابلية النقر: {}", locator);
        return new WebDriverWait(driver, Duration.ofSeconds(timeoutSeconds))
                .until(ExpectedConditions.elementToBeClickable(locator));
    }

    // ================= Invisible =================
    public static void waitForElementToDisappear(WebDriver driver, By locator, int timeoutSeconds) {
        try {
            new WebDriverWait(driver, Duration.ofSeconds(timeoutSeconds))
                    .until(ExpectedConditions.invisibilityOfElementLocated(locator));
            logger.debug("✅ اختفى العنصر: {}", locator);
        } catch (Exception e) {
            logger.debug("⚠️ انتهت المهلة أو العنصر غير موجود أصلاً: {}", locator);
        }
    }

    // ================= Present in DOM =================
    public static WebElement waitForElementToBePresent(WebDriver driver, By locator, int timeoutSeconds) {
        logger.debug("⏳ انتظار وجود العنصر في DOM: {}", locator);
        return new WebDriverWait(driver, Duration.ofSeconds(timeoutSeconds))
                .until(ExpectedConditions.presenceOfElementLocated(locator));
    }

    // ================= Page Ready =================
    public static void waitForPageToBeReady(WebDriver driver, int timeoutSeconds) {
        // 1) document.readyState
        new WebDriverWait(driver, Duration.ofSeconds(timeoutSeconds))
                .until(webDriver ->
                        ((org.openqa.selenium.JavascriptExecutor) webDriver)
                                .executeScript("return document.readyState")
                                .equals("complete"));

        // 2) انتظار اختفاء اللودر والـ overlay والـ toast
        By loader  = By.cssSelector("div.loading, div.ng-star-inserted.loading");
        By overlay = By.cssSelector(".modal-backdrop, .cdk-overlay-backdrop-showing");

        waitForElementToDisappear(driver, loader,  timeoutSeconds);
        waitForElementToDisappear(driver, overlay, timeoutSeconds);

        logger.debug("✅ الصفحة جاهزة");
    }

    // ================= URL Contains =================
    public static void waitForUrlToContain(WebDriver driver, String urlFragment, int timeoutSeconds) {
        logger.debug("⏳ انتظار URL يحتوي على: {}", urlFragment);
        new WebDriverWait(driver, Duration.ofSeconds(timeoutSeconds))
                .until(ExpectedConditions.urlContains(urlFragment));
    }

    // ================= Text in Element =================
    public static void waitForTextInElement(WebDriver driver, By locator, String text, int timeoutSeconds) {
        logger.debug("⏳ انتظار ظهور النص '{}' في: {}", text, locator);
        new WebDriverWait(driver, Duration.ofSeconds(timeoutSeconds))
                .until(ExpectedConditions.textToBePresentInElementLocated(locator, text));
    }
}