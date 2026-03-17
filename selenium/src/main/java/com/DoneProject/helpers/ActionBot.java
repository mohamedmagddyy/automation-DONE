package com.DoneProject.helpers;

import org.openqa.selenium.*;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.Duration;

public class ActionBot {

    private final WebDriver driver;
    private final WebDriverWait wait;
    private final Actions actions;

    private static final Logger logger   = LoggerFactory.getLogger(ActionBot.class);
    private static final int    TIMEOUT  = 20;

    public ActionBot(WebDriver driver) {
        this.driver  = driver;
        this.wait    = new WebDriverWait(driver, Duration.ofSeconds(TIMEOUT));
        this.actions = new Actions(driver);
        logger.info("✅ تم إنشاء ActionBot بنجاح مع WebDriver");
    }

    // ================= Click =================
    public void click(By locator) {
        try {
            logger.debug("⏳ جاري الانتظار لعنصر قابل للنقر: {}", locator);
            wait.until(ExpectedConditions.elementToBeClickable(locator)).click();
            logger.info("✅ Clicked: {}", locator);
        } catch (Exception e) {
            logger.error("❌ فشل النقر على العنصر: {} - الخطأ: {}", locator, e.getMessage());
            throw e;
        }
    }

    // ================= Type =================
    public void type(By locator, String text) {
        try {
            logger.debug("⏳ جاري الانتظار لعنصر مرئي: {}", locator);
            WebElement element = wait.until(ExpectedConditions.visibilityOfElementLocated(locator));
            logger.debug("🔄 جاري مسح النص القديم والكتابة الجديدة: {}", text);
            element.sendKeys(Keys.CONTROL + "a");
            element.sendKeys(Keys.DELETE);
            element.sendKeys(text);
            logger.info("✅ Typed: [{}] in {}", text, locator);
        } catch (Exception e) {
            logger.error("❌ فشلت الكتابة في العنصر: {} - النص: {} - الخطأ: {}", locator, text, e.getMessage());
            throw e;
        }
    }

    // ================= JS Click =================
    public void jsClick(By locator) {
        try {
            logger.debug("⏳ جاري الانتظار لعنصر موجود: {}", locator);
            WebElement element = wait.until(ExpectedConditions.presenceOfElementLocated(locator));
            logger.debug("⚙️ جاري النقر باستخدام JavaScript على: {}", locator);
            ((JavascriptExecutor) driver).executeScript("arguments[0].click();", element);
            logger.info("✅ JS Clicked: {}", locator);
        } catch (Exception e) {
            logger.error("❌ فشل النقر بـ JavaScript على العنصر: {} - الخطأ: {}", locator, e.getMessage());
            throw e;
        }
    }

    // ================= Scroll To =================
    public void scrollTo(By locator) {
        try {
            logger.debug("⏳ جاري الانتظار لعنصر مرئي للتمرير: {}", locator);
            WebElement element = wait.until(ExpectedConditions.visibilityOfElementLocated(locator));
            ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", element);
            logger.debug("✅ Scrolled to: {}", locator);
        } catch (Exception e) {
            logger.error("❌ فشل التمرير إلى العنصر: {} - الخطأ: {}", locator, e.getMessage());
            throw e;
        }
    }

    // ================= Get Text =================
    public String getText(By locator) {
        try {
            logger.debug("⏳ جاري الانتظار لعنصر مرئي لقراءة النص: {}", locator);
            String text = wait.until(ExpectedConditions.visibilityOfElementLocated(locator)).getText();
            logger.debug("✅ Read text from {}: [{}]", locator, text);
            return text;
        } catch (Exception e) {
            logger.error("❌ فشل قراءة النص من العنصر: {} - الخطأ: {}", locator, e.getMessage());
            throw e;
        }
    }

    // ================= Hover =================
    public void hover(By locator) {
        try {
            logger.debug("⏳ جاري التحويم على العنصر: {}", locator);
            WebElement element = wait.until(ExpectedConditions.visibilityOfElementLocated(locator));
            actions.moveToElement(element).perform();
            logger.info("✅ Hovered on: {}", locator);
        } catch (Exception e) {
            logger.error("❌ فشل التحويم على العنصر: {} - الخطأ: {}", locator, e.getMessage());
            throw e;
        }
    }

    // ================= Double Click =================
    public void doubleClick(By locator) {
        try {
            logger.debug("⏳ جاري النقر المزدوج على العنصر: {}", locator);
            WebElement element = wait.until(ExpectedConditions.elementToBeClickable(locator));
            actions.doubleClick(element).perform();
            logger.info("✅ Double Clicked: {}", locator);
        } catch (Exception e) {
            logger.error("❌ فشل النقر المزدوج على العنصر: {} - الخطأ: {}", locator, e.getMessage());
            throw e;
        }
    }

    // ================= Right Click =================
    public void rightClick(By locator) {
        try {
            logger.debug("⏳ جاري النقر الأيمن على العنصر: {}", locator);
            WebElement element = wait.until(ExpectedConditions.elementToBeClickable(locator));
            actions.contextClick(element).perform();
            logger.info("✅ Right Clicked: {}", locator);
        } catch (Exception e) {
            logger.error("❌ فشل النقر الأيمن على العنصر: {} - الخطأ: {}", locator, e.getMessage());
            throw e;
        }
    }

    // ================= Drag And Drop =================
    public void dragAndDrop(By source, By target) {
        try {
            logger.debug("⏳ جاري السحب والإفلات من {} إلى {}", source, target);
            WebElement src = wait.until(ExpectedConditions.visibilityOfElementLocated(source));
            WebElement trg = wait.until(ExpectedConditions.visibilityOfElementLocated(target));
            actions.dragAndDrop(src, trg).perform();
            logger.info("✅ Dragged from {} to {}", source, target);
        } catch (Exception e) {
            logger.error("❌ فشل السحب والإفلات من {} إلى {} - الخطأ: {}", source, target, e.getMessage());
            throw e;
        }
    }
}