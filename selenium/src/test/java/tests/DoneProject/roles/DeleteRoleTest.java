package tests.DoneProject.roles;

import com.DoneProject.Pages.LoginPage;
import com.DoneProject.Pages.NavBarPage;
import com.DoneProject.Pages.RolesPage;
import tests.DoneProject.BaseTest;
import org.testng.annotations.Test;

public class DeleteRoleTest extends BaseTest {

    @Test
    public void testDeleteRole() {
        new LoginPage().login("ismealadmin", "123456");

        NavBarPage navBar    = new NavBarPage();
        RolesPage  rolesPage = new RolesPage();

        navBar.goToRoles();
        rolesPage.deleteRoleByName("Automation Role");
    }
}