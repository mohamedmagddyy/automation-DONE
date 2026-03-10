package tests.DoneProject.roles;

import com.DoneProject.Pages.LoginPage;
import com.DoneProject.Pages.NavBarPage;
import com.DoneProject.Pages.RolesPage;
import com.DoneProject.utils.DriverManager;
import com.DoneProject.utils.LogUtils;
import com.DoneProject.utils.Urls;
import org.openqa.selenium.WebDriver;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

public class AddRoleTest {

    WebDriver driver;
    LoginPage loginPage;
    RolesPage rolesPage;
    NavBarPage navBar;

    @BeforeClass(alwaysRun = true)
    public void setUp() {
        driver = DriverManager.getDriver();
        loginPage = new LoginPage();
        rolesPage = new RolesPage();
        navBar = new NavBarPage();

        driver.get(Urls.BASE_URL + "/login");
        loginPage.login("ismealadmin", "123456");
    }

    @Test
    public void testAddRole() {
        LogUtils.log.info("Test Started");
        navBar.goToRoles();
        rolesPage.addRole("Automation RoleEeewwewe");
    }
}
