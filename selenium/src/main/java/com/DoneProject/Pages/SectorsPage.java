package com.DoneProject.Pages;

import org.openqa.selenium.By;
import org.openqa.selenium.support.ui.Select;

public class SectorsPage extends BasePage {

    // ================= STATIC LOCATORS =================
    private final By addSectorButton = By.cssSelector("h5.title-list ~ button.btn-primary");
    private final By sectorNameInput = By.cssSelector("input[type='text'].form-control");
    private final By saveSectorButton = By.cssSelector("button.btn.btn-primary[type='submit']");
    private final By confirmDeleteButton = By.cssSelector("button.btn.btn-danger");
    private final By managerSelectDropdown = By.id("bossSelect");

    // ================= CONSTRUCTOR =================
    public SectorsPage() {
        super();
    }

    // ================= DYNAMIC LOCATORS =================
    private By sectorCardByName(String sectorName) {
        return By.xpath("//div[contains(@class,'projectCard')][.//text()[normalize-space()='" + sectorName + "']]");
    }

    private By editButtonBySectorName(String sectorName) {
        return By.xpath("//div[contains(@class,'projectCard')][.//text()[normalize-space()='" + sectorName + "']]//button[@title='Edit']");
    }

    private By deleteButtonBySectorName(String sectorName) {
        return By.xpath("//div[contains(@class,'projectCard')][.//text()[normalize-space()='" + sectorName + "']]//button[@title='Delete']");
    }

    // ================= ADD SECTOR =================
    public void addSector(String name) {
        waitForPageToBeReady();
        click(addSectorButton);
        sendKeys(sectorNameInput, name);
        waitForToastToDisappear();
    }

    // ================= EDIT SECTOR =================
    public void editSector(String oldName, String newName) {
        waitForPageToBeReady();
        By editBtn = editButtonBySectorName(oldName);
        click(editBtn);
        sendKeys(sectorNameInput, newName);
        click(saveSectorButton);
        waitForToastToDisappear();
    }

    // ================= DELETE SECTOR =================
    public void deleteSector(String sectorName) {
        waitForPageToBeReady();
        By deleteBtn = deleteButtonBySectorName(sectorName);
        click(deleteBtn);
        click(confirmDeleteButton);
        waitForToastToDisappear();
    }

    // ================= SELECT MANAGER =================
    public void selectManagerByName(String managerName) {
        Select select = new Select(driver.findElement(managerSelectDropdown));
        select.selectByVisibleText(managerName);
        click(saveSectorButton);
        waitForToastToDisappear();
    }

    // ================= ASSERTIONS =================
    public String getToastMessage() {
        return getText(toastSuccess).trim();
    }
}
