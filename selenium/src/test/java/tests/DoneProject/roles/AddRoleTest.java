package tests.DoneProject.roles;

import com.DoneProject.Pages.LoginPage;
import com.DoneProject.Pages.NavBarPage;
import com.DoneProject.Pages.RolesPage;
import tests.DoneProject.BaseTest;
import org.testng.annotations.Test;

public class AddRoleTest extends BaseTest {

    @Test
    public void testAddRole() {
        new LoginPage().login("ismealadmin", "123456");

        NavBarPage navBar    = new NavBarPage();
        RolesPage  rolesPage = new RolesPage();

        navBar.goToRoles();
        rolesPage.addRole("Automation RoleEeewwewe");
    }
}