package com.DoneProject.pages;

import org.openqa.selenium.By;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class FileManagerPage extends BasePage {

    private static final Logger logger = LoggerFactory.getLogger(FileManagerPage.class);

    private final By addFileButton = By.xpath("//button[contains(@class, 'addfileAssets') or normalize-space(.)='Add File']");
    private final By addFolderButton = By.xpath("//button[contains(@class, 'addfolderAssets') or normalize-space(.)='Add Folder']");
    private final By projectDropdown = By.xpath("//p-autocomplete[@placeholder='Select the project']//input");
    private final By saveButton = By.xpath("//button[normalize-space(.)='Save']");
    private final By confirmDeleteBtn = By.xpath("//button[contains(@class, 'btn-danger') and (normalize-space(.)='Delete' or normalize-space(.)='Yes')]");
    private final By fileUploadInput = By.xpath("//input[@type='file']");
    private final By folderNameInput = By.xpath("//input[@formcontrolname='folderName' or @placeholder='Folder Name']");
    private static final By DELETE_OPTION = By.xpath("//li[normalize-space(.)='Delete'] | //button[normalize-space(.)='Delete']");

    public FileManagerPage() {
        super();
    }

    public void addFile(String fileName, String filePath, String projectName) {
        logger.info("Adding file: {} to project: {}", fileName, projectName);
        waitForPageToBeReady();
        click(addFileButton);
        sendKeys(fileUploadInput, filePath + fileName);
        selectProjectByName(projectName);
        click(saveButton);
        waitForToastToDisappear();
    }

    public void addFolder(String folderName, String projectName) {
        logger.info("Adding folder: {} to project: {}", folderName, projectName);
        waitForPageToBeReady();
        click(addFolderButton);
        sendKeys(folderNameInput, folderName);
        selectProjectByName(projectName);
        click(saveButton);
        waitForToastToDisappear();
    }

    public void deleteFile(String fileName) {
        logger.info("Deleting file: {}", fileName);
        waitForPageToBeReady();
        clickActionsMenuByName(fileName);
        click(DELETE_OPTION);
        click(confirmDeleteBtn);
        waitForToastToDisappear();
    }

    public void deleteFolder(String folderName) {
        logger.info("Deleting folder: {}", folderName);
        waitForPageToBeReady();
        clickActionsMenuByName(folderName);
        click(DELETE_OPTION);
        click(confirmDeleteBtn);
        waitForToastToDisappear();
    }

    private void selectProjectByName(String projectName) {
        sendKeys(projectDropdown, projectName);
        By option = By.xpath(
                "//li[contains(@class, 'p-autocomplete-item')]//span[normalize-space()='" + projectName + "']");
        click(option);
    }

    private void clickActionsMenuByName(String name) {
        By actionMenu = By.xpath(
                "//tr[.//td[normalize-space()='" + name + "'] or .//a[normalize-space()='" + name + "']]"
                        + "//button[contains(@class,'menu')]");
        click(actionMenu);
    }
}
