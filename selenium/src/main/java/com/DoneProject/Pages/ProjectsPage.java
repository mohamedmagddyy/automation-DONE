package com.DoneProject.Pages;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.Duration;

public class ProjectsPage extends BasePage {

    private static final Logger logger        = LoggerFactory.getLogger(ProjectsPage.class);
    private static final int    WAIT_TIMEOUT  = 10;

    private final By addButton          = By.id("dropdownMenuButton1");
    private final By newProjectOption   = By.xpath("//a[normalize-space()='New project']");
    private final By projectNameInput   = By.id("projectName");
    private final By confirmDeleteButton = By.xpath("//div[contains(@class,'modal') and contains(@class,'show')]//button[normalize-space()='Yes']");

    public ProjectsPage() {
        super();
    }

    private By projectCardByName(String projectName) {
        return By.xpath(
                "//div[contains(@class,'projectCard')][.//text()[normalize-space()='" + projectName + "']]"
        );
    }

    private By actionButtonByName(String projectName) {
        return By.xpath(
                "//div[contains(@class,'projectCard')][.//text()[normalize-space()='" + projectName + "']]"
                + "//button[contains(@class,'dropdown-toggle')]"
        );
    }

    private By actionOptionByName(String projectName, String action) {
        return By.xpath(
                "//div[contains(@class,'projectCard')][.//text()[normalize-space()='" + projectName + "']]"
                + "//span[normalize-space()='" + action + "']"
        );
    }

    private By sectorLinkByName(String sectorName) {
        return By.xpath("//a[.//h4[normalize-space()='" + sectorName + "']]");
    }

    public ProjectsPage openSectorByName(String sectorName) {
        logger.info("Opening sector: {}", sectorName);
        waitForPageToBeReady();
        click(sectorLinkByName(sectorName));
        waitForPageToBeReady();
        return this;
    }

    public ProjectsPage addProject(String projectName) {
        logger.info("Adding project: {}", projectName);
        waitForPageToBeReady();
        click(addButton);
        click(newProjectOption);
        typeAndAutoSave(projectName);
        return this;
    }

    public ProjectsPage editProject(String oldName, String newName) {
        logger.info("Editing project: {} -> {}", oldName, newName);
        waitForPageToBeReady();
        click(actionButtonByName(oldName));
        By editOption = actionOptionByName(oldName, "Edit");
        new WebDriverWait(driver, Duration.ofSeconds(WAIT_TIMEOUT))
                .until(ExpectedConditions.visibilityOfElementLocated(editOption));
        click(editOption);
        typeAndAutoSave(newName);
        return this;
    }

    public ProjectsPage deleteProject(String projectName) {
        logger.info("Deleting project: {}", projectName);
        waitForPageToBeReady();
        click(actionButtonByName(projectName));
        click(actionOptionByName(projectName, "Delete"));
        click(confirmDeleteButton);
        waitForToastToDisappear();
        return this;
    }

    private void typeAndAutoSave(String projectName) {
        new WebDriverWait(driver, Duration.ofSeconds(WAIT_TIMEOUT))
                .until(ExpectedConditions.visibilityOfElementLocated(projectNameInput));
        JavascriptExecutor js = (JavascriptExecutor) driver;
        js.executeScript("arguments[0].value='';", driver.findElement(projectNameInput));
        js.executeScript("arguments[0].value=arguments[1];", driver.findElement(projectNameInput), projectName);
        js.executeScript("arguments[0].dispatchEvent(new Event('input', { bubbles: true }));",
                driver.findElement(projectNameInput));
        js.executeScript("arguments[0].blur();", driver.findElement(projectNameInput));
        new WebDriverWait(driver, Duration.ofSeconds(WAIT_TIMEOUT))
                .until(ExpectedConditions.visibilityOfElementLocated(projectCardByName(projectName)));
    }
}