package tests.DoneProject.sectors;

import com.DoneProject.pages.LoginPage;
import com.DoneProject.pages.NavBarPage;
import com.DoneProject.pages.SectorsPage;
import tests.DoneProject.BaseTest;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.util.List;

public class AddSectorTest extends BaseTest {

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
        // Arrange
        String sectorName = "Automation Sector " + System.currentTimeMillis();

        // Act: Open modal, fill name, assign manager, and save
        sectorsPage.addSector(sectorName);
        sectorsPage.selectManager("ismealadmin");
        sectorsPage.saveSector();

        // Assert: Validate success toast
        List<String> messages = sectorsPage.getAllToastMessages();
        Assert.assertTrue(messages.stream().anyMatch(m -> m.contains("Add Done")), 
                "Expected 'Add Done' toast not found! Actual: " + messages);
        
        sectorsPage.waitForToastToDisappear();
    }
}
