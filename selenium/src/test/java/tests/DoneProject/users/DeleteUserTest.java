package tests.DoneProject.users;

import com.DoneProject.Pages.LoginPage;
import com.DoneProject.Pages.NavBarPage;
import com.DoneProject.Pages.UsersPage;
import tests.DoneProject.BaseTest;
import org.testng.annotations.Test;

public class DeleteUserTest extends BaseTest {

    @Test
    public void testDeleteUser() {
        new LoginPage().login("ismealadmin", "123456");

        NavBarPage navBar    = new NavBarPage();
        UsersPage  usersPage = new UsersPage();

        navBar.goToUsers();
        usersPage.deleteUserByName("testuser012");
    }
}