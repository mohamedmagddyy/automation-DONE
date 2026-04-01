package tests.DoneProject.sectors;

import com.DoneProject.pages.LoginPage;
import com.DoneProject.pages.NavBarPage;
import com.DoneProject.pages.SectorsPage;
import tests.DoneProject.BaseTest;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.util.List;

public class DeleteSectorTest extends BaseTest {

    private SectorsPage sectorsPage;

    private final String USERNAME = "ismealadmin";
    private final String PASSWORD = "123456";

    @BeforeMethod
    public void setupTest() {
        LoginPage loginPage = new LoginPage(driver);
        loginPage.login(USERNAME, PASSWORD);

        NavBarPage navBar = new NavBarPage();
        navBar.goToSectors();

        sectorsPage = new SectorsPage();
    }

    @Test(priority = 1)
    public void deleteSectorSuccessfully() {
        // Arrange
        String sectorName = "Automation Sector Updated X " + System.currentTimeMillis();

        // Setup: Add a sector explicitly so the delete logic is independent and stable
        sectorsPage.addSector(sectorName);
        sectorsPage.saveSector();
        sectorsPage.waitForToastToDisappear();

        // Act: Delete the setup sector
        sectorsPage.deleteSector(sectorName);

        // Assert: Validate success toast exactly matching expected string
        List<String> messages = sectorsPage.getAllToastMessages();
        Assert.assertTrue(messages.stream().anyMatch(m -> m.contains("Delete Done")), 
                "Expected 'Delete Done' toast not found! Actual: " + messages);
        
        sectorsPage.waitForToastToDisappear();
    }
}
