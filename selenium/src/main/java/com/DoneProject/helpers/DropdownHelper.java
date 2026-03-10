package com.DoneProject.helpers;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

public class DropdownHelper {

    public static void selectFromAngularDropdown(WebDriver driver, WebDriverWait wait, By dropdownLocator, String valueToSelect) {
        WebElement dropdown = wait.until(ExpectedConditions.elementToBeClickable(dropdownLocator));
        dropdown.click();
        WebElement option = wait.until(ExpectedConditions.elementToBeClickable(
                By.xpath("//mat-option//span[normalize-space()='" + valueToSelect + "']")));
        option.click();
        dropdown.sendKeys(Keys.ESCAPE);
    }

    public static void selectFromHtmlSelect(WebDriverWait wait, By dropdownLocator, String visibleText) {
        WebElement element = wait.until(ExpectedConditions.elementToBeClickable(dropdownLocator));
        Select select = new Select(element);
        select.selectByVisibleText(visibleText);
    }
}

