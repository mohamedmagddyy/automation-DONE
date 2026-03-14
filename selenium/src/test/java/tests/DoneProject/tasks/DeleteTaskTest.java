package tests.DoneProject.tasks;

import com.DoneProject.Pages.LoginPage;
import com.DoneProject.Pages.NavBarPage;
import com.DoneProject.Pages.TasksPage;
import com.DoneProject.drivers.WebDriverFactory;
import com.DoneProject.utils.Urls;
import org.openqa.selenium.WebDriver;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

public class DeleteTaskTest {
    WebDriver driver;
    LoginPage loginPage;
    NavBarPage navBar;
    TasksPage tasksPage = new TasksPage();

    @BeforeClass(alwaysRun = true)
    public void setUp() {
        driver = WebDriverFactory.getDriver();

        loginPage = new LoginPage();
        navBar = new NavBarPage();

        // فتح صفحة login
        driver.get(Urls.BASE_URL + "/login");

        // تسجيل دخول
        loginPage.login("ismealadmin", "123456");
    }

    @Test
    public void testDeleteTask() {
        navBar.goToTasksBoard();
        tasksPage.deleteFirstTask();
    }

//    @AfterClass
//    public void tearDown() {
//        DriverManager.quitDriver();
//    }
}
