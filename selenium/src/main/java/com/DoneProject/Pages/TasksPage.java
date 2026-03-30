package com.DoneProject.Pages;

import com.DoneProject.utils.WaitUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebElement;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class TasksPage extends BasePage {

    private static final Logger logger = LoggerFactory.getLogger(TasksPage.class);

    private final By addTaskMenuButton = By.id("dropdownMenuButton1");
    private final By createTaskOption = By.xpath("//a[normalize-space()='Create task']");
    private final By closeModalButton = By
            .cssSelector("#addNewQmission .modal-header button.btn-close[aria-label='Close']");
    private final By confirmDeleteButton = By
            .xpath("//div[contains(@class,'modal') and contains(@class,'show')]//button[normalize-space()='Yes']");
    private final By firstTaskActionButton = By
            .xpath("//div[@id='ToDo']//div[@class='p-scrollpanel-content']//div[1]//div[1]//button[1]//i[1]");

    private final String taskContainerXpath = "//div[contains(@class,'card') and .//*[normalize-space()='%s']]";
    private final String threeDotsButtonXpathSnippet = ".//button[contains(@class,'tooltip-n') and normalize-space()='…']";
    private final By dropdownMenu = By.cssSelector(".dropdown-menu.show");
    private final By confirmButton = By
            .cssSelector(".modal.show .btn-primary, .modal.show .btn-danger, .modal.show .btn-submitdone");

    private final By[] taskNameCandidates = {
            By.xpath("//div[@id='addNewQmission']//input[@id='name']"),
            By.xpath("//input[@id='name' or @name='name']"),
            By.cssSelector("input#name"),
            By.xpath("//div[contains(@id,'addNew') or contains(@class,'modal')]//input[@id='name' or @name='name']"),
            By.xpath("//input[contains(@placeholder,'Task') or contains(@placeholder,'task')]")
    };

    public TasksPage() {
        super();
    }

    // --- Helpers ---
    private void typeTaskName(String text) {
        for (By candidate : taskNameCandidates) {
            try {
                WaitUtils.waitForElementToBeVisible(driver, candidate, 5);
                WebElement el = driver.findElement(candidate);
                if (el.isDisplayed()) {
                    scrollTo(candidate);
                    sendKeys(candidate, text);
                    logger.info("Typed task name using locator: {}", candidate);
                    return;
                }
            } catch (Exception ignored) {
            }
        }
        throw new RuntimeException("Task name input field not found.");
    }

    private void closeModal() {
        try {
            WaitUtils.waitForElementToBeClickable(driver, closeModalButton, 5);
            click(closeModalButton);
        } catch (Exception e) {
            jsClick(closeModalButton);
        }
    }

    private void handleConfirmation() {
        WebElement el = WaitUtils.waitForElementToBeClickable(driver, confirmButton, 5);
        el.click();
    }

    private WebElement getTaskElement(String taskName) {
        By locator = By.xpath(String.format(taskContainerXpath, taskName));
        WaitUtils.waitForElementToBeVisible(driver, locator, 10);
        return driver.findElement(locator);
    }

    private void validateMenuOption(String optionName) {
        final java.util.List<String> allowed = java.util.Arrays.asList("Open", "Edit task status", "Move to archive",
                "Delete");
        if (!allowed.contains(optionName)) {
            throw new IllegalArgumentException("Invalid menu option requested: " + optionName);
        }
    }

    private void selectMenuOption(String taskName, String optionName) {
        validateMenuOption(optionName);
        clickThreeDotsForTask(taskName);
        By optionLocator = By
                .xpath("//ul[contains(@class,'dropdown-menu') and contains(@class,'show')]//span[normalize-space()='"
                        + optionName + "']");
        WaitUtils.waitForElementToBeClickable(driver, optionLocator, 5).click();
    }

    private void clickThreeDotsForTask(String taskName) {
        WebElement container = getTaskElement(taskName);
        By fullDotsLocator = By
                .xpath(String.format(taskContainerXpath, taskName) + threeDotsButtonXpathSnippet.substring(1));
        WebElement dots = WaitUtils.waitForElementToBeClickable(driver, fullDotsLocator, 5);
        ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", dots);
        dots.click();
        WaitUtils.waitForElementToBeVisible(driver, dropdownMenu, 5);
    }

    // --- Public API ---
    public TasksPage addTask(String taskName) {
        click(addTaskMenuButton);
        click(createTaskOption);
        typeTaskName(taskName);
        closeModal();
        return this;
    }

    public TasksPage editFirstTask(String newTaskName) {
        click(firstTaskActionButton);
        click(By.xpath(
                "//ul[contains(@class,'dropdown-menu') and contains(@class,'show')]//span[normalize-space()='Open']"));
        typeTaskName(newTaskName);
        closeModal();
        return this;
    }

    public TasksPage deleteFirstTask() {
        click(firstTaskActionButton);
        click(By.xpath(
                "//ul[contains(@class,'dropdown-menu') and contains(@class,'show')]//span[normalize-space()='Delete']"));
        WaitUtils.waitForElementToBeClickable(driver, confirmDeleteButton, 5).click();
        return this;
    }

    public boolean isTaskDisplayed(String taskName) {
        try {
            return getTaskElement(taskName).isDisplayed();
        } catch (Exception e) {
            return false;
        }
    }

    public String getFirstTaskName() {
        By locator = By.xpath("(//div[contains(@class,'card')]//h4)[1]");
        WaitUtils.waitForElementToBeVisible(driver, locator, 10);
        return getText(locator);
    }

    public TasksPage openTask(String taskName) {
        selectMenuOption(taskName, "Open");
        return this;
    }

    public TasksPage editTaskStatus(String taskName) {
        selectMenuOption(taskName, "Edit task status");
        return this;
    }

    public TasksPage moveToArchive(String taskName) {
        selectMenuOption(taskName, "Move to archive");
        handleConfirmation();
        return this;
    }

    public TasksPage deleteTask(String taskName) {
        selectMenuOption(taskName, "Delete");
        handleConfirmation();
        return this;
    }
}