package tests.DoneProject.tasks;

import com.DoneProject.Pages.LoginPage;
import com.DoneProject.Pages.NavBarPage;
import com.DoneProject.Pages.TasksPage;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import tests.DoneProject.BaseTest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

public class EditTaskTest extends BaseTest {

    private static final Logger logger = LoggerFactory.getLogger(EditTaskTest.class);
    private TasksPage tasksPage;
    private final String USERNAME = "ismealadmin";
    private final String PASSWORD = "123456";

    @BeforeMethod
    public void setupTest() {
        logger.info("Setting up EditTaskTest: logging in and navigating to TasksBoard");
        new LoginPage(driver).login(USERNAME, PASSWORD);
        new NavBarPage().goToTasksBoard();
        tasksPage = new TasksPage();
    }

    @Test
    public void testEditTaskStatus() {
        String taskName = "AutoTask_ToEdit_" + System.currentTimeMillis();
        logger.info("Executing testEditTaskStatus with taskName: {}", taskName);
        
        // Setup: Create task to edit
        tasksPage.addTask(taskName);
        tasksPage.waitForToastToDisappear();
        
        // Act: Edit status using the explicit POM method
        tasksPage.editTaskStatus(taskName);
        
        // Assert
        List<String> messages = tasksPage.getAllToastMessages();
        Assert.assertTrue(messages.stream().anyMatch(m -> m.contains("successfully")), 
            "Expected 'successfully' toast not found after editing status! Actual: " + messages);
        tasksPage.waitForToastToDisappear();
    }
}