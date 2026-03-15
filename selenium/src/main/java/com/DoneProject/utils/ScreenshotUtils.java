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
    public static void takeScreenshot(String testName) {
        WebDriver driver = WebDriverFactory.getDriver();

        if (driver == null) {
            logger.warn("⚠️ لا يوجد driver نشط — تعذّر أخذ screenshot");
            return;
        }

        try {
            // ✅ تنسيق التاريخ آمن على ويندوز (بدون : في الاسم)
            String timestamp = LocalDateTime.now()
                    .format(DateTimeFormatter.ofPattern("yyyy-MM-dd_HH-mm-ss"));

            String fileName = SCREENSHOTS_DIR + testName + "_" + timestamp + ".png";

            File src  = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
            File dest = new File(fileName);

            // ✅ إنشاء الـ directory لو مش موجود
            dest.getParentFile().mkdirs();

            FileUtils.copyFile(src, dest);
            logger.info("✅ تم حفظ الـ screenshot: {}", fileName);

        } catch (IOException e) {
            logger.error("❌ فشل حفظ الـ screenshot: {}", e.getMessage());
        }
    }
}