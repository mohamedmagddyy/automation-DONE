package com.DoneProject.Pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

public class RecurringTaskPage extends BasePage {

    private static final Logger logger = LoggerFactory.getLogger(RecurringTaskPage.class);

    private final By addButton        = By.className("btn-add");
    private final By taskNameInput    = By.xpath("//input[@formcontrolname='name']");
    private final By projectDropdown  = By.xpath("//ng-select[@formcontrolname='projectId']");
    private final By descriptionInput = By.xpath("//textarea[@formcontrolname='description']");
    private final By statusDropdown   = By.xpath("//ng-select[@formcontrolname='statusId']");
    private final By patternDropdown  = By.xpath("//select[@formcontrolname='pattern']");
    private final By intervalInput    = By.xpath("//input[@formcontrolname='interval']");
    private final By timeInput        = By.xpath("//input[@formcontrolname='scheduleTime']");
    private final By durationInput    = By.xpath("//input[@formcontrolname='duration']");
    private final By isActiveCheckbox = By.xpath("//input[@formcontrolname='isActive' or @id='isActive']");
    private final By saveButton       = By.xpath("//button[contains(@class, 'btn-add') and (normalize-space(.)='Add' or normalize-space(.)='Save' or normalize-space(.)='Update')]");
    private final By confirmDeleteBtn = By.xpath("//button[contains(@class, 'btn-danger') and (normalize-space(.)='Yes' or normalize-space(.)='Delete')]");

    public RecurringTaskPage() {
        super();
    }

    private By actionsMenuByTaskName(String taskName) {
        return By.xpath("//tr[.//td[normalize-space()='" + taskName + "']]//button[contains(@class,'dropdown-toggle')]");
    }

    private By dropdownOption(String optionName) {
        return By.xpath("//ul[contains(@class,'dropdown-menu') and contains(@class,'show')]//span[contains(text(),'" + optionName + "')]");
    }

    public By taskRow(String taskName) {
        return By.xpath("//tr[.//td[normalize-space()='" + taskName + "']]");
    }

    private By ngSelectOption(String text) {
        return By.xpath("//span[contains(@class, 'ng-option-label') and normalize-space()='" + text + "']");
    }

    private By userCheckbox(String userName) {
        return By.xpath("//label[normalize-space()='" + userName + "']/preceding-sibling::input");
    }

    public RecurringTaskPage addRecurringTask(String name, String project, String description, String status,
                                 String pattern, String interval, String time, String duration,
                                 List<String> users, boolean isActive) {
        logger.info("Adding recurring task: {}", name);
        waitForPageToBeReady();
        click(addButton);
        fillTaskForm(name, project, description, status, pattern, interval, time, duration, users, isActive);
        click(saveButton);
        return this;
    }

    public RecurringTaskPage editRecurringTask(String taskName, String newName, String project, String description,
                                  String status, String pattern, String interval, String time,
                                  String duration, List<String> users, boolean isActive) {
        logger.info("Editing recurring task: {}", taskName);
        waitForPageToBeReady();
        click(actionsMenuByTaskName(taskName));
        click(dropdownOption("Edit"));
        fillTaskForm(newName, project, description, status, pattern, interval, time, duration, users, isActive);
        click(saveButton);
        return this;
    }

    public RecurringTaskPage deactivateRecurringTask(String taskName) {
        logger.info("Deactivating recurring task: {}", taskName);
        waitForPageToBeReady();
        click(actionsMenuByTaskName(taskName));
        click(dropdownOption("Deactivate"));
        return this;
    }

    public RecurringTaskPage deleteRecurringTask(String taskName) {
        logger.info("Deleting recurring task: {}", taskName);
        waitForPageToBeReady();
        click(actionsMenuByTaskName(taskName));
        click(dropdownOption("Delete"));
        click(confirmDeleteBtn);
        return this;
    }

    private void fillTaskForm(String name, String project, String description, String status,
                              String pattern, String interval, String time, String duration,
                              List<String> users, boolean isActive) {
        if (name != null)        sendKeys(taskNameInput, name);
        if (project != null)     { click(projectDropdown); click(ngSelectOption(project)); }
        if (description != null) sendKeys(descriptionInput, description);
        if (status != null)      { click(statusDropdown); click(ngSelectOption(status)); }
        if (pattern != null)     actionBot.selectOptionByVisibleText(patternDropdown, pattern);
        if (interval != null)    sendKeys(intervalInput, interval);
        if (time != null)        sendKeys(timeInput, time);
        if (duration != null)    sendKeys(durationInput, duration);

        if (users != null) {
            for (String user : users) {
                click(userCheckbox(user));
            }
        }

        WebElement activeCheck = driver.findElement(isActiveCheckbox);
        if (activeCheck.isSelected() != isActive) {
            click(isActiveCheckbox);
        }
    }
}