package tests.DoneProject.sectors;

import com.DoneProject.Pages.LoginPage;
import com.DoneProject.Pages.NavBarPage;
import com.DoneProject.Pages.SectorsPage;
import tests.DoneProject.BaseTest;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.util.List;

public class AddEditDeleteSectorTest extends BaseTest {

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
    public void addSectorSuccessfully() {
        String sectorName = "AutoSector_" + System.currentTimeMillis();
        sectorsPage.addSector(sectorName);
        sectorsPage.saveSector();

        List<String> messages = sectorsPage.getAllToastMessages();
        Assert.assertTrue(messages.stream().anyMatch(m -> m.contains("Add Done")), "Expected 'Add Done' toast not found! Actual: " + messages);
        sectorsPage.waitForToastToDisappear();
    }

    @Test(priority = 2)
    public void editSectorSuccessfully() {
        long timestamp = System.currentTimeMillis();
        String oldName = "BaseSector_" + timestamp;
        
        // Setup: Add a sector to edit
        sectorsPage.addSector(oldName);
        sectorsPage.saveSector();
        sectorsPage.waitForToastToDisappear();

        // Act: Edit the newly added sector
        String newName = "UpdatedSector_" + timestamp;
        sectorsPage.editSector(oldName, newName);

        // Assert
        List<String> messages = sectorsPage.getAllToastMessages();
        Assert.assertTrue(messages.stream().anyMatch(m -> m.contains("EditDone")), "Expected 'EditDone' toast not found! Actual: " + messages);
        sectorsPage.waitForToastToDisappear();
    }

    @Test(priority = 3)
    public void deleteSectorSuccessfully() {
        long timestamp = System.currentTimeMillis();
        String sectorName = "DeleteSector_" + timestamp;

        // Setup: Add a sector to delete
        sectorsPage.addSector(sectorName);
        sectorsPage.saveSector();
        sectorsPage.waitForToastToDisappear();

        // Act: Delete the sector
        sectorsPage.deleteSector(sectorName);

        // Assert
        List<String> messages = sectorsPage.getAllToastMessages();
        Assert.assertTrue(messages.stream().anyMatch(m -> m.contains("Delete Done")), "Expected 'Delete Done' toast not found! Actual: " + messages);
        sectorsPage.waitForToastToDisappear();
    }
}