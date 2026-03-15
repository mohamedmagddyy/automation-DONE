package com.DoneProject.Pages;

import com.DoneProject.utils.WaitUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class TasksPage extends BasePage {

    private static final Logger logger = LoggerFactory.getLogger(TasksPage.class);

    // ===== Static Locators =====
    private final By addTaskMenuButton = By.id("dropdownMenuButton1");
    private final By createTaskOption  = By.xpath("//a[normalize-space()='Create task']");
    private final By closeTaskButton   = By.cssSelector("#addNewQmission .modal-header button.btn-close[aria-label='Close']");
    private final By confirmDeleteBtn  = By.xpath("//div[contains(@class,'modal') and contains(@class,'show')]//button[normalize-space()='Yes']");
    private final By firstTaskActionBtn = By.xpath("//div[@id='ToDo']//div[@class='p-scrollpanel-content']//div[1]//div[1]//button[1]//i[1]");

    // ✅ Candidates للـ task name input — مرتبة من الأكثر دقة للأقل
    private final By[] taskNameCandidates = {
            By.xpath("//div[@id='addNewQmission']//input[@id='name']"),
            By.xpath("//input[@id='name' or @name='name']"),
            By.cssSelector("input#name"),
            By.xpath("//div[contains(@id,'addNew') or contains(@class,'modal')]//input[@id='name' or @name='name']"),
            By.xpath("//input[contains(@placeholder,'Task') or contains(@placeholder,'task') or contains(@placeholder,'مهمة')]")
    };

    public TasksPage() {
        super();
    }

    // ===== Dynamic Locator =====
    private By dropdownOption(String optionName) {
        return By.xpath("//ul[contains(@class,'dropdown-menu') and contains(@class,'show')]//span[normalize-space()='" + optionName + "']");
    }

    // ===== Type Into Task Name (with fallback candidates) =====
    private void typeTaskName(String text) {
        for (By candidate : taskNameCandidates) {
            try {
                WaitUtils.waitForElementToBeVisible(driver, candidate, 10);
                WebElement el = driver.findElement(candidate);
                if (el.isDisplayed()) {
                    actionBot.scrollTo(candidate);
                    actionBot.type(candidate, text);
                    logger.info("✅ تم كتابة اسم المهمة باستخدام: {}", candidate);
                    return;
                }
            } catch (Exception e) {
                logger.debug("⚠️ Candidate غير متاح: {}", candidate);
            }
        }
        throw new RuntimeException("❌ لم يتم العثور على حقل اسم المهمة");
    }

    // ===== Close Modal =====
    private void closeModal() {
        try {
            click(closeTaskButton);
        } catch (Exception e) {
            try {
                actionBot.jsClick(closeTaskButton);
            } catch (Exception ex) {
                logger.warn("⚠️ تعذّر إغلاق الـ modal: {}", ex.getMessage());
            }
        }
    }

    // ===== Add Task =====
    public void addTask(String taskName) {
        waitForPageToBeReady();
        click(addTaskMenuButton);
        click(createTaskOption);
        typeTaskName(taskName);
        closeModal();
        waitForToastToDisappear();
        logger.info("✅ تم إضافة المهمة: {}", taskName);
    }

    // ===== Edit First Task =====
    public void editFirstTask(String newName) {
        waitForPageToBeReady();
        click(firstTaskActionBtn);
        click(dropdownOption("Open"));
        typeTaskName(newName);
        closeModal();
        waitForToastToDisappear();
        logger.info("✅ تم تعديل المهمة إلى: {}", newName);
    }

    // ===== Delete First Task =====
    public void deleteFirstTask() {
        waitForPageToBeReady();
        click(firstTaskActionBtn);
        click(dropdownOption("Delete"));
        click(confirmDeleteBtn);
        waitForToastToDisappear();
        logger.info("✅ تم حذف المهمة");
    }

    // ===== Toast Message =====
    public String getToastMessage() {
        return super.getToastMessage();
    }
}