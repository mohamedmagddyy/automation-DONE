package tests.DoneProject.roles;

import com.DoneProject.Pages.LoginPage;
import com.DoneProject.Pages.RolesPage;
import tests.DoneProject.BaseTest;
import org.testng.Assert;
import org.testng.annotations.Test;
import java.util.List;

public class AddRoleTest extends BaseTest {

    @Test(priority = 1)
    public void testAddRoleSuccessfully() {
        String roleName = "AutoRole_" + System.currentTimeMillis();
        RolesPage rolesPage = new LoginPage()
                .login("ismealadmin", "123456")
                .goToRoles()
                .addRole(roleName);
        
        List<String> messages = rolesPage.getAllToastMessages();
        Assert.assertTrue(messages.stream().anyMatch(m -> m.contains("successfully")), "Role creation failed!");
    }

    @Test(priority = 2)
    public void addRoleWithSpecialChars() {
        String specialRole = "Role!@#$%^" + System.currentTimeMillis();
        RolesPage rolesPage = new LoginPage()
                .login("ismealadmin", "123456")
                .goToRoles()
                .addRole(specialRole);
        
        List<String> messages = rolesPage.getAllToastMessages();
        Assert.assertTrue(messages.stream().anyMatch(m -> m.contains("successfully")), "Role with special chars failed!");
    }

    @Test(priority = 3)
    public void addAndDeleteRoleLifecycle() {
        String tempRole = "DeleteMe_" + System.currentTimeMillis();
        RolesPage rolesPage = new LoginPage()
                .login("ismealadmin", "123456")
                .goToRoles()
                .addRole(tempRole);
        
        Assert.assertTrue(rolesPage.getAllToastMessages().stream().anyMatch(m -> m.contains("successfully")));
        rolesPage.waitForToastToDisappear();

        rolesPage.deleteRoleByName(tempRole);
        Assert.assertTrue(rolesPage.getAllToastMessages().stream().anyMatch(m -> m.contains("successfully")));
    }

    @Test(priority = 4)
    public void addRoleAndEditPermissions() {
        String permRole = "PermRole_" + System.currentTimeMillis();
        new LoginPage()
                .login("ismealadmin", "123456")
                .goToRoles()
                .addRole(permRole)
                .waitForToastToDisappear();

        new RolesPage()
                .openEditRoleByName(permRole)
                .selectPermissionsAndMove("View dashboard", "Add Project")
                .clickSave()
                .getAllToastMessages();
    }
}