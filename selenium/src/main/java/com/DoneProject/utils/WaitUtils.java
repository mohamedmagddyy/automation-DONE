package com.DoneProject.utils;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class WaitUtils {

    public static void waitForElementToBeVisible(WebDriver driver, By locator, int timeout) {
        new WebDriverWait(driver, Duration.ofSeconds(timeout))
                .until(ExpectedConditions.visibilityOfElementLocated(locator));
    }

    public static WebElement waitForElementToBeClickable(WebDriver driver, By locator, int timeout) {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(timeout));
        return wait.until(ExpectedConditions.elementToBeClickable(locator));
    }

    public static void waitForElementToDisappear(WebDriver driver, By locator, int timeout) {
        try {
            new WebDriverWait(driver, Duration.ofSeconds(timeout))
                    .until(ExpectedConditions.invisibilityOfElementLocated(locator));
        } catch (Exception ignored) {
        }
    }

    public static void waitForPageToBeReady(WebDriver driver, int timeout) {
        By loader = By.cssSelector("div.loading, div.ng-star-inserted.loading");
        By toast = By.cssSelector("div.toast-message");
        By overlay = By.cssSelector(".modal-backdrop, .cdk-overlay-backdrop-showing");

        waitForElementToDisappear(driver, loader, timeout);
        waitForElementToDisappear(driver, toast, timeout);
        waitForElementToDisappear(driver, overlay, timeout);
    }

}

