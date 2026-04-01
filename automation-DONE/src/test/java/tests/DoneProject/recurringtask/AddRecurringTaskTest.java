package tests.DoneProject.recurringtask;

import com.DoneProject.pages.LoginPage;
import com.DoneProject.pages.NavBarPage;
import com.DoneProject.pages.RecurringTaskPage;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import tests.DoneProject.BaseTest;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class AddRecurringTaskTest extends BaseTest {

    private RecurringTaskPage recurringTaskPage;

    private final String USERNAME = "ismealadmin";
    private final String PASSWORD = "123456";

    @BeforeMethod
    public void setupTest() {
        // Step 1: Explicit Login
        LoginPage loginPage = new LoginPage(driver);
        loginPage.login(USERNAME, PASSWORD);

        // Step 2: Navigate to the target page via NavBar
        NavBarPage navBar = new NavBarPage();
        recurringTaskPage = navBar.goToRecurringTasks();
    }

    @Test(priority = 1, description = "Add a daily recurring task with all fields filled")
    public void testAddDailyRecurringTask() {
        String taskName = "Daily_AutoTask_" + System.currentTimeMillis();
        recurringTaskPage.addRecurringTask(
                taskName,
                "Project test",
                "Testing daily recurring task creation with automation",
                "To Do",
                "Daily",
                "1",
                "10:00 AM",
                "30",
                Collections.singletonList("ismealadmin"),
                true);

        List<String> messages = recurringTaskPage.getAllToastMessages();
        Assert.assertTrue(messages.stream().anyMatch(m -> m.contains("successfully")),
                "Failed to add daily task! Toast: " + messages);
        recurringTaskPage.waitForToastToDisappear();

        Assert.assertTrue(driver.findElement(recurringTaskPage.taskRow(taskName)).isDisplayed(),
                "Task not found in table!");
    }

    @Test(priority = 2, description = "Add a weekly task with multiple assigned users")
    public void testAddWeeklyTaskWithMultipleUsers() {
        String taskName = "Weekly_MultiUser_" + System.currentTimeMillis();
        recurringTaskPage.addRecurringTask(
                taskName,
                "Project test",
                "Testing weekly recurring task with multiple users",
                "In Progress",
                "Weekly",
                "1",
                "02:00 PM",
                "60",
                Arrays.asList("ismealadmin", "testuser"), // Assumes testuser exists
                true);

        List<String> messages = recurringTaskPage.getAllToastMessages();
        Assert.assertTrue(messages.stream().anyMatch(m -> m.contains("successfully")),
                "Failed to add weekly task! Toast: " + messages);
        recurringTaskPage.waitForToastToDisappear();

        Assert.assertTrue(driver.findElement(recurringTaskPage.taskRow(taskName)).isDisplayed(),
                "Weekly task not found!");
    }

    @Test(priority = 3, description = "Add a monthly recurring task")
    public void testAddMonthlyRecurringTask() {
        String taskName = "Monthly_AutoTask_" + System.currentTimeMillis();
        recurringTaskPage.addRecurringTask(
                taskName,
                "Project test",
                "Testing monthly recurring task",
                "Testing",
                "Monthly",
                "1",
                "09:00 AM",
                "15",
                Collections.singletonList("ismealadmin"),
                false // Inactive
        );

        List<String> messages = recurringTaskPage.getAllToastMessages();
        Assert.assertTrue(messages.stream().anyMatch(m -> m.contains("successfully")),
                "Failed to add monthly task! Toast: " + messages);
        recurringTaskPage.waitForToastToDisappear();

        Assert.assertTrue(driver.findElement(recurringTaskPage.taskRow(taskName)).isDisplayed(),
                "Monthly task not found!");
    }
}
