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
    RolesPage RolesPage;
    NavBarPage navBar;

    @BeforeClass(alwaysRun = true)
    public void setUp() {
        LogUtils.info("Starting setUp() method");
        driver = DriverManager.getDriver();
        loginPage = new LoginPage();
        RolesPage = new RolesPage();
        navBar = new NavBarPage();

        LogUtils.info("Opening login page");
        driver.get(Urls.BASE_URL + "/login");

        LogUtils.info("Logging in with credentials");
        loginPage.login("ismealadmin", "123456");
        LogUtils.info("Login successful");
    }

    @Test
    public void testAddRole() {
        LogUtils.info("Starting testAddRole() method");
        LogUtils.info("Navigating to Roles page");
        navBar.goToRoles();

        LogUtils.info("Adding new role: Automation RoleE");
        RolesPage.addRole("Automation RoleE");
        LogUtils.info("Role added successfully");
    }

    // ...existing code...
}

