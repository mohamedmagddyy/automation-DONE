package tests.DoneProject.sectors;

import com.DoneProject.Pages.LoginPage;
import com.DoneProject.Pages.NavBarPage;
import com.DoneProject.Pages.SectorsPage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import tests.DoneProject.BaseTest;

import java.util.List;

/**
 * Test class for SectorsPage functionality.
 * Tests include: Create, Read, Update, Delete (CRUD) operations on sectors.
 * Follows TestNG best practices with proper setup/teardown and assertions.
 *
 * @author QA Team
 * @version 1.0
 */
public class SectorPageTest extends BaseTest {

    private static final Logger logger = LoggerFactory.getLogger(SectorPageTest.class);

    // Test data
    private SectorsPage sectorsPage;
    private static final String USERNAME = "ismealadmin";
    private static final String PASSWORD = "123456";
    private static final String TOAST_ADD_SUCCESS = "Add Done";
    private static final String TOAST_EDIT_SUCCESS = "EditDone";
    private static final String TOAST_DELETE_SUCCESS = "Delete Done";

    // ==================== Setup & Teardown ====================

    /**
     * Prepares the test environment: Login and navigate to Sectors page.
     * This method runs before each test method.
     */
    @BeforeMethod(alwaysRun = true)
    public void setupSectorTest() {
        logger.info("========== Setting up SectorPageTest ==========");
        
        // Login
        LoginPage loginPage = new LoginPage();
        logger.info("Logging in with user: {}", USERNAME);
        loginPage.login(USERNAME, PASSWORD);
        
        // Navigate to Sectors
        NavBarPage navBar = new NavBarPage();
        navBar.goToSectors();
        logger.info("Navigated to Sectors page");
        
        // Initialize SectorsPage
        sectorsPage = new SectorsPage();
        
        // Verify page loaded
        Assert.assertTrue(sectorsPage.isPageLoaded(), 
                "Sectors page failed to load");
        logger.info("========== SectorPageTest Setup Complete ==========");
    }

    // ==================== CREATE Tests ====================

    /**
     * Test: Create a sector with a unique name.
     * Validates that a sector is successfully created and confirmation toast appears.
     */
    @Test(priority = 1, description = "Create a new sector successfully")
    public void testCreateSectorSuccessfully() {
        logger.info("Starting: testCreateSectorSuccessfully");
        
        // Arrange
        String sectorName = "AutoSector_" + System.currentTimeMillis();
        logger.debug("Test data - Sector Name: {}", sectorName);
        
        // Act
        sectorsPage.addSector(sectorName);
        sectorsPage.saveSector();
        logger.debug("Sector created and saved");
        
        // Assert - Check Toast Message
        List<String> messages = sectorsPage.getAllToastMessages();
        Assert.assertTrue(
                messages.stream().anyMatch(m -> m.contains(TOAST_ADD_SUCCESS)),
                "Expected '" + TOAST_ADD_SUCCESS + "' toast not found! Actual: " + messages
        );
        logger.info("Toast message verified: {}", TOAST_ADD_SUCCESS);
        
        // Cleanup
        sectorsPage.waitForToastToDisappear();
        logger.info("✓ testCreateSectorSuccessfully PASSED");
    }

    /**
     * Test: Create multiple sectors in sequence.
     * Validates that multiple sectors can be created without issues.
     */
    @Test(priority = 2, description = "Create multiple sectors sequentially")
    public void testCreateMultipleSectors() {
        logger.info("Starting: testCreateMultipleSectors");
        
        // Arrange
        String[] sectorNames = {
                "Sector_Alpha_" + System.currentTimeMillis(),
                "Sector_Beta_" + System.currentTimeMillis(),
                "Sector_Gamma_" + System.currentTimeMillis()
        };
        logger.debug("Creating {} sectors", sectorNames.length);
        
        // Act & Assert
        for (String sectorName : sectorNames) {
            logger.debug("Creating sector: {}", sectorName);
            sectorsPage.addSector(sectorName);
            sectorsPage.saveSector();
            
            List<String> messages = sectorsPage.getAllToastMessages();
            Assert.assertTrue(
                    messages.stream().anyMatch(m -> m.contains(TOAST_ADD_SUCCESS)),
                    "Failed to create sector: " + sectorName
            );
            sectorsPage.waitForToastToDisappear();
            logger.debug("Sector created successfully: {}", sectorName);
        }
        
        logger.info("✓ testCreateMultipleSectors PASSED");
    }

