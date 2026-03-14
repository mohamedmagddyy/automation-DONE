package tests.DoneProject.users;

import com.DoneProject.Pages.LoginPage;
import com.DoneProject.Pages.NavBarPage;
import com.DoneProject.Pages.UsersPage;
import com.DoneProject.drivers.WebDriverFactory;
import com.DoneProject.utils.Urls;
import org.openqa.selenium.WebDriver;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

public class DeleteUserTest {

    WebDriver driver;
    LoginPage loginPage;
    UsersPage usersPage;
    NavBarPage navBar;

    @BeforeClass(alwaysRun = true)
    public void setUp() {
        driver = WebDriverFactory.getDriver();

        loginPage = new LoginPage();
        navBar = new NavBarPage();
        usersPage = new UsersPage();

        // فتح صفحة login
        driver.get(Urls.BASE_URL + "/login");

        // تسجيل دخول
        loginPage.login("ismealadmin", "123456");
    }

    @Test
    public void testDeleteUser() {
        navBar.goToUsers();
        usersPage.deleteUserByName("testuser012");
    }

//    @AfterClass
//    public void tearDown() {
//        DriverManager.quitDriver();
//    }
}
