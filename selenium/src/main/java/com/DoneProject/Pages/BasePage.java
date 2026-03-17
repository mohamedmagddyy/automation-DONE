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

    // ✅ Locators للـ Toast (Angular ngx-toastr)
    protected By toastContainer = By.id("toast-container");
    protected By toastSuccess   = By.cssSelector(".toast-success");
    protected By toastError     = By.cssSelector(".toast-error");
    protected By toastInfo      = By.cssSelector(".toast-info");
    protected By toastWarning   = By.cssSelector(".toast-warning");
    
    // أي رسالة تظهر داخل الكونتينر
    protected By toastAny = By.cssSelector("#toast-container .ngx-toastr, #toast-container .toast-message, .toast-success, .toast-error");

    private static final Logger logger = LoggerFactory.getLogger(BasePage.class);

    public BasePage() {
        this.driver = WebDriverFactory.getDriver();
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
        logger.debug("⏳ انتظار الصفحة لتكون جاهزة...");
        WaitUtils.waitForPageToBeReady(driver, 20);
        logger.debug("✅ الصفحة جاهزة");
    }

    // ================= Wait for Toast to Appear =================
    // ✅ ننتظر ظهور الكونتينر أو أي رسالة بداخله
    public void waitForToastToAppear() {
        logger.debug("⏳ انتظار ظهور Toast...");
        WaitUtils.waitForElementToBeVisible(driver, toastAny, 10);
        logger.debug("✅ Toast ظهر");
    }

    // ================= Get All Toast Messages =================
    // ✅ تجيب كل الرسائل اللي ظاهرة حالياً في ليست
    public java.util.List<String> getAllToastMessages() {
        waitForToastToAppear();
        java.util.List<org.openqa.selenium.WebElement> toasts = driver.findElements(toastAny);
        java.util.List<String> messages = new java.util.ArrayList<>();
        for (org.openqa.selenium.WebElement toast : toasts) {
            String txt = toast.getText().trim();
            if (!txt.isEmpty()) {
                messages.add(txt);
                logger.debug("🔔 Toast Message Found: {}", txt);
            }
        }
        // طباعة كل الرسايل مرة واحدة في الـ log للوضوح
        if(!messages.isEmpty()){
            logger.info("📋 Intercepted Toasts: {}", messages);
        }
        return messages;
    }

    // ================= Get Toast Message (Single/First) =================
    public String getToastMessage() {
        java.util.List<String> messages = getAllToastMessages();
        return messages.isEmpty() ? "" : messages.get(0);
    }

    // ================= Wait for Toast to Disappear =================
    public void waitForToastToDisappear() {
        logger.debug("⏳ انتظار Toast أن يختفي...");
        WaitUtils.waitForElementToDisappear(driver, toastContainer, 10);
        logger.debug("✅ Toast اختفى بنجاح");
    }

    // ================= Wait for Toast then Disappear =================
    public java.util.List<String> readAllToastsAndWaitToDisappear() {
        java.util.List<String> messages = getAllToastMessages();
        waitForToastToDisappear();
        return messages;
    }
}