    /**
     * Test: Create a sector with special characters in name.
     * Validates that sector names with special characters are handled properly.
     */
    @Test(priority = 3, description = "Create sector with special characters")
    public void testCreateSectorWithSpecialCharacters() {
        logger.info("Starting: testCreateSectorWithSpecialCharacters");
        
        // Arrange
        String sectorName = "Sector-Test_#" + System.currentTimeMillis();
        logger.debug("Test data - Sector Name with special chars: {}", sectorName);
        
        // Act
        sectorsPage.addSector(sectorName);
        sectorsPage.saveSector();
        logger.debug("Sector with special characters saved");
        
        // Assert
        List<String> messages = sectorsPage.getAllToastMessages();
        Assert.assertTrue(
                messages.stream().anyMatch(m -> m.contains(TOAST_ADD_SUCCESS)),
                "Failed to create sector with special characters"
        );
        sectorsPage.waitForToastToDisappear();
        
        logger.info("✓ testCreateSectorWithSpecialCharacters PASSED");
    }

    // ==================== READ Tests ====================

    /**
     * Test: Verify a sector is displayed on the page after creation.
     * Validates the isDisplayed() method functionality.
     */
    @Test(priority = 4, description = "Verify sector is displayed after creation")
    public void testSectorDisplayedAfterCreation() {
        logger.info("Starting: testSectorDisplayedAfterCreation");
        
        // Arrange
        String sectorName = "DisplaySector_" + System.currentTimeMillis();
        
        // Act
        sectorsPage.addSector(sectorName);
        sectorsPage.saveSector();
        sectorsPage.waitForToastToDisappear();
        logger.debug("Sector created and toasts cleared");
        
        // Assert
        boolean isDisplayed = sectorsPage.isSectorDisplayed(sectorName);
        Assert.assertTrue(isDisplayed, "Sector should be displayed after creation");
        logger.info("✓ Sector is displayed: {}", sectorName);
        
        logger.info("✓ testSectorDisplayedAfterCreation PASSED");
    }

    // ==================== UPDATE Tests ====================

    /**
     * Test: Edit an existing sector name.
     * Validates that a sector can be edited and the change is confirmed with a toast.
     */
    @Test(priority = 5, description = "Edit sector name successfully")
    public void testEditSectorSuccessfully() {
        logger.info("Starting: testEditSectorSuccessfully");
        
        // Arrange - Create a sector to edit
        long timestamp = System.currentTimeMillis();
        String oldName = "EditTest_" + timestamp;
        String newName = "UpdatedEditTest_" + timestamp;
        
        logger.debug("Creating sector to edit: {}", oldName);
        sectorsPage.addSector(oldName);
        sectorsPage.saveSector();
        sectorsPage.waitForToastToDisappear();
        
        // Act - Edit the sector
        logger.debug("Editing sector: {} -> {}", oldName, newName);
        sectorsPage.editSector(oldName, newName);
        
        // Assert
        List<String> messages = sectorsPage.getAllToastMessages();
        Assert.assertTrue(
                messages.stream().anyMatch(m -> m.contains(TOAST_EDIT_SUCCESS)),
                "Expected '" + TOAST_EDIT_SUCCESS + "' toast not found! Actual: " + messages
        );
        logger.info("Toast message verified: {}", TOAST_EDIT_SUCCESS);
        
        // Verify new name is displayed
        sectorsPage.waitForToastToDisappear();
        boolean isNewNameDisplayed = sectorsPage.isSectorDisplayed(newName);
        Assert.assertTrue(isNewNameDisplayed, "Edited sector name should be displayed");
        logger.info("✓ Edited sector is displayed with new name: {}", newName);
        
        logger.info("✓ testEditSectorSuccessfully PASSED");
    }

