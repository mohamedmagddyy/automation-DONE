package tests.DoneProject.roles;

import com.DoneProject.pages.LoginPage;
import com.DoneProject.pages.NavBarPage;
import com.DoneProject.pages.RolesPage;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.NoSuchElementException;
import tests.DoneProject.BaseTest;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.util.ArrayList;
import java.util.List;

public class DeleteRoleTest extends BaseTest {

    private RolesPage rolesPage;

    private final String USERNAME = "ismealadmin";
    private final String PASSWORD = "123456";

    @BeforeMethod
    public void setupTest() {
        LoginPage loginPage = new LoginPage(driver);
        loginPage.login(USERNAME, PASSWORD);

        NavBarPage navBar = new NavBarPage();
        navBar.goToRoles();

        rolesPage = new RolesPage();
    }

    @Test(priority = 1)
    public void deleteExistingRole() {
        // Setup: Create a single role dynamically
        String roleToDelete = "RoleToDelete_" + System.currentTimeMillis();
        rolesPage.addRole(roleToDelete);
        rolesPage.waitForToastToDisappear();

        // Act: Delete the role
        rolesPage.deleteRoleByName(roleToDelete);

        // Assert: Verify deletion trigger success toast
        List<String> messages = rolesPage.getAllToastMessages();
        Assert.assertTrue(messages.stream().anyMatch(m -> m.contains("successfully")), "Role deletion failed!");
        rolesPage.waitForToastToDisappear();
    }

    @Test(priority = 2, expectedExceptions = {TimeoutException.class, NoSuchElementException.class, Exception.class})
    public void deleteNonExistingRole() {
        // Act: Attempt to delete a role that doesn't exist
        // This is expected to throw an exception when the ActionBot fails to find the target row.
        String nonExistingRole = "NonExistingRole_" + System.currentTimeMillis();
        rolesPage.deleteRoleByName(nonExistingRole);
    }

    @Test(priority = 3)
    public void deleteMultipleRolesSequentially() {
        int numRoles = 3;
        List<String> rolesToDelete = new ArrayList<>();
        long timestamp = System.currentTimeMillis();

        // Setup: Create multiple roles first to ensure isolation
        for (int i = 1; i <= numRoles; i++) {
            String roleName = "SeqDeleteRole_" + i + "_" + timestamp;
            rolesPage.addRole(roleName);
            rolesPage.waitForToastToDisappear();
            rolesToDelete.add(roleName);
        }

        // Act & Assert: Delete each created role sequentially
        for (String roleName : rolesToDelete) {
            rolesPage.deleteRoleByName(roleName);
            
            List<String> messages = rolesPage.getAllToastMessages();
            Assert.assertTrue(messages.stream().anyMatch(m -> m.contains("successfully")), 
                    "Sequential deletion failed for role: " + roleName);
            
            rolesPage.waitForToastToDisappear();
        }
    }
}
