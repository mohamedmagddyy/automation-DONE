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

public class EditUserTest extends BaseTest {

    private static final Logger logger = LoggerFactory.getLogger(EditUserTest.class);
    private UsersPage usersPage;
    private final String ADMIN_USER = "ismealadmin";
    private final String ADMIN_PASS = "123456";

    @BeforeMethod
    public void setupTest() {
        logger.info("Setting up EditUserTest: logging in and navigating to Users page");
        new LoginPage(driver).login(ADMIN_USER, ADMIN_PASS);
        new NavBarPage().goToUsers();
        usersPage = new UsersPage();
    }

    @Test(priority = 1, description = "Verify successful editing of an existing user")
    public void testEditUserSuccessfully() {
        String timestamp = String.valueOf(System.currentTimeMillis());
        String initialUserName = "UserToEdit_" + timestamp;
        String updatedUserName = "UserUpdated_" + timestamp;
        
        logger.info("Setting up user to edit: {}", initialUserName);
        usersPage.addUser(initialUserName, "aA@123Mah", "Initial Name", "مشرف");
        usersPage.waitForToastToDisappear();
        
        logger.info("Executing testEditUserSuccessfully: {} -> {}", initialUserName, updatedUserName);
        
        // Act
        usersPage.editUser(initialUserName, updatedUserName, "newPass@123", "Updated Full Name", "مشرف");

        // Assert toast message
        List<String> messages = usersPage.getAllToastMessages();
        Assert.assertTrue(messages.stream().anyMatch(m -> m.contains("successfully")), 
            "Expected 'successfully' toast not found after edit! Actual: " + messages);
        usersPage.waitForToastToDisappear();

        // Assert updated user visibility in table
        Assert.assertTrue(usersPage.isUserDisplayed(updatedUserName), "Updated user was not found in the users table!");
    }
}
