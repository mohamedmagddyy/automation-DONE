package com.DoneProject.helpers;

import org.openqa.selenium.*;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.Duration;

public class ActionBot {

    private final WebDriver driver;
    private final WebDriverWait wait;
    private final Actions actions;

    private static final Logger logger  = LoggerFactory.getLogger(ActionBot.class);
    private static final int    TIMEOUT = 20;

    public ActionBot(WebDriver driver) {
        this.driver  = driver;
        this.wait    = new WebDriverWait(driver, Duration.ofSeconds(TIMEOUT));
        this.actions = new Actions(driver);
        logger.info("ActionBot initialized");
    }

    public void click(By locator) {
        try {
            logger.debug("Waiting for clickable element: {}", locator);
            wait.until(ExpectedConditions.elementToBeClickable(locator)).click();
            logger.info("Clicked: {}", locator);
        } catch (Exception e) {
            logger.error("Failed to click element: {} - {}", locator, e.getMessage());
            throw e;
        }
    }

    public void type(By locator, String text) {
        try {
            logger.debug("Waiting for visible element to type into: {}", locator);
            WebElement element = wait.until(ExpectedConditions.visibilityOfElementLocated(locator));
            element.sendKeys(Keys.CONTROL + "a");
            element.sendKeys(Keys.DELETE);
            element.sendKeys(text);
            logger.info("Typed: [{}] into {}", text, locator);
        } catch (Exception e) {
            logger.error("Failed to type '{}' into element: {} - {}", text, locator, e.getMessage());
            throw e;
        }
    }

    public void jsClick(By locator) {
        try {
            logger.debug("Waiting for element presence for JS click: {}", locator);
            WebElement element = wait.until(ExpectedConditions.presenceOfElementLocated(locator));
            ((JavascriptExecutor) driver).executeScript("arguments[0].click();", element);
            logger.info("JS Clicked: {}", locator);
        } catch (Exception e) {
            logger.error("Failed JS click on element: {} - {}", locator, e.getMessage());
            throw e;
        }
    }

    public void scrollTo(By locator) {
        try {
            logger.debug("Scrolling to element: {}", locator);
            WebElement element = wait.until(ExpectedConditions.visibilityOfElementLocated(locator));
            ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", element);
            logger.debug("Scrolled to: {}", locator);
        } catch (Exception e) {
            logger.error("Failed to scroll to element: {} - {}", locator, e.getMessage());
            throw e;
        }
    }

    public String getText(By locator) {
        try {
            logger.debug("Reading text from element: {}", locator);
            String text = wait.until(ExpectedConditions.visibilityOfElementLocated(locator)).getText();
            logger.debug("Text read from {}: [{}]", locator, text);
            return text;
        } catch (Exception e) {
            logger.error("Failed to read text from element: {} - {}", locator, e.getMessage());
            throw e;
        }
    }

    public void hover(By locator) {
        try {
            logger.debug("Hovering on element: {}", locator);
            WebElement element = wait.until(ExpectedConditions.visibilityOfElementLocated(locator));
            actions.moveToElement(element).perform();
            logger.info("Hovered on: {}", locator);
        } catch (Exception e) {
            logger.error("Failed to hover on element: {} - {}", locator, e.getMessage());
            throw e;
        }
    }

    public void doubleClick(By locator) {
        try {
            logger.debug("Double-clicking element: {}", locator);
            WebElement element = wait.until(ExpectedConditions.elementToBeClickable(locator));
            actions.doubleClick(element).perform();
            logger.info("Double clicked: {}", locator);
        } catch (Exception e) {
            logger.error("Failed to double-click element: {} - {}", locator, e.getMessage());
            throw e;
        }
    }

    public void rightClick(By locator) {
        try {
            logger.debug("Right-clicking element: {}", locator);
            WebElement element = wait.until(ExpectedConditions.elementToBeClickable(locator));
            actions.contextClick(element).perform();
            logger.info("Right clicked: {}", locator);
        } catch (Exception e) {
            logger.error("Failed to right-click element: {} - {}", locator, e.getMessage());
            throw e;
        }
    }

    public void dragAndDrop(By source, By target) {
        try {
            logger.debug("Dragging from {} to {}", source, target);
            WebElement src = wait.until(ExpectedConditions.visibilityOfElementLocated(source));
            WebElement trg = wait.until(ExpectedConditions.visibilityOfElementLocated(target));
            actions.dragAndDrop(src, trg).perform();
            logger.info("Dragged from {} to {}", source, target);
        } catch (Exception e) {
            logger.error("Failed drag-and-drop from {} to {} - {}", source, target, e.getMessage());
            throw e;
        }
    }

    public void selectOptionByVisibleText(By locator, String text) {
        try {
            logger.debug("Selecting option [{}] from dropdown: {}", text, locator);
            WebElement element = wait.until(ExpectedConditions.visibilityOfElementLocated(locator));
            new Select(element).selectByVisibleText(text);
            logger.info("Selected: [{}] from {}", text, locator);
        } catch (Exception e) {
            logger.error("Failed to select option [{}] from: {} - {}", text, locator, e.getMessage());
            throw e;
        }
    }

    public boolean isElementVisible(By locator) {
        try {
            return wait.until(ExpectedConditions.visibilityOfElementLocated(locator)).isDisplayed();
        } catch (Exception e) {
            return false;
        }
    }
}