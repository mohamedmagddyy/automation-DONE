package tests.DoneProject.tasks;

import com.DoneProject.Pages.LoginPage;
import com.DoneProject.Pages.NavBarPage;
import com.DoneProject.Pages.TasksPage;
import com.DoneProject.Pages.UsersPage;
import com.DoneProject.utils.DriverManager;
import com.DoneProject.utils.Urls;
import org.openqa.selenium.WebDriver;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

public class AddTaskTest {
    WebDriver driver;
    LoginPage loginPage;
    NavBarPage navBar;
    UsersPage usersPage = new UsersPage();
    TasksPage tasksPage = new TasksPage();

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

    @Test()
    public void testAddTask() {
        navBar.goToTasksBoard();
        tasksPage.addTask("Automation Task");
    }


//    @AfterClass
//    public void tearDown() {
//        DriverManager.quitDriver();
//    }
}

