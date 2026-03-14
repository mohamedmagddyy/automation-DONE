package com.DoneProject.helpers;

import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.*;

import java.time.Duration;

public class DropdownHelper {

    public static void selectFromAngularDropdown(WebDriver driver, By dropdownLocator, String valueToSelect) {

        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(15));

        WebElement dropdown = wait.until(ExpectedConditions.elementToBeClickable(dropdownLocator));
        dropdown.click();

        WebElement option = wait.until(ExpectedConditions.elementToBeClickable(
                By.xpath("//mat-option//span[normalize-space()='" + valueToSelect + "']")));

        option.click();
    }

    public static void selectFromHtmlSelect(WebDriver driver, By dropdownLocator, String visibleText) {

        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(15));

        WebElement element = wait.until(ExpectedConditions.elementToBeClickable(dropdownLocator));

        Select select = new Select(element);
        select.selectByVisibleText(visibleText);
    }
}