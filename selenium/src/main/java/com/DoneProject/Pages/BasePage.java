package com.DoneProject.Pages;

import com.DoneProject.drivers.WebDriverFactory;
import com.DoneProject.helpers.ActionBot;
import com.DoneProject.utils.WaitUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.Select;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;

public class BasePage {

    protected WebDriver driver;
    protected ActionBot actionBot;

    private static final Logger logger = LoggerFactory.getLogger(BasePage.class);

    private static final int DEFAULT_TIMEOUT = 10;

    private final By toastContainer = By.id("toast-container");
    private final By toastAny       = By.cssSelector(
            "#toast-container .ngx-toastr, #toast-container .toast-message, .toast-success, .toast-error"
    );

    public BasePage() {
        this.driver    = WebDriverFactory.getDriver();
        this.actionBot = new ActionBot(driver);
        logger.info("BasePage initialized");
    }

    protected void click(By locator) {
        actionBot.click(locator);
    }

    protected void sendKeys(By locator, String text) {
        actionBot.type(locator, text);
    }

    protected WebElement waitForElement(By locator) {
        return WaitUtils.waitForElementToBeClickable(driver, locator, DEFAULT_TIMEOUT);
    }

    protected void selectFromDropdown(By locator, String value) {
        WaitUtils.waitForElementToBeVisible(driver, locator, DEFAULT_TIMEOUT);
        WebElement element = WaitUtils.waitForElementToBePresent(driver, locator, DEFAULT_TIMEOUT);
        new Select(element).selectByVisibleText(value);
        logger.debug("Selected '{}' from dropdown: {}", value, locator);
    }

    protected void jsClick(By locator) {
        actionBot.jsClick(locator);
    }

    protected void scrollTo(By locator) {
        actionBot.scrollTo(locator);
    }

    protected String getText(By locator) {
        return actionBot.getText(locator);
    }

    protected void waitForPageToBeReady() {
        WaitUtils.waitForPageToBeReady(driver, 20);
    }

    public void waitForToastToAppear() {
        WaitUtils.waitForElementToBeVisible(driver, toastAny, DEFAULT_TIMEOUT);
    }

    public List<String> getAllToastMessages() {
        waitForToastToAppear();
        List<WebElement> toasts = driver.findElements(toastAny);
        List<String> messages = new ArrayList<>();
        for (WebElement toast : toasts) {
            String txt = toast.getText().trim();
            if (!txt.isEmpty()) {
                messages.add(txt);
                logger.debug("Toast message: {}", txt);
            }
        }
        if (!messages.isEmpty()) {
            logger.info("Intercepted toasts: {}", messages);
        }
        return messages;
    }

    public String getToastMessage() {
        List<String> messages = getAllToastMessages();
        return messages.isEmpty() ? "" : messages.get(0);
    }

    public void waitForToastToDisappear() {
        WaitUtils.waitForElementToDisappear(driver, toastContainer, DEFAULT_TIMEOUT);
    }

    public List<String> readAllToastsAndWaitToDisappear() {
        List<String> messages = getAllToastMessages();
        waitForToastToDisappear();
        return messages;
    }
}