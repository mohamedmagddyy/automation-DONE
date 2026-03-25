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

public class AddTaskTest extends BaseTest {

    private static final Logger logger = LoggerFactory.getLogger(AddTaskTest.class);
    private TasksPage tasksPage;
    private final String USERNAME = "ismealadmin";
    private final String PASSWORD = "123456";

    @BeforeMethod
    public void setupTest() {
        logger.info("Setting up AddTaskTest: logging in and navigating to TasksBoard");
        new LoginPage(driver).login(USERNAME, PASSWORD);
        new NavBarPage().goToTasksBoard();
        tasksPage = new TasksPage();
    }

    @Test
    public void testAddTaskSuccessfully() {
        String taskName = "AutoTask_" + System.currentTimeMillis();
        logger.info("Executing testAddTaskSuccessfully with taskName: {}", taskName);
        
        tasksPage.addTask(taskName);

        List<String> messages = tasksPage.getAllToastMessages();
        Assert.assertTrue(messages.stream().anyMatch(m -> m.contains("successfully")), 
            "Expected 'successfully' toast not found! Actual: " + messages);
        tasksPage.waitForToastToDisappear();

        Assert.assertTrue(tasksPage.isTaskDisplayed(taskName), "Task was not displayed in the board: " + taskName);
    }
}