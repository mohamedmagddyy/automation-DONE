package tests.DoneProject.users;

import com.DoneProject.Pages.LoginPage;
import com.DoneProject.Pages.NavBarPage;
import com.DoneProject.Pages.UsersPage;
import tests.DoneProject.BaseTest;
import org.testng.annotations.Test;

public class AddUserTest extends BaseTest {

    @Test
    public void testAddUser() {
        new LoginPage().login("ismealadmin", "123456");

        NavBarPage navBar    = new NavBarPage();
        UsersPage  usersPage = new UsersPage();

        navBar.goToUsers();
        usersPage.clickAddUser();
        usersPage.fillUserForm(
                "testuser012",
                "Automation User",
                "testuser012@mail.com",
                "01012345678",
                "2000-01-01",
                "مشرف",
                "Full Time",
                "100",
                "1111",
                "aA@123Mah"
        );
        usersPage.saveUser();
    }
}