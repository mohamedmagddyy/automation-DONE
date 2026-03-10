package tests.DoneProject.users;

import com.DoneProject.Pages.LoginPage;
import com.DoneProject.Pages.NavBarPage;
import com.DoneProject.Pages.UsersPage;
import com.DoneProject.utils.DriverManager;
import com.DoneProject.utils.Urls;
import org.openqa.selenium.WebDriver;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

public class AddUserTest {
    WebDriver driver;
    LoginPage loginPage;
    NavBarPage navBar;
    UsersPage usersPage = new UsersPage();

    @BeforeClass(alwaysRun = true)
    public void setUp() {
        driver = DriverManager.getDriver();
        loginPage = new LoginPage();
        navBar = new NavBarPage();
        usersPage = new UsersPage();

        // فتح صفحة login
        driver.get(Urls.BASE_URL + "/login");

        // تسجيل دخول
        loginPage.login("ismealadmin", "123456");
    }

    @Test
    public void testAddUser() {
        navBar.goToUsers();
        usersPage.clickAddUser();
        usersPage.fillUserForm("testuser012", "Automation User", "testuser012@mail.com",
                "01012345678", "2000-01-01", "مشرف", "Full Time", "100", "1111", "aA@123Mah");
        usersPage.saveUser();
    }

//    @AfterClass
//    public void tearDown() {
//        DriverManager.quitDriver();
//    }
}
