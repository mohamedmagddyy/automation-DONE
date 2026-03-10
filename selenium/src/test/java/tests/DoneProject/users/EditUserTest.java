//package tests.DoneProject.users;
//
//import com.DoneProject.Pages.LoginPage;
//import com.DoneProject.Pages.UsersPage;
//import com.DoneProject.utils.DriverManager;
//import org.openqa.selenium.WebDriver;
//import org.testng.annotations.BeforeClass;
//import org.testng.annotations.Test;
//
//public class EditUserTest {
//
//    WebDriver driver;
//    LoginPage loginPage;
//    UsersPage usersPage;
//
//    @BeforeClass
//    public void setUp() {
//        driver = DriverManager.getDriver();
//        loginPage = new LoginPage();
//        usersPage = new UsersPage();
//
//        driver.get("https://donefronttesting1.keyframe-eg.org/login");
//        loginPage.login("ismealadmin", "123456");
//    }
//
//    @Test
//    public void testEditUser() {
//        usersPage.editFirstUser("Updated Automation User");
//    }
//
//    @AfterClass
//    public void tearDown() {
//        DriverManager.quitDriver();
//    }
//}
