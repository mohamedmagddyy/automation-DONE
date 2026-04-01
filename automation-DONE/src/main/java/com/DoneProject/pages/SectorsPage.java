package com.DoneProject.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.Duration;

/**
 * Page Object Model for Sectors Management.
 * Provides methods for creating, reading, updating, and deleting sectors.
 * Follows POM best practices with clean separation of locators and actions.
 */
public class SectorsPage extends BasePage {

    private static final Logger logger        = LoggerFactory.getLogger(SectorsPage.class);
    private static final int    WAIT_TIMEOUT  = 10;

    // ==================== Locators (Private & Final) ====================
    
    private final By addSectorButton         = By.cssSelector("h5.title-list ~ button.btn-primary");
    private final By sectorNameInput         = By.cssSelector("input[type='text'].form-control");
    private final By saveSectorButton        = By.cssSelector("button.btn.btn-primary[type='submit']");
    private final By confirmDeleteButton     = By.cssSelector("button.btn.btn-danger");
    private final By managerDropdown         = By.id("bossSelect");
    private final By sectorCardContainer     = By.cssSelector("div.projectCard");

    // ==================== Constructor ====================

    /**
     * Default constructor. Driver is managed by BasePage.
     */
    public SectorsPage() {
        super();
        logger.info("SectorsPage initialized");
    }

    /**
     * @deprecated Use default constructor instead. Driver is handled by BasePage.
     */
    @Deprecated
    public SectorsPage(org.openqa.selenium.WebDriver driver) {
        this();
    }

    // ==================== Dynamic Locators ====================

    /**
     * Generates locator for edit button of a specific sector by name.
     * Uses stable XPath with normalize-space() to handle whitespace.
     *
     * @param sectorName The name of the sector
     * @return By locator for the edit button
     */
    private By getEditButtonBySectorName(String sectorName) {
        return By.xpath(
                "//div[contains(@class,'projectCard')][.//text()[normalize-space()='" + sectorName + "']]"
                + "//button[@title='Edit']"
        );
    }

    /**
     * Generates locator for delete button of a specific sector by name.
     *
     * @param sectorName The name of the sector
     * @return By locator for the delete button
     */
    private By getDeleteButtonBySectorName(String sectorName) {
        return By.xpath(
                "//div[contains(@class,'projectCard')][.//text()[normalize-space()='" + sectorName + "']]"
                + "//button[@title='Delete']"
        );
    }

    /**
     * Generates locator for a sector card by name.
     *
     * @param sectorName The name of the sector
     * @return By locator for the sector card
     */
    private By getSectorCardByName(String sectorName) {
        return By.xpath(
                "//div[contains(@class,'projectCard')][.//text()[normalize-space()='" + sectorName + "']]"
        );
    }

    // ==================== Page State Validation ====================

    /**
     * Checks if the Sectors page is fully loaded.
     * Validates by checking for the presence of the "Add Sector" button.
     *
     * @return true if page is loaded, false otherwise
     */
    public boolean isPageLoaded() {
        try {
            logger.debug("Checking if Sectors page is loaded");
            WebElement element = waitForElement(addSectorButton);
            boolean isLoaded = element.isDisplayed();
            logger.info("Sectors page load status: {}", isLoaded);
            return isLoaded;
        } catch (Exception e) {
            logger.warn("Failed to verify Sectors page load: {}", e.getMessage());
            return false;
        }
    }

    /**
     * Checks if a specific sector is displayed on the page.
     *
     * @param sectorName The name of the sector to check
     * @return true if sector is visible, false otherwise
     */
    public boolean isSectorDisplayed(String sectorName) {
        try {
            logger.debug("Checking if sector is displayed: {}", sectorName);
            By sectorLocator = getSectorCardByName(sectorName);
            WebElement element = new WebDriverWait(driver, Duration.ofSeconds(WAIT_TIMEOUT))
                    .until(ExpectedConditions.visibilityOfElementLocated(sectorLocator));
            logger.info("Sector found on page: {}", sectorName);
            return true;
        } catch (Exception e) {
            logger.warn("Sector not found: {} - {}", sectorName, e.getMessage());
            return false;
        }
    }

    // ==================== Business Actions ====================

    /**
     * Opens the Add Sector modal and enters the sector name.
     * Note: This does NOT save. Call saveSector() to persist changes.
     *
     * @param sectorName The name of the new sector
     * @return SectorsPage for method chaining (fluent API)
     */
    public SectorsPage addSector(String sectorName) {
        logger.info("Starting Add Sector flow with name: {}", sectorName);
        waitForPageToBeReady();
        click(addSectorButton);
        logger.debug("Add Sector button clicked");
        sendKeys(sectorNameInput, sectorName);
        logger.debug("Sector name '{}' entered in input field", sectorName);
        return this;
    }