    /**
     * Test: Edit sector with special characters.
     * Validates that editing a sector with special characters works correctly.
     */
    @Test(priority = 6, description = "Edit sector with special characters")
    public void testEditSectorWithSpecialCharacters() {
        logger.info("Starting: testEditSectorWithSpecialCharacters");
        
        // Arrange
        long timestamp = System.currentTimeMillis();
        String originalName = "SpecialEdit_" + timestamp;
        String editedName = "Updated-Special_#" + timestamp;
        
        sectorsPage.addSector(originalName);
        sectorsPage.saveSector();
        sectorsPage.waitForToastToDisappear();
        
        // Act
        sectorsPage.editSector(originalName, editedName);
        
        // Assert
        List<String> messages = sectorsPage.getAllToastMessages();
        Assert.assertTrue(
                messages.stream().anyMatch(m -> m.contains(TOAST_EDIT_SUCCESS)),
                "Failed to edit sector with special characters"
        );
        sectorsPage.waitForToastToDisappear();
        
        logger.info("✓ testEditSectorWithSpecialCharacters PASSED");
    }

    // ==================== DELETE Tests ====================

    /**
     * Test: Delete a sector successfully.
     * Validates that a sector can be deleted and confirmation toast appears.
     */
    @Test(priority = 7, description = "Delete sector successfully")
    public void testDeleteSectorSuccessfully() {
        logger.info("Starting: testDeleteSectorSuccessfully");
        
        // Arrange - Create a sector to delete
        String sectorName = "DeleteTest_" + System.currentTimeMillis();
        logger.debug("Creating sector to delete: {}", sectorName);
        sectorsPage.addSector(sectorName);
        sectorsPage.saveSector();
        sectorsPage.waitForToastToDisappear();
        
        // Verify sector exists
        Assert.assertTrue(sectorsPage.isSectorDisplayed(sectorName), 
                "Sector should exist before deletion");
        
        // Act - Delete the sector
        logger.debug("Deleting sector: {}", sectorName);
        sectorsPage.deleteSector(sectorName);
        
        // Assert
        List<String> messages = sectorsPage.getAllToastMessages();
        Assert.assertTrue(
                messages.stream().anyMatch(m -> m.contains(TOAST_DELETE_SUCCESS)),
                "Expected '" + TOAST_DELETE_SUCCESS + "' toast not found! Actual: " + messages
        );
        logger.info("Toast message verified: {}", TOAST_DELETE_SUCCESS);
        
        logger.info("✓ testDeleteSectorSuccessfully PASSED");
    }

    /**
     * Test: Delete multiple sectors.
     * Validates that multiple sectors can be deleted sequentially.
     */
    @Test(priority = 8, description = "Delete multiple sectors")
    public void testDeleteMultipleSectors() {
        logger.info("Starting: testDeleteMultipleSectors");
        
        // Arrange
        long timestamp = System.currentTimeMillis();
        String[] sectorNames = {
                "DeleteMulti_1_" + timestamp,
                "DeleteMulti_2_" + timestamp,
                "DeleteMulti_3_" + timestamp
        };
        
        logger.debug("Creating {} sectors for deletion", sectorNames.length);
        for (String sectorName : sectorNames) {
            sectorsPage.addSector(sectorName);
            sectorsPage.saveSector();
            sectorsPage.waitForToastToDisappear();
        }
        
        // Act & Assert
        for (String sectorName : sectorNames) {
            logger.debug("Deleting sector: {}", sectorName);
            sectorsPage.deleteSector(sectorName);
            
            List<String> messages = sectorsPage.getAllToastMessages();
            Assert.assertTrue(
                    messages.stream().anyMatch(m -> m.contains(TOAST_DELETE_SUCCESS)),
                    "Failed to delete sector: " + sectorName
            );
            logger.debug("Sector deleted successfully: {}", sectorName);
        }
        
        logger.info("✓ testDeleteMultipleSectors PASSED");
    }

    // ==================== Integration Tests ====================

