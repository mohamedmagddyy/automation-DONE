package com.DoneProject.Pages;

import com.DoneProject.helpers.ActionBot;
import com.DoneProject.utils.WaitUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class BasePage {

    protected WebDriver driver;
    protected ActionBot actionBot;
    protected By toastSuccess = By.cssSelector("div.toast-success");
    private static final Logger logger = LoggerFactory.getLogger(BasePage.class);


    public BasePage() {
        this.driver = com.DoneProject.utils.DriverManager.getDriver();
        this.actionBot = new ActionBot(driver);
        logger.info("✅ تم إنشاء BasePage بنجاح");
    }

    // ================== الإجراءات الأساسية ==================
    protected void click(By locator) {
        logger.debug("جاري النقر على: {}", locator);
        actionBot.click(locator);
    }

    protected void sendKeys(By locator, String text) {
        logger.debug("جاري كتابة: [{}] في: {}", text, locator);
        actionBot.type(locator, text);
    }

    protected String getText(By locator) {
        logger.debug("جاري قراءة النص من: {}", locator);
        String text = actionBot.getText(locator);
        logger.debug("النص المقروء: [{}]", text);
        return text;
    }

    protected void jsClick(By locator) {
        logger.debug("جاري النقر بـ JavaScript على: {}", locator);
        actionBot.jsClick(locator);
    }

    protected void jsClick(WebElement element) {
        // تحويل WebElement إلى By locator إذا لزم الأمر، أو استخدام JS click مباشرة
        if (element != null) {
            logger.debug("جاري النقر بـ JavaScript على WebElement");
            ((org.openqa.selenium.JavascriptExecutor) driver).executeScript("arguments[0].click();", element);
            logger.info("✅ تم النقر بـ JavaScript بنجاح");
        }
    }

    // ================== الإجراءات المتقدمة ==================
    protected void hover(By locator) {
        logger.debug("جاري التحويم على: {}", locator);
        actionBot.hover(locator);
    }

    protected void doubleClick(By locator) {
        logger.debug("جاري النقر المزدوج على: {}", locator);
        actionBot.doubleClick(locator);
    }

    protected void rightClick(By locator) {
        logger.debug("جاري النقر الأيمن على: {}", locator);
        actionBot.rightClick(locator);
    }

    protected void dragAndDrop(By source, By target) {
        logger.debug("جاري السحب من {} إلى {}", source, target);
        actionBot.dragAndDrop(source, target);
    }

    protected void scrollTo(By locator) {
        logger.debug("جاري التمرير إلى: {}", locator);
        actionBot.scrollTo(locator);
    }

    // ================== طرق الانتظار والمساعدة ==================
    protected void waitForPageToBeReady() {
        logger.info("⏳ انتظار الصفحة لتكون جاهزة...");
        WaitUtils.waitForPageToBeReady(driver, 20);
        logger.info("✅ الصفحة جاهزة");
    }

    protected void waitForToastToDisappear() {
        logger.info("⏳ انتظار Toast أن يختفي...");
        WaitUtils.waitForElementToDisappear(driver, By.cssSelector("div.toast-message"), 20);
        logger.info("✅ Toast اختفى بنجاح");
    }

    protected void waitForElementToDisappear(By locator) {
        logger.debug("⏳ انتظار العنصر أن يختفي: {}", locator);
        WaitUtils.waitForElementToDisappear(driver, locator, 20);
        logger.info("✅ العنصر اختفى بنجاح: {}", locator);
    }


}
