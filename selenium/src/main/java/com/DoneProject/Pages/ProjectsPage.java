package com.DoneProject.Pages;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.devtools.DevTools;
import org.openqa.selenium.devtools.HasDevTools;
//import org.openqa.selenium.devtools.v137.network.Network;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicBoolean;

public class ProjectsPage extends BasePage {

    // ===== Static Locators =====
    private final By addButton = By.id("dropdownMenuButton1");
    private final By newProjectOption = By.xpath("//a[normalize-space()='New project']");
    private final By projectNameInput = By.id("projectName");
    private final By saveButton = By.cssSelector("button.btn.btn-primary[type='submit']");
    private final By confirmDeleteButton = By.xpath("//div[contains(@class,'modal') and contains(@class,'show')]//button[normalize-space()='Yes']");

    // ===== Dynamic Locators =====
    private By projectCardByName(String projectName) {
        return By.xpath("//div[contains(@class,'projectCard')][.//text()[normalize-space()='"
                + projectName + "']]");
    }

    private By actionButton(String projectName) {
        return By.xpath("//div[contains(@class,'projectCard')][.//text()[normalize-space()='"
                + projectName + "']]//button[contains(@class,'dropdown-toggle')]");
    }

    private By actionOption(String projectName, String action) {
        return By.xpath("//div[contains(@class,'projectCard')][.//text()[normalize-space()='"
                + projectName + "']]//span[normalize-space()='" + action + "']");
    }

    public void openSectorByName(String sectorName) {
        By sectorLink = By.xpath(
                "//a[.//h4[normalize-space()='" + sectorName + "']]"
        );

        waitForPageToBeReady();
        click(sectorLink);
        waitForPageToBeReady();
    }

    // ===== Add Project =====
    public void addProject(String projectName) {
        waitForPageToBeReady();

        click(addButton);
        click(newProjectOption);

        typeProjectNameAndSave(projectName);
    }
    private void typeProjectNameAndSave(String projectName) {
        // مسح النص القديم + كتابة النص الجديد
        ((JavascriptExecutor) driver)
                .executeScript("arguments[0].value='';", driver.findElement(projectNameInput));
        ((JavascriptExecutor) driver)
                .executeScript("arguments[0].value=arguments[1];", driver.findElement(projectNameInput), projectName);

        // force blur → لتفعيل Auto-Save
        ((JavascriptExecutor) driver)
                .executeScript("arguments[0].blur();", driver.findElement(projectNameInput));

        // انتظار ظهور المشروع في الجدول → تأكيد Auto-Save
        new WebDriverWait(driver, Duration.ofSeconds(5))
                .until(ExpectedConditions.visibilityOfElementLocated(projectCardByName(projectName)));
    }

    // ===== Edit Project =====
    public void editProject(String oldName, String newName) {
        waitForPageToBeReady();

        click(actionButton(oldName));
        click(actionOption(oldName, "Edit"));

        // مسح القديم بطريقة آمنة
//        projectNameInput.sendKeys(Keys.chord(Keys.CONTROL, "a"));
//        projectNameInput.sendKeys(Keys.DELETE);

        // كتابة الاسم الجديد + Auto-Save
        typeProjectNameAndSave(newName);
    }

    // ===== Delete Project =====
    public void deleteProject(String projectName) {
        waitForPageToBeReady();

        click(actionButton(projectName));
        click(actionOption(projectName, "Delete"));
        click(confirmDeleteButton);
        waitForToastToDisappear();
    }

    // ===== Assertion Helper =====
    public String getToastMessage() {
        return getText(toastSuccess).trim();
    }
}