package tests.DoneProject.roles;

import com.DoneProject.pages.LoginPage;
import com.DoneProject.pages.NavBarPage;
import com.DoneProject.pages.RolesPage;
import tests.DoneProject.BaseTest;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.util.List;

public class EditRoleTest extends BaseTest {

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
    public void editRoleNameSuccessfully() {
        // Setup: Create a base role
        long timestamp = System.currentTimeMillis();
        String oldName = "OldRole_" + timestamp;
        rolesPage.addRole(oldName);
        rolesPage.waitForToastToDisappear();

        // Act: Edit the role name
        String newName = "UpdatedRole_" + timestamp;
        rolesPage.editRole(oldName, newName);

        // Assert: Validate success toast
        List<String> messages = rolesPage.getAllToastMessages();
        Assert.assertTrue(messages.stream().anyMatch(m -> m.contains("successfully")), "Edit role name failed! Toasts: " + messages);
        rolesPage.waitForToastToDisappear();
    }

    @Test(priority = 2)
    public void editRoleWithSpecialCharacters() {
        // Setup: Create a base role
        String oldName = "BaseSpecialRole_" + System.currentTimeMillis();
        rolesPage.addRole(oldName);
        rolesPage.waitForToastToDisappear();

        // Act: Edit name to include special characters
        String specialName = "Updated_@#$%^_" + System.currentTimeMillis();
        rolesPage.editRole(oldName, specialName);

        // Assert: Validate success toast
        List<String> messages = rolesPage.getAllToastMessages();
        Assert.assertTrue(messages.stream().anyMatch(m -> m.contains("successfully")), "Edit role with special chars failed! Toasts: " + messages);
        rolesPage.waitForToastToDisappear();
    }

    @Test(priority = 3)
    public void editRolePermissionsAndSave() {
        // Setup: Create a base role
        String roleName = "PermRole_" + System.currentTimeMillis();
        rolesPage.addRole(roleName);
        rolesPage.waitForToastToDisappear();

        // Act: Edit permissions
        rolesPage.openEditPermissionsByName(roleName);
        rolesPage.selectPermissionsAndMove("View dashboard", "Add Project");
        rolesPage.savePermissions();

        // Assert: Validate success toast for saving permissions
        List<String> messages = rolesPage.getAllToastMessages();
        Assert.assertTrue(messages.stream().anyMatch(m -> m.contains("successfully")), "Edit role permissions failed! Toasts: " + messages);
        rolesPage.waitForToastToDisappear();
    }
}
