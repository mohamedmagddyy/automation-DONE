package tests.DoneProject.sectors;

import com.DoneProject.Pages.LoginPage;
import com.DoneProject.Pages.SectorsPage;
import com.DoneProject.drivers.WebDriverFactory;
import com.DoneProject.utils.Urls;
import org.openqa.selenium.WebDriver;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

public class AddEditDeleteSectorTest {

    WebDriver driver;
    SectorsPage sectorsPage;

    String sectorName = "Automation Sector 3";
    String updatedSectorName = "Automation Sector Updated 3";
    String managerName = "اسماعيل";

    @BeforeClass
    public void setUp() {
        driver = WebDriverFactory.getDriver();
        sectorsPage = new SectorsPage();

        driver.get(Urls.BASE_URL + "/login");

        new LoginPage().login("ismealadmin", "123456");
    }

    @Test(priority = 1)
    public void addSectorSuccessfully() {

        sectorsPage.addSector(sectorName);
        sectorsPage.selectManagerByName(managerName);

        String actualMessage = sectorsPage.getToastMessage();

        Assert.assertEquals(
                actualMessage,
                "Add Done",
                "Add Done"
        );
    }

    @Test(priority = 2, dependsOnMethods = "addSectorSuccessfully")
    public void editSectorSuccessfully() {

        sectorsPage.editSector(sectorName, updatedSectorName);

        String actualToast = sectorsPage.getToastMessage();

        Assert.assertEquals(
                actualToast,
                "EditDone",
                "EditDone"
        );
    }

    @Test(priority = 3, dependsOnMethods = "editSectorSuccessfully")
    public void deleteSectorSuccessfully() {

        sectorsPage.deleteSector(updatedSectorName);

        String actualToast = sectorsPage.getToastMessage();

        Assert.assertEquals(
                actualToast,
                "Delete Done",
                "Delete Done"
        );
    }
}
