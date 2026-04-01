package tests.DoneProject.users;

import com.DoneProject.pages.LoginPage;
import com.DoneProject.pages.NavBarPage;
import com.DoneProject.pages.UsersPage;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import tests.DoneProject.BaseTest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

public class AddUserTest extends BaseTest {

    private static final Logger logger = LoggerFactory.getLogger(AddUserTest.class);
    private UsersPage usersPage;
    private final String ADMIN_USER = "ismealadmin";
    private final String ADMIN_PASS = "123456";

    @BeforeMethod
    public void setupTest() {
        logger.info("Setting up AddUserTest: logging in and navigating to Users page");
        new LoginPage(driver).login(ADMIN_USER, ADMIN_PASS);
        new NavBarPage().goToUsers();
        usersPage = new UsersPage();
    }

    @Test(priority = 1, description = "Verify successful addition of a new user")
    public void testAddUserSuccessfully() {
        String timestamp = String.valueOf(System.currentTimeMillis());
        String userName = "AutoUser_" + timestamp;
        String fullName = "Automation User " + timestamp;
        
        logger.info("Executing testAddUserSuccessfully with userName: {}", userName);
        
        // Act
        usersPage.addUser(userName, "aA@123Mah", fullName, "مشرف");

        // Assert toast message
        List<String> messages = usersPage.getAllToastMessages();
        Assert.assertTrue(messages.stream().anyMatch(m -> m.contains("successfully")), 
            "Expected 'successfully' toast not found! Actual: " + messages);
        usersPage.waitForToastToDisappear();

        // Assert user visibility in table
        Assert.assertTrue(usersPage.isUserDisplayed(userName), "Added user was not found in the users table!");
    }
}
