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

public class ArchiveTaskTest extends BaseTest {

    private static final Logger logger = LoggerFactory.getLogger(ArchiveTaskTest.class);
    private TasksPage tasksPage;
    private final String USERNAME = "ismealadmin";
    private final String PASSWORD = "123456";

    @BeforeMethod
    public void setupTest() {
        logger.info("Setting up ArchiveTaskTest: logging in and navigating to TasksBoard");
        new LoginPage(driver).login(USERNAME, PASSWORD);
        new NavBarPage().goToTasksBoard();
        tasksPage = new TasksPage();
    }

    @Test
    public void testArchiveTaskSuccessfully() {
        String taskName = "AutoTask_ToArchive_" + System.currentTimeMillis();
        logger.info("Executing testArchiveTaskSuccessfully for task: {}", taskName);
        
        // Setup: Create task to archive
        tasksPage.addTask(taskName);
        tasksPage.waitForToastToDisappear();
        
        // Act: Archive the task
        tasksPage.moveToArchive(taskName);
        
        // Assert: Success toast
        List<String> messages = tasksPage.getAllToastMessages();
        Assert.assertTrue(messages.stream().anyMatch(m -> m.contains("successfully")), 
            "Expected 'successfully' toast not found after archiving! Actual: " + messages);
        tasksPage.waitForToastToDisappear();

        // Verify task no longer displayed in the active active board
        Assert.assertFalse(tasksPage.isTaskDisplayed(taskName), "Archived task should not be displayed in the active list!");
    }
}
