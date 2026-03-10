package tests.DoneProject.sectors;

import com.DoneProject.Pages.LoginPage;
import com.DoneProject.Pages.SectorsPage;
import com.DoneProject.utils.DriverManager;
import com.DoneProject.utils.Urls;
import org.openqa.selenium.WebDriver;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

public class DeleteSectorTest {

    WebDriver driver;
    SectorsPage sectorsPage;

    @BeforeClass
    public void setUp() {
        driver = DriverManager.getDriver();
        sectorsPage = new SectorsPage();

        driver.get(Urls.BASE_URL + "/login");
        new LoginPage().login("ismealadmin", "123456");
    }

    @Test
    public void deleteSectorSuccessfully() {

        String sectorName = "Automation Sector2";

        sectorsPage.deleteSector(sectorName);

        String actualToast = sectorsPage.getToastMessage();

        Assert.assertEquals(
                actualToast,
                "Sector added successfully",
                "❌ Wrong toast message"
        );
    }
}
