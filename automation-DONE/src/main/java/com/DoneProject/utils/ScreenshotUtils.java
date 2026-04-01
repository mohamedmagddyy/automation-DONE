package com.DoneProject.utils;

import com.DoneProject.drivers.WebDriverFactory;
import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class ScreenshotUtils {

    private static final Logger logger = LoggerFactory.getLogger(ScreenshotUtils.class);
    private static final String SCREENSHOTS_DIR = "screenshots/";

    // ✅ منع إنشاء instance — Utility class
    private ScreenshotUtils() {}

    // ================= Take Screenshot =================
    public static void takeScreenshot(String className, String testName) {
        WebDriver driver = WebDriverFactory.getDriver();

        if (driver == null) {
            logger.warn("⚠️ No active driver — cannot take screenshot");
            return;
        }

        try {
            String timestamp = LocalDateTime.now()
                    .format(DateTimeFormatter.ofPattern("yyyy-MM-dd_HH-mm-ss"));

            // ✅ إنشاء المسار: screenshots/ClassName/TestName_Timestamp.png
            String folderPath = SCREENSHOTS_DIR + className + "/";
            String filePath   = folderPath + testName + "_" + timestamp + ".png";

            File src  = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
            File dest = new File(filePath);

            // ✅ إنشاء المجلدات لو مش موجودة
            if (dest.getParentFile().mkdirs()) {
                logger.debug("📁 Created directory: {}", folderPath);
            }

            FileUtils.copyFile(src, dest);
            logger.info("📸 Screenshot saved: {}", filePath);

        } catch (IOException e) {
            logger.error("❌ Failed to save screenshot: {}", e.getMessage());
        }
    }
}