    /**
     * Creates a sector with just a name. Opens modal, enters name, and saves.
     * This is a complete high-level action combining add and save.
     *
     * @param sectorName The name of the sector to create
     * @return SectorsPage for method chaining
     */
    public SectorsPage createSector(String sectorName) {
        logger.info("Creating sector: {}", sectorName);
        addSector(sectorName);
        saveSector();
        waitForPageToBeReady();
        logger.info("Sector created successfully: {}", sectorName);
        return this;
    }

    /**
     * Selects a manager from the manager dropdown menu.
     * Should be called after opening the Add/Edit modal.
     *
     * @param managerName The name of the manager to select
     * @return SectorsPage for method chaining
     */
    public SectorsPage selectManager(String managerName) {
        logger.info("Selecting manager: {}", managerName);
        selectFromDropdown(managerDropdown, managerName);
        logger.debug("Manager '{}' selected from dropdown", managerName);
        return this;
    }

    /**
     * Saves the current sector (whether adding new or editing existing).
     * Waits for page to be ready before and after saving.
     *
     * @return SectorsPage for method chaining
     */
    public SectorsPage saveSector() {
        logger.info("Saving sector");
        waitForPageToBeReady();
        click(saveSectorButton);
        logger.debug("Save Sector button clicked");
        waitForPageToBeReady();
        logger.info("Sector saved successfully");
        return this;
    }

    /**
     * Selects a sector by clicking on it.
     * Useful for navigating into a specific sector.
     *
     * @param sectorName The name of the sector to select
     * @return SectorsPage for method chaining
     */
    public SectorsPage selectSector(String sectorName) {
        logger.info("Selecting sector: {}", sectorName);
        waitForPageToBeReady();
        click(getSectorCardByName(sectorName));
        logger.debug("Sector '{}' selected/clicked", sectorName);
        waitForPageToBeReady();
        return this;
    }

    /**
     * Edits an existing sector by changing its name.
     * High-level action: clicks edit, updates name, and saves.
     *
     * @param oldName The current name of the sector
     * @param newName The new name for the sector
     * @return SectorsPage for method chaining
     */
    public SectorsPage editSector(String oldName, String newName) {
        logger.info("Editing sector: '{}' -> '{}'", oldName, newName);
        waitForPageToBeReady();
        
        // Click edit button
        click(getEditButtonBySectorName(oldName));
        logger.debug("Edit button clicked for sector: {}", oldName);
        
        // Wait for modal and update name
        waitForPageToBeReady();
        sendKeys(sectorNameInput, newName);
        logger.debug("Sector name updated to: {}", newName);
        
        // Save changes
        saveSector();
        logger.info("Sector edit completed: '{}' -> '{}'", oldName, newName);
        
        return this;
    }

    /**
     * Deletes a sector with confirmation.
     * High-level action: clicks delete, confirms, and waits for completion.
     *
     * @param sectorName The name of the sector to delete
     * @return SectorsPage for method chaining
     */
    public SectorsPage deleteSector(String sectorName) {
        logger.info("Deleting sector: {}", sectorName);
        waitForPageToBeReady();
        
        // Click delete button
        click(getDeleteButtonBySectorName(sectorName));
        logger.debug("Delete button clicked for sector: {}", sectorName);
        
        // Confirm deletion in the confirmation dialog
        waitForPageToBeReady();
        click(confirmDeleteButton);
        logger.debug("Delete confirmation submitted for sector: {}", sectorName);
        
        // Wait for operation to complete
        waitForPageToBeReady();
        waitForToastToDisappear();
        logger.info("Sector deleted successfully: {}", sectorName);
        
        return this;
    }

    /**
     * Performs a complete sector lifecycle: create, edit, and delete.
     * Useful for comprehensive testing.
     *
     * @param sectorName The name of the sector
     * @param editedName The new name after editing
     * @return SectorsPage for method chaining
     */
    public SectorsPage testFullSectorLifecycle(String sectorName, String editedName) {
        logger.info("Starting full sector lifecycle test");
        createSector(sectorName);
        logger.debug("Sector created: {}", sectorName);
        
        editSector(sectorName, editedName);
        logger.debug("Sector edited: {} -> {}", sectorName, editedName);
        
        deleteSector(editedName);
        logger.debug("Sector deleted: {}", editedName);
        
        logger.info("Full sector lifecycle test completed");
        return this;
    }
}
