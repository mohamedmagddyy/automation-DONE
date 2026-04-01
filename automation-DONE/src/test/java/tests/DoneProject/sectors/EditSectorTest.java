package tests.DoneProject.sectors;

import com.DoneProject.pages.LoginPage;
import com.DoneProject.pages.NavBarPage;
import com.DoneProject.pages.SectorsPage;
import tests.DoneProject.BaseTest;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.util.List;

public class EditSectorTest extends BaseTest {

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
    public void editSectorSuccessfully() {
        // Arrange: Generate dynamic names ensuring isolation
        long timestamp = System.currentTimeMillis();
        String baseName = "Automation Sector X " + timestamp;
        String updatedName = "Automation Sector Updated X " + timestamp;

        // Setup: Create the base sector so the edit logic executes flawlessly
        sectorsPage.addSector(baseName);
        sectorsPage.saveSector();
        sectorsPage.waitForToastToDisappear();

        // Act: Edit the base sector
        sectorsPage.editSector(baseName, updatedName);

        // Assert: Validate against the exact specified success toast string
        List<String> messages = sectorsPage.getAllToastMessages();
        Assert.assertTrue(messages.stream().anyMatch(m -> m.contains("EditDone")), 
                "Expected 'EditDone' toast not found! Actual: " + messages);
        
        sectorsPage.waitForToastToDisappear();
    }
}
