package com.DoneProject.helpers;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.Duration;

public class DropdownHelper {

    private static final Logger logger  = LoggerFactory.getLogger(DropdownHelper.class);
    private static final int    TIMEOUT = 15;

    // ✅ منع إنشاء instance — Utility class
    private DropdownHelper() {}

    // ================= Angular Material Dropdown =================
    public static void selectFromAngularDropdown(WebDriver driver, By dropdownLocator, String valueToSelect) {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(TIMEOUT));

        WebElement dropdown = wait.until(ExpectedConditions.elementToBeClickable(dropdownLocator));
        dropdown.click();

        By optionLocator = By.xpath("//mat-option//span[normalize-space()='" + valueToSelect + "']");
        WebElement option = wait.until(ExpectedConditions.elementToBeClickable(optionLocator));
        option.click();

        logger.info("✅ تم الاختيار من Angular dropdown: {}", valueToSelect);
    }

    // ================= HTML Select =================
    public static void selectFromHtmlSelect(WebDriver driver, By dropdownLocator, String visibleText) {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(TIMEOUT));

        WebElement element = wait.until(ExpectedConditions.elementToBeClickable(dropdownLocator));
        new Select(element).selectByVisibleText(visibleText);

        logger.info("✅ تم الاختيار من HTML select: {}", visibleText);
    }

    // ================= HTML Select by Value =================
    public static void selectFromHtmlSelectByValue(WebDriver driver, By dropdownLocator, String value) {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(TIMEOUT));

        WebElement element = wait.until(ExpectedConditions.elementToBeClickable(dropdownLocator));
        new Select(element).selectByValue(value);

        logger.info("✅ تم الاختيار من HTML select بالـ value: {}", value);
    }
}