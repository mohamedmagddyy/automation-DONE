package com.DoneProject.Pages;

import com.DoneProject.drivers.WebDriverFactory;
import com.DoneProject.helpers.ActionBot;
import com.DoneProject.utils.WaitUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class BasePage {

    protected WebDriver driver;
    protected ActionBot actionBot;

    protected By toastSuccess = By.cssSelector(".toast-success");

    public BasePage() {
        driver = WebDriverFactory.getDriver();
        actionBot = new ActionBot(driver);
    }

    protected void click(By locator) {
        actionBot.click(locator);
    }

    protected void sendKeys(By locator, String text) {
        actionBot.type(locator, text);
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

    protected void waitForToastToDisappear() {
        WaitUtils.waitForElementToDisappear(driver, toastSuccess, 10);
    }
}