    /**
     * Test: Complete sector lifecycle - Create, Edit, Delete.
     * Validates the full lifecycle of a sector management operation.
     */
    @Test(priority = 9, description = "Complete sector lifecycle: Create -> Edit -> Delete")
    public void testCompleteSectorLifecycle() {
        logger.info("Starting: testCompleteSectorLifecycle");
        
        // Arrange
        long timestamp = System.currentTimeMillis();
        String originalName = "Lifecycle_" + timestamp;
        String editedName = "LifecycleEdited_" + timestamp;
        
        // Act & Assert - Create
        logger.info("Step 1: Creating sector");
        sectorsPage.createSector(originalName);
        sectorsPage.waitForToastToDisappear();
        Assert.assertTrue(sectorsPage.isSectorDisplayed(originalName), 
                "Created sector should be displayed");
        logger.info("✓ Sector created: {}", originalName);
        
        // Act & Assert - Edit
        logger.info("Step 2: Editing sector");
        sectorsPage.editSector(originalName, editedName);
        sectorsPage.waitForToastToDisappear();
        Assert.assertTrue(sectorsPage.isSectorDisplayed(editedName), 
                "Edited sector should be displayed with new name");
        logger.info("✓ Sector edited: {} -> {}", originalName, editedName);
        
        // Act & Assert - Delete
        logger.info("Step 3: Deleting sector");
        sectorsPage.deleteSector(editedName);
        logger.info("✓ Sector deleted: {}", editedName);
        
        logger.info("✓ testCompleteSectorLifecycle PASSED");
    }

    /**
     * Test: Verify page state validation methods.
     * Validates that page state checking methods work correctly.
     */
    @Test(priority = 10, description = "Verify page state validation methods")
    public void testPageStateValidation() {
        logger.info("Starting: testPageStateValidation");
        
        // Assert page is loaded
        Assert.assertTrue(sectorsPage.isPageLoaded(), 
                "Sectors page should be loaded after navigation");
        logger.info("✓ Page is loaded");
        
        // Create a sector and verify it's displayed
        String sectorName = "PageState_" + System.currentTimeMillis();
        sectorsPage.createSector(sectorName);
        sectorsPage.waitForToastToDisappear();
        
        Assert.assertTrue(sectorsPage.isSectorDisplayed(sectorName), 
                "Created sector should be visible on page");
        logger.info("✓ Created sector is displayed");
        
        logger.info("✓ testPageStateValidation PASSED");
    }

    // ==================== Error Handling Tests ====================

    /**
     * Test: Verify fluent API method chaining works correctly.
     * Validates that methods return SectorsPage for proper chaining.
     */
    @Test(priority = 11, description = "Verify fluent API method chaining")
    public void testFluentAPIChaining() {
        logger.info("Starting: testFluentAPIChaining");
        
        // Arrange
        String sectorName = "FluentAPI_" + System.currentTimeMillis();
        
        // Act - Using method chaining (fluent API)
        SectorsPage result = sectorsPage
                .addSector(sectorName)
                .saveSector();
        
        // Assert
        Assert.assertNotNull(result, "Method chaining should return SectorsPage instance");
        Assert.assertTrue(result instanceof SectorsPage, 
                "Returned object should be SectorsPage");
        Assert.assertTrue(sectorsPage.isSectorDisplayed(sectorName), 
                "Sector should be created through fluent API");
        
        logger.info("✓ testFluentAPIChaining PASSED");
    }

    /**
     * Test: Verify toast message collection functionality.
     * Validates that toast messages are properly captured and cleared.
     */
    @Test(priority = 12, description = "Verify toast message handling")
    public void testToastMessageHandling() {
        logger.info("Starting: testToastMessageHandling");
        
        // Arrange
        String sectorName = "ToastTest_" + System.currentTimeMillis();
        
        // Act
        sectorsPage.createSector(sectorName);
        List<String> toastMessages = sectorsPage.readAllToastsAndWaitToDisappear();
        
        // Assert
        Assert.assertNotNull(toastMessages, "Toast messages list should not be null");
        Assert.assertFalse(toastMessages.isEmpty(), "Should have at least one toast message");
        Assert.assertTrue(toastMessages.stream().anyMatch(m -> m.contains(TOAST_ADD_SUCCESS)), 
                "Toast should contain success message");
        
        logger.info("Toast messages captured: {}", toastMessages);
        logger.info("✓ testToastMessageHandling PASSED");
    }
}



