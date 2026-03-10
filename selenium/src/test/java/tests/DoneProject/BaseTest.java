package tests.DoneProject;

import com.DoneProject.utils.DriverManager;
import org.openqa.selenium.WebDriver;
import org.testng.annotations.BeforeMethod;

public class BaseTest {

    protected WebDriver driver;

    @BeforeMethod
    public void setUp() {
        driver = DriverManager.getDriver(); // هيمسك آخر نسخة تلقائي
    }

//    @AfterMethod
//    public void tearDown() {
//        DriverManager.quitDriver();
//    }
}
