package com.DoneProject.Pages;

import org.openqa.selenium.By;
import org.openqa.selenium.support.ui.Select;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class SectorsPage extends BasePage {

    private static final Logger logger = LoggerFactory.getLogger(SectorsPage.class);

    private final By addSectorButton     = By.cssSelector("h5.title-list ~ button.btn-primary");
    private final By sectorNameInput     = By.cssSelector("input[type='text'].form-control");
    private final By saveSectorButton    = By.cssSelector("button.btn.btn-primary[type='submit']");
    private final By confirmDeleteButton = By.cssSelector("button.btn.btn-danger");
    private final By managerDropdown     = By.id("bossSelect");

    public SectorsPage() {
        super();
    }

    private By editButtonBySectorName(String sectorName) {
        return By.xpath(
                "//div[contains(@class,'projectCard')][.//text()[normalize-space()='" + sectorName + "']]"
                + "//button[@title='Edit']"
        );
    }

    private By deleteButtonBySectorName(String sectorName) {
        return By.xpath(
                "//div[contains(@class,'projectCard')][.//text()[normalize-space()='" + sectorName + "']]"
                + "//button[@title='Delete']"
        );
    }

    public void addSector(String name) {
        logger.info("Opening add sector modal and entering name: {}", name);
        waitForPageToBeReady();
        click(addSectorButton);
        sendKeys(sectorNameInput, name);
    }

    public void selectManagerByName(String managerName) {
        logger.info("Selecting manager: {}", managerName);
        new Select(driver.findElement(managerDropdown)).selectByVisibleText(managerName);
    }

    public void saveSector() {
        logger.info("Clicking the save button for the sector");
        click(saveSectorButton);
    }

    public void editSector(String oldName, String newName) {
        logger.info("Editing sector: {} -> {}", oldName, newName);
        waitForPageToBeReady();
        click(editButtonBySectorName(oldName));
        sendKeys(sectorNameInput, newName);
        click(saveSectorButton);
    }

    public void deleteSector(String sectorName) {
        logger.info("Deleting sector: {}", sectorName);
        waitForPageToBeReady();
        click(deleteButtonBySectorName(sectorName));
        click(confirmDeleteButton);
    }
}