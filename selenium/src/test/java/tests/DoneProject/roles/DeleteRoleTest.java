package tests.DoneProject.roles;

import com.DoneProject.Pages.LoginPage;
import com.DoneProject.Pages.NavBarPage;
import com.DoneProject.Pages.RolesPage;
import com.DoneProject.drivers.WebDriverFactory;
import com.DoneProject.utils.Urls;
import org.openqa.selenium.WebDriver;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

public class DeleteRoleTest {

    WebDriver driver;
    LoginPage loginPage;
    RolesPage RolesPage;
    NavBarPage navBar;

    @BeforeClass(alwaysRun = true)
    public void setUp() {
        driver = WebDriverFactory.getDriver();
        loginPage = new LoginPage();
        RolesPage = new RolesPage();
        navBar = new NavBarPage();

        // فتح صفحة login
        driver.get(Urls.BASE_URL + "/login");

        // تسجيل دخول
        loginPage.login("ismealadmin", "123456");
    }


    @Test
    public void testDeleteRole() {
        navBar.goToRoles();
        RolesPage.deleteRoleByName("Automation Role");
    }

//    @AfterClass
//    public void tearDown() {
//        DriverManager.quitDriver();
//    }
}
