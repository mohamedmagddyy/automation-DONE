package com.DoneProject.Pages;

import com.DoneProject.drivers.WebDriverFactory;
import com.DoneProject.helpers.ActionBot;
import com.DoneProject.utils.WaitUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class BasePage {

    protected WebDriver driver;
    protected ActionBot actionBot;

    // ✅ Locators للـ Toast
    protected By toastSuccess = By.cssSelector(".toast-success");
    protected By toastAny     = By.cssSelector(".toast-success, .toast-error, .toast-info, .toast-warning");

    private static final Logger logger = LoggerFactory.getLogger(BasePage.class);

    public BasePage() {
        this.driver    = WebDriverFactory.getDriver();
        this.actionBot = new ActionBot(driver);
        logger.info("✅ تم إنشاء BasePage بنجاح");
    }

    // ================= Click =================
    protected void click(By locator) {
        actionBot.click(locator);
    }

    // ================= Type =================
    protected void sendKeys(By locator, String text) {
        actionBot.type(locator, text);
    }

    // ================= JS Click =================
    protected void jsClick(By locator) {
        actionBot.jsClick(locator);
    }

    // ================= Scroll =================
    protected void scrollTo(By locator) {
        actionBot.scrollTo(locator);
    }

    // ================= Get Text =================
    protected String getText(By locator) {
        return actionBot.getText(locator);
    }

    // ================= Wait for Page Ready =================
    protected void waitForPageToBeReady() {
        logger.info("⏳ انتظار الصفحة لتكون جاهزة...");
        WaitUtils.waitForPageToBeReady(driver, 20);
        logger.info("✅ الصفحة جاهزة");
    }

    // ================= Wait for Toast to Appear =================
    // ✅ إضافة جديدة: ننتظر ظهور الـ toast أولاً قبل قراءته
    protected void waitForToastToAppear() {
        logger.info("⏳ انتظار ظهور Toast...");
        WaitUtils.waitForElementToBeVisible(driver, toastSuccess, 10);
        logger.info("✅ Toast ظهر");
    }

    // ================= Get Toast Message =================
    // ✅ إضافة جديدة: تقرأ الـ toast بعد ما تنتظر ظهوره
    protected String getToastMessage() {
        waitForToastToAppear();
        return getText(toastSuccess).trim();
    }

    // ================= Wait for Toast to Disappear =================
    protected void waitForToastToDisappear() {
        logger.info("⏳ انتظار Toast أن يختفي...");
        WaitUtils.waitForElementToDisappear(driver, toastSuccess, 10);
        logger.info("✅ Toast اختفى بنجاح");
    }

    // ================= Wait for Toast then Disappear =================
    // ✅ إضافة جديدة: مفيدة لما تحتاج تقرأ الـ toast وبعدين تنتظر اختفاؤه
    protected String readToastAndWaitToDisappear() {
        String message = getToastMessage();
        waitForToastToDisappear();
        return message;
    }
}