package com.DoneProject.Pages;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.Duration;

public class ProjectsPage extends BasePage {

    private static final Logger logger = LoggerFactory.getLogger(ProjectsPage.class);

    // ===== Static Locators =====
    private final By addButton          = By.id("dropdownMenuButton1");
    private final By newProjectOption   = By.xpath("//a[normalize-space()='New project']");
    private final By projectNameInput   = By.id("projectName");
    private final By confirmDeleteButton = By.xpath("//div[contains(@class,'modal') and contains(@class,'show')]//button[normalize-space()='Yes']");

    public ProjectsPage() {
        super();
    }

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

    // ===== Open Sector =====
    public void openSectorByName(String sectorName) {
        By sectorLink = By.xpath("//a[.//h4[normalize-space()='" + sectorName + "']]");
        waitForPageToBeReady();
        click(sectorLink);
        waitForPageToBeReady();
        logger.info("✅ تم فتح السيكتور: {}", sectorName);
    }

    // ===== Add Project =====
    // ✅ إصلاح: الـ app يعتمد على auto-save — لا يوجد save button
    public void addProject(String projectName) {
        waitForPageToBeReady();
        click(addButton);
        click(newProjectOption);
        typeAndAutoSave(projectName);
        logger.info("✅ تم إضافة المشروع: {}", projectName);
    }

    // ===== Edit Project =====
    // ✅ إصلاح: انتظار ظهور خيار Edit بعد فتح الـ dropdown
    public void editProject(String oldName, String newName) {
        waitForPageToBeReady();
        click(actionButton(oldName));

        By editOption = actionOption(oldName, "Edit");
        new WebDriverWait(driver, Duration.ofSeconds(10))
                .until(ExpectedConditions.visibilityOfElementLocated(editOption));
        click(editOption);

        typeAndAutoSave(newName);
        logger.info("✅ تم تعديل المشروع من {} إلى {}", oldName, newName);
    }

    // ===== Delete Project =====
    public void deleteProject(String projectName) {
        waitForPageToBeReady();
        click(actionButton(projectName));
        click(actionOption(projectName, "Delete"));
        click(confirmDeleteButton);
        waitForToastToDisappear();
        logger.info("✅ تم حذف المشروع: {}", projectName);
    }

    // ===== Auto-Save Helper =====
    private void typeAndAutoSave(String projectName) {
        new WebDriverWait(driver, Duration.ofSeconds(10))
                .until(ExpectedConditions.visibilityOfElementLocated(projectNameInput));

        JavascriptExecutor js = (JavascriptExecutor) driver;
        js.executeScript("arguments[0].value='';", driver.findElement(projectNameInput));
        js.executeScript("arguments[0].value=arguments[1];", driver.findElement(projectNameInput), projectName);
        js.executeScript("arguments[0].dispatchEvent(new Event('input', { bubbles: true }));",
                driver.findElement(projectNameInput));
        js.executeScript("arguments[0].blur();", driver.findElement(projectNameInput));

        // انتظار ظهور الكارد للتأكد من الحفظ
        new WebDriverWait(driver, Duration.ofSeconds(10))
                .until(ExpectedConditions.visibilityOfElementLocated(projectCardByName(projectName)));
    }

    // ===== Toast Message =====
    public String getToastMessage() {
        return super.getToastMessage();
    }
}