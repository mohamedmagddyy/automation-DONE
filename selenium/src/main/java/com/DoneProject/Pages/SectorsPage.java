package com.DoneProject.Pages;

import org.openqa.selenium.By;
import org.openqa.selenium.support.ui.Select;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class SectorsPage extends BasePage {

    private static final Logger logger = LoggerFactory.getLogger(SectorsPage.class);

    // ===== Static Locators =====
    private final By addSectorButton    = By.cssSelector("h5.title-list ~ button.btn-primary");
    private final By sectorNameInput    = By.cssSelector("input[type='text'].form-control");
    private final By saveSectorButton   = By.cssSelector("button.btn.btn-primary[type='submit']");
    private final By confirmDeleteButton = By.cssSelector("button.btn.btn-danger");
    private final By managerDropdown    = By.id("bossSelect");

    public SectorsPage() {
        super();
    }

    // ===== Dynamic Locators =====
    private By editButtonBySectorName(String sectorName) {
        return By.xpath("//div[contains(@class,'projectCard')][.//text()[normalize-space()='"
                + sectorName + "']]//button[@title='Edit']");
    }

    private By deleteButtonBySectorName(String sectorName) {
        return By.xpath("//div[contains(@class,'projectCard')][.//text()[normalize-space()='"
                + sectorName + "']]//button[@title='Delete']");
    }

    // ===== Add Sector =====
    public void addSector(String name) {
        waitForPageToBeReady();
        click(addSectorButton);
        sendKeys(sectorNameInput, name);
        waitForToastToDisappear();
        logger.info("✅ تم إضافة السيكتور: {}", name);
    }

    // ===== Edit Sector =====
    public void editSector(String oldName, String newName) {
        waitForPageToBeReady();
        click(editButtonBySectorName(oldName));
        sendKeys(sectorNameInput, newName);
        click(saveSectorButton);
        waitForToastToDisappear();
        logger.info("✅ تم تعديل السيكتور من {} إلى {}", oldName, newName);
    }

    // ===== Delete Sector =====
    public void deleteSector(String sectorName) {
        waitForPageToBeReady();
        click(deleteButtonBySectorName(sectorName));
        click(confirmDeleteButton);
        waitForToastToDisappear();
        logger.info("✅ تم حذف السيكتور: {}", sectorName);
    }

    // ===== Select Manager =====
    public void selectManagerByName(String managerName) {
        Select select = new Select(driver.findElement(managerDropdown));
        select.selectByVisibleText(managerName);
        click(saveSectorButton);
        waitForToastToDisappear();
        logger.info("✅ تم اختيار المدير: {}", managerName);
    }

    // ===== Toast Message =====
    // ✅ تستخدم getToastMessage() من BasePage مباشرة
    public String getToastMessage() {
        return super.getToastMessage();
    }
}