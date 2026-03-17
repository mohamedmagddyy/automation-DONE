package tests.DoneProject.users;

import com.DoneProject.Pages.LoginPage;
import com.DoneProject.Pages.UsersPage;
import tests.DoneProject.BaseTest;
import org.testng.Assert;
import org.testng.annotations.Test;
import java.util.List;

public class AddUserTest extends BaseTest {

    @Test(priority = 1)
    public void testAddUserSuccessfully() {
        String username = "autouser" + System.currentTimeMillis();
        UsersPage usersPage = new LoginPage()
                .login("ismealadmin", "123456")
                .goToUsers()
                .clickAddUser()
                .fillUserForm(username, "Automation User", username + "@mail.com", "01012345678", "2000-01-01", "مشرف", "Full Time", "100", "E123", "aA@123Mah")
                .saveUser();
        
        List<String> messages = usersPage.getAllToastMessages();
        Assert.assertTrue(messages.stream().anyMatch(m -> m.contains("successfully")), "User creation failed!");
    }

    @Test(priority = 2)
    public void addUserWithExistingEmail() {
        UsersPage usersPage = new LoginPage()
                .login("ismealadmin", "123456")
                .goToUsers()
                .clickAddUser()
                .fillUserForm("colliduser", "Collision User", "testuser012@mail.com", "01012345678", "1990-05-05", "مشرف", "Full Time", "150", "9999", "aA@123Mah")
                .saveUser();
        
        List<String> messages = usersPage.getAllToastMessages();
        Assert.assertTrue(messages.stream().anyMatch(msg -> msg.toLowerCase().contains("exist") || msg.toLowerCase().contains("error")), "Expected error for duplicate email!");
    }

    @Test(priority = 3)
    public void addAndDeleteUserLifecycle() {
        String username = "lifecycle" + System.currentTimeMillis();
        UsersPage usersPage = new LoginPage()
                .login("ismealadmin", "123456")
                .goToUsers()
                .clickAddUser()
                .fillUserForm(username, "Lifecycle User", username + "@life.com", "01155667788", "1985-10-10", "مشرف", "Full Time", "200", "L888", "aA@123Mah")
                .saveUser();
        
        Assert.assertTrue(usersPage.getAllToastMessages().stream().anyMatch(m -> m.contains("successfully")));
        usersPage.waitForToastToDisappear();

        usersPage.deleteUserByName(username);
        Assert.assertTrue(usersPage.getAllToastMessages().stream().anyMatch(m -> m.contains("successfully")));
    }
}