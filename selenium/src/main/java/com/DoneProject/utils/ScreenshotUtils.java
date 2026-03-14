package com.DoneProject.utils;

import com.DoneProject.drivers.WebDriverFactory;
import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;

import java.io.File;
import java.io.IOException;
import java.time.LocalDateTime;

public class ScreenshotUtils {

    public static void takeScreenshot(String testName) {

        WebDriver driver = WebDriverFactory.getDriver();

        File src = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);

        try {

            String fileName = "screenshots/" + testName + "_" + LocalDateTime.now().toString().replace(":", "-") + ".png";

            FileUtils.copyFile(src, new File(fileName));

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}