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

public class DeleteUserTest extends BaseTest {

    private static final Logger logger = LoggerFactory.getLogger(DeleteUserTest.class);
    private UsersPage usersPage;
    private final String ADMIN_USER = "ismealadmin";
    private final String ADMIN_PASS = "123456";

    @BeforeMethod
    public void setupTest() {
        logger.info("Setting up DeleteUserTest: logging in and navigating to Users page");
        new LoginPage(driver).login(ADMIN_USER, ADMIN_PASS);
        new NavBarPage().goToUsers();
        usersPage = new UsersPage();
    }

    @Test(priority = 1, description = "Verify successful deletion of an existing user")
    public void testDeleteUserSuccessfully() {
        String timestamp = String.valueOf(System.currentTimeMillis());
        String userName = "UserToDelete_" + timestamp;
        
        logger.info("Setting up user to delete: {}", userName);
        usersPage.addUser(userName, "aA@123Mah", "Delete Me", "مشرف");
        usersPage.waitForToastToDisappear();
        
        logger.info("Executing testDeleteUserSuccessfully for user: {}", userName);
        
        // Act
        usersPage.deleteUser(userName);

        // Assert toast message
        List<String> messages = usersPage.getAllToastMessages();
        Assert.assertTrue(messages.stream().anyMatch(m -> m.contains("successfully")), 
            "Expected 'successfully' toast not found after delete! Actual: " + messages);
        usersPage.waitForToastToDisappear();

        // Assert user removed from table
        Assert.assertFalse(usersPage.isUserDisplayed(userName), "Deleted user should no longer be displayed in the table!");
    }
}
