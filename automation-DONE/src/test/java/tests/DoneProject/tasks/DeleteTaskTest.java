package tests.DoneProject.tasks;

import com.DoneProject.pages.LoginPage;
import com.DoneProject.pages.NavBarPage;
import com.DoneProject.pages.TasksPage;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import tests.DoneProject.BaseTest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

public class DeleteTaskTest extends BaseTest {

    private static final Logger logger = LoggerFactory.getLogger(DeleteTaskTest.class);
    private TasksPage tasksPage;
    private final String USERNAME = "ismealadmin";
    private final String PASSWORD = "123456";

    @BeforeMethod
    public void setupTest() {
        logger.info("Setting up DeleteTaskTest: logging in and navigating to TasksBoard");
        new LoginPage(driver).login(USERNAME, PASSWORD);
        new NavBarPage().goToTasksBoard();
        tasksPage = new TasksPage();
    }

    @Test
    public void testDeleteTaskSuccessfully() {
        String taskName = "AutoTask_ToDelete_" + System.currentTimeMillis();
        logger.info("Executing testDeleteTaskSuccessfully for task: {}", taskName);
        
        // Setup: Create task to delete
        tasksPage.addTask(taskName);
        tasksPage.waitForToastToDisappear();
        
        // Act: Delete the task
        tasksPage.deleteTask(taskName);
        
        // Assert: Success toast
        List<String> messages = tasksPage.getAllToastMessages();
        Assert.assertTrue(messages.stream().anyMatch(m -> m.contains("successfully")), 
            "Expected 'successfully' toast not found after deleting! Actual: " + messages);
        tasksPage.waitForToastToDisappear();

        // Verify task no longer displayed
        Assert.assertFalse(tasksPage.isTaskDisplayed(taskName), "Deleted task should no longer be displayed in the list!");
    }
}
