package com.DoneProject.Pages;

import com.DoneProject.utils.WaitUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class TasksPage extends BasePage {

    // ================= المحددات الثابتة =================

    // Add Task
    private final By addTaskMenuButton = By.id("dropdownMenuButton1");

    private final By createTaskOption =
            By.xpath("//a[normalize-space()='Create task']");

    // candidate locators - بعض المواقع تغير الـ id فنجرب أكتر من خيار
    private final By taskNameInput = By.xpath("//div[@id='addNewQmission']//input[@id='name']");
    private final By closeTaskButton =
            By.cssSelector("#addNewQmission .modal-header button.btn-close[aria-label='Close']");

    // First task action (temporary – لحد ما نعمل by name)
    private final By firstTaskActionButton =
            By.xpath("//div[@id='ToDo']//div[@class='p-scrollpanel-content']//div[1]//div[1]//button[1]//i[1]");

    // Confirm delete
    private final By confirmDeleteTaskButton =
            By.xpath("//div[contains(@class,'modal') and contains(@class,'show')]//button[normalize-space()='Yes']");

    private static final Logger logger = LoggerFactory.getLogger(TasksPage.class);

    // additional candidate locators
    private final By taskNameInput_candidate_1 = By.xpath("//input[@id='name' or @name='name']");
    private final By taskNameInput_candidate_2 = By.xpath("//div[contains(@id,'addNew') or contains(@class,'addNew') or contains(@class,'modal')]//input[@id='name' or @name='name']");
    private final By taskNameInput_candidate_3 = By.cssSelector("input#name");
    private final By taskNameInput_candidate_4 = By.xpath("//input[contains(@placeholder,'Task') or contains(@placeholder,'task') or contains(@placeholder,'مهمة') or contains(@placeholder,'اسم')]" );

    // ================= CONSTRUCTOR =================

    public TasksPage() {
        super();
    }

    // ================= DROPDOWN HELPER =================

    private By dropdownOption(String optionName) {
        return By.xpath(
                "//ul[contains(@class,'dropdown-menu') and contains(@class,'show')]//span[normalize-space()='"
                        + optionName + "']"
        );
    }

    // helper: try typing into the first visible candidate locator
    private void safeTypeIntoTaskName(String text) {
        By[] candidates = new By[]{
                taskNameInput, taskNameInput_candidate_1, taskNameInput_candidate_2, taskNameInput_candidate_3, taskNameInput_candidate_4
        };

        boolean typed = false;
        for (By candidate : candidates) {
            try {
                logger.info("Trying candidate locator for task input: {}", candidate);
                // wait a short time for visibility
                try {
                    WaitUtils.waitForElementToBeVisible(driver, candidate, 10);
                } catch (Exception ignored) {
                    // try next candidate
                    logger.debug("Candidate not visible: {}", candidate);
                    continue;
                }

                WebElement el = driver.findElement(candidate);
                if (el.isDisplayed()) {
                    actionBot.scrollTo(candidate);
                    actionBot.type(candidate, text);
                    typed = true;
                    logger.info("Successfully typed task name using {}", candidate);
                    break;
                }
            } catch (Exception e) {
                logger.warn("Candidate failed {} - {}", candidate, e.getMessage());
                // continue to next candidate
            }
        }

        if (!typed) {
            throw new RuntimeException("Could not find visible task name input - tried multiple candidates");
        }
    }

    // ================= ADD TASK =================

    public void addTask(String taskName) {
        waitForPageToBeReady();
        click(addTaskMenuButton);
        click(createTaskOption);

        // type into whichever input is present
        safeTypeIntoTaskName(taskName);

        // close the modal (save happens automatically on close)
        try {
            // try original close locator first, fallback to closing modal via JS or Escape
            try {
                click(closeTaskButton);
            } catch (Exception e) {
                logger.debug("closeTaskButton not clickable, trying Escape key or JS close");
                // fallback: send ESC to close
                actionBot.jsClick(closeTaskButton); // try js click as last resort
            }
        } catch (Exception e) {
            logger.warn("Could not close task modal normally: {}", e.getMessage());
        }

        // toast should appear because system auto-saves on close
        waitForToastToDisappear();
    }

    // Simple alias to keep API intuitive
    public void createTask(String taskName) {
        addTask(taskName);
    }

    // ================= EDIT BY NAME =================

    public void editTaskByName(String currentName, String newName) {
        waitForPageToBeReady();

        // locate the task element by visible text and open its menu
        By taskLabel = By.xpath("//*[normalize-space()='" + currentName + "']");
        By actionBtn = By.xpath("//*[normalize-space()='" + currentName + "']/ancestor::div[contains(@class,'projectCard') or contains(@class,'p-card') or contains(@class,'task') or contains(@class,'p-grid')][1]//button[contains(@class,'dropdown-toggle') or contains(@class,'btn')][1]");

        try {
            WaitUtils.waitForElementToBeClickable(driver, actionBtn, 15);
            click(actionBtn);
            click(dropdownOption("Open"));

            safeTypeIntoTaskName(newName);

            // close modal (auto-save)
            try {
                click(closeTaskButton);
            } catch (Exception e) {
                try {
                    actionBot.jsClick(closeTaskButton);
                } catch (Exception ex) {
                    logger.warn("Could not close edit modal: {}", ex.getMessage());
                }
            }

            waitForToastToDisappear();
        } catch (Exception e) {
            logger.warn("Could not edit task '{}' -> '{}': {}", currentName, newName, e.getMessage());
            throw e;
        }
    }

    // ================= EDIT FIRST TASK =================

    public void editFirstTask(String newName) {
        waitForPageToBeReady();
        click(firstTaskActionButton);
        click(dropdownOption("Open"));

        // type into whichever input is present
        safeTypeIntoTaskName(newName);

        // close modal (auto-save)
        try {
            click(closeTaskButton);
        } catch (Exception e) {
            try {
                actionBot.jsClick(closeTaskButton);
            } catch (Exception ex) {
                logger.warn("Could not close edit modal: {}", ex.getMessage());
            }
        }

        waitForToastToDisappear();
    }

    // ================= DELETE FIRST TASK =================

    public void deleteFirstTask() {
        waitForPageToBeReady();
        click(firstTaskActionButton);
        click(dropdownOption("Delete"));
        click(confirmDeleteTaskButton);
        waitForToastToDisappear();
    }

    // ================= ASSERTION =================

    public String getToastMessage() {
        return getText(toastSuccess).trim();
    }
}

