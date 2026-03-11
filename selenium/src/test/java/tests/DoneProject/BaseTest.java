package tests.DoneProject;
import com.DoneProject.drivers.WebDriverFactory;
import com.DoneProject.utils.ConfigReader;
import org.openqa.selenium.WebDriver;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;

public class BaseTest {

    protected WebDriver driver;

    @BeforeMethod
    public void setup() {

        String browser = ConfigReader.getProperty("browser");

        driver = WebDriverFactory.initDriver(browser);

        driver.manage().window().maximize();

        driver.get(ConfigReader.getProperty("url"));
    }

//    @AfterMethod
//    public void tearDown() {
//
//        if (WebDriverFactory.getDriver() != null) {
//            WebDriverFactory.getDriver().quit();
//        }
//    }
}