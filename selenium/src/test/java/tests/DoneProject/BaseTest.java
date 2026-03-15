package tests.DoneProject;

import com.DoneProject.drivers.WebDriverFactory;
import com.DoneProject.utils.ScreenshotUtils;
import com.DoneProject.utils.Urls;
import org.openqa.selenium.WebDriver;
import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;

public class BaseTest {

    protected WebDriver driver;

    // ✅ BaseTest بيعمل الـ driver بس
    // كل تيست يعمل الـ login بنفسه بأي user يحتاجه

    @BeforeMethod(alwaysRun = true)
    public void setup() {
        driver = WebDriverFactory.initDriver("chrome");
        driver.manage().window().maximize();
        driver.get(Urls.LOGIN_URL);
    }

    @AfterMethod(alwaysRun = true)
    public void tearDown(ITestResult result) {
        if (result.getStatus() == ITestResult.FAILURE) {
            ScreenshotUtils.takeScreenshot(result.getName());
        }
        WebDriver d = WebDriverFactory.getDriver();
        if (d != null) {
            d.quit();
        }
    }
}