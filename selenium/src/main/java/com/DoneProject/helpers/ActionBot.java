package com.DoneProject.helpers;

import org.openqa.selenium.*;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.Duration;

public class ActionBot {

    private WebDriver driver;
    private WebDriverWait wait;
    private Actions actions;
    private static final Logger logger = LoggerFactory.getLogger(ActionBot.class);

    public ActionBot(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(20));
        this.actions = new Actions(driver);
        logger.info("✅ تم إنشاء ActionBot بنجاح مع WebDriver");
    }

    // ===============================
    // Click
    // ===============================
    public void click(By locator) {
        try {
            logger.info("⏳ جاري الانتظار لعنصر قابل للنقر: {}", locator);
            wait.until(ExpectedConditions.elementToBeClickable(locator)).click();
            logger.info("✅ تم النقر على العنصر: {}", locator);
        } catch (Exception e) {
            logger.error("❌ فشل النقر على العنصر: {} - الخطأ: {}", locator, e.getMessage());
            throw e;
        }
    }

    // ===============================
    // Type
    // ===============================
    public void type(By locator, String text) {
        try {
            logger.info("⏳ جاري الانتظار لعنصر مرئي: {}", locator);
            WebElement element = wait.until(ExpectedConditions.visibilityOfElementLocated(locator));
            logger.info("🔄 جاري مسح النص القديم والكتابة الجديدة: {}", text);
            element.clear();
            element.sendKeys(text);
            logger.info("✅ تم كتابة النص بنجاح: [{}] في العنصر: {}", text, locator);
        } catch (Exception e) {
            logger.error("❌ فشلت الكتابة في العنصر: {} - النص: {} - الخطأ: {}", locator, text, e.getMessage());
            throw e;
        }
    }

    // ===============================
    // Hover
    // ===============================
    public void hover(By locator) {
        try {
            logger.info("⏳ جاري الانتظار لعنصر مرئي للتحويم: {}", locator);
            WebElement element = wait.until(ExpectedConditions.visibilityOfElementLocated(locator));
            logger.info("🖱️ جاري التحويم على العنصر: {}", locator);
            actions.moveToElement(element).perform();
            logger.info("✅ تم التحويم بنجاح على: {}", locator);
        } catch (Exception e) {
            logger.error("❌ فشل التحويم على العنصر: {} - الخطأ: {}", locator, e.getMessage());
            throw e;
        }
    }

    // ===============================
    // Double Click
    // ===============================
    public void doubleClick(By locator) {
        try {
            logger.info("⏳ جاري الانتظار لعنصر قابل للنقر: {}", locator);
            WebElement element = wait.until(ExpectedConditions.elementToBeClickable(locator));
            logger.info("👆 جاري النقر المزدوج على العنصر: {}", locator);
            actions.doubleClick(element).perform();
            logger.info("✅ تم النقر المزدوج بنجاح على: {}", locator);
        } catch (Exception e) {
            logger.error("❌ فشل النقر المزدوج على العنصر: {} - الخطأ: {}", locator, e.getMessage());
            throw e;
        }
    }

    // ===============================
    // Right Click
    // ===============================
    public void rightClick(By locator) {
        try {
            logger.info("⏳ جاري الانتظار لعنصر قابل للنقر: {}", locator);
            WebElement element = wait.until(ExpectedConditions.elementToBeClickable(locator));
            logger.info("➡️ جاري النقر الأيمن على العنصر: {}", locator);
            actions.contextClick(element).perform();
            logger.info("✅ تم النقر الأيمن بنجاح على: {}", locator);
        } catch (Exception e) {
            logger.error("❌ فشل النقر الأيمن على العنصر: {} - الخطأ: {}", locator, e.getMessage());
            throw e;
        }
    }

    // ===============================
    // Drag And Drop
    // ===============================
    public void dragAndDrop(By source, By target) {
        try {
            logger.info("⏳ جاري الانتظار للعناصر: المصدر: {}, الهدف: {}", source, target);
            WebElement src = wait.until(ExpectedConditions.visibilityOfElementLocated(source));
            WebElement trg = wait.until(ExpectedConditions.visibilityOfElementLocated(target));
            logger.info("🔄 جاري السحب والإفلات من {} إلى {}", source, target);
            actions.dragAndDrop(src, trg).perform();
            logger.info("✅ تم السحب والإفلات بنجاح من {} إلى {}", source, target);
        } catch (Exception e) {
            logger.error("❌ فشل السحب والإفلات من {} إلى {} - الخطأ: {}", source, target, e.getMessage());
            throw e;
        }
    }

    // ===============================
    // Scroll To Element
    // ===============================
    public void scrollTo(By locator) {
        try {
            logger.info("⏳ جاري الانتظار لعنصر مرئي للتمرير: {}", locator);
            WebElement element = wait.until(ExpectedConditions.visibilityOfElementLocated(locator));
            logger.info("📜 جاري التمرير إلى العنصر: {}", locator);
            ((JavascriptExecutor) driver)
                    .executeScript("arguments[0].scrollIntoView(true);", element);
            logger.info("✅ تم التمرير بنجاح إلى: {}", locator);
        } catch (Exception e) {
            logger.error("❌ فشل التمرير إلى العنصر: {} - الخطأ: {}", locator, e.getMessage());
            throw e;
        }
    }

    // ===============================
    // JS Click (Fallback)
    // ===============================
    public void jsClick(By locator) {
        try {
            logger.info("⏳ جاري الانتظار لعنصر موجود: {}", locator);
            WebElement element = wait.until(ExpectedConditions.presenceOfElementLocated(locator));
            logger.info("⚙️ جاري النقر باستخدام JavaScript على: {}", locator);
            ((JavascriptExecutor) driver)
                    .executeScript("arguments[0].click();", element);
            logger.info("✅ تم النقر بـ JavaScript بنجاح على: {}", locator);
        } catch (Exception e) {
            logger.error("❌ فشل النقر بـ JavaScript على العنصر: {} - الخطأ: {}", locator, e.getMessage());
            throw e;
        }
    }

    // ===============================
    // Get Text
    // ===============================
    public String getText(By locator) {
        try {
            logger.info("⏳ جاري الانتظار لعنصر مرئي لقراءة النص: {}", locator);
            String text = wait.until(ExpectedConditions.visibilityOfElementLocated(locator)).getText();
            logger.info("✅ تم قراءة النص من {}: [{}]", locator, text);
            return text;
        } catch (Exception e) {
            logger.error("❌ فشل قراءة النص من العنصر: {} - الخطأ: {}", locator, e.getMessage());
            throw e;
        }
    }

    // ===============================
    // Simple Logger
    // ===============================
    private void log(String message) {
        logger.info("[ACTION BOT] {}", message);
    }
}

