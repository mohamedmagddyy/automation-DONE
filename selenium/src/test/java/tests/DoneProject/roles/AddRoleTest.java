package tests.DoneProject.roles;

import com.DoneProject.Pages.LoginPage;
import com.DoneProject.Pages.NavBarPage;
import com.DoneProject.Pages.RolesPage;
import tests.DoneProject.BaseTest;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.util.List;

public class AddRoleTest extends BaseTest {

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
    public void testAddRoleSuccessfully() {
        String roleName = "AutoRole_" + System.currentTimeMillis();
        rolesPage.addRole(roleName);
        
        List<String> messages = rolesPage.getAllToastMessages();
        Assert.assertTrue(messages.stream().anyMatch(m -> m.contains("successfully")), "Role creation failed! Toast messages: " + messages);
        rolesPage.waitForToastToDisappear();
    }

    @Test(priority = 2)
    public void addRoleWithSpecialChars() {
        String specialRole = "Role!@#$%^" + System.currentTimeMillis();
        rolesPage.addRole(specialRole);
        
        List<String> messages = rolesPage.getAllToastMessages();
        Assert.assertTrue(messages.stream().anyMatch(m -> m.contains("successfully")), "Role with special chars failed! Toast messages: " + messages);
        rolesPage.waitForToastToDisappear();
    }

    @Test(priority = 3)
    public void addAndDeleteRoleLifecycle() {
        String tempRole = "DeleteMe_" + System.currentTimeMillis();
        rolesPage.addRole(tempRole);
        
        List<String> addMessages = rolesPage.getAllToastMessages();
        Assert.assertTrue(addMessages.stream().anyMatch(m -> m.contains("successfully")), "Role creation failed! Toast: " + addMessages);
        rolesPage.waitForToastToDisappear();

        rolesPage.deleteRoleByName(tempRole);
        List<String> deleteMessages = rolesPage.getAllToastMessages();
        Assert.assertTrue(deleteMessages.stream().anyMatch(m -> m.contains("successfully")), "Role deletion failed! Toast: " + deleteMessages);
        rolesPage.waitForToastToDisappear();
    }

    @Test(priority = 4)
    public void addRoleAndEditPermissions() {
        String permRole = "PermRole_" + System.currentTimeMillis();
        rolesPage.addRole(permRole);
        rolesPage.waitForToastToDisappear();

        rolesPage.openEditPermissionsByName(permRole);
        rolesPage.selectPermissionsAndMove("View dashboard", "Add Project");
        rolesPage.savePermissions();
        rolesPage.waitForToastToDisappear();
    }
}