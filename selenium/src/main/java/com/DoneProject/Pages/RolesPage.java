package com.DoneProject.Pages;

import com.DoneProject.utils.WaitUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class RolesPage extends BasePage {

    private static final Logger logger = LoggerFactory.getLogger(RolesPage.class);

    private final By addRoleButton    = By.cssSelector(".btn-add");
    private final By roleNameInput    = By.id("name");
    private final By saveRoleButton   = By.xpath("//div[@id='offcanvasposition']//button[@type='submit'][normalize-space()='Save']");
    private final By confirmDeleteBtn = By.cssSelector("button.btn.btn-danger");
    private final By moveRightButton  = By.xpath("//i[contains(@class,'icon-chevron-right')]");
    private final By savePermissionsButton = By.xpath("//button[contains(@class,'btn-success') and normalize-space()='Save']");

    public RolesPage() {
        super();
    }

    private By editRoleBtnByName(String roleName) {
        return By.xpath("//tr[td[normalize-space()='" + roleName + "']]//button[@title='Edit']");
    }

    private By editPermissionsBtnByName(String roleName) {
        return By.xpath("//tr[td[normalize-space()='" + roleName + "']]//button[@data-bs-target='#rollslist']");
    }

    private By deleteRoleBtnByName(String roleName) {
        return By.xpath("//tr[td[normalize-space()='" + roleName + "']]//button[@data-bs-target='#confirmdelete']");
    }

    private By permissionLabelByName(String permissionName) {
        return By.xpath("//label[contains(@class,'form-check-label') and normalize-space(.)='" + permissionName + "']");
    }

    public RolesPage addRole(String roleName) {
        logger.info("Adding role: {}", roleName);
        waitForPageToBeReady();
        click(addRoleButton);
        sendKeys(roleNameInput, roleName);
        click(saveRoleButton);
        return this;
    }

    public RolesPage editRole(String oldName, String newName) {
        logger.info("Editing role: {} -> {}", oldName, newName);
        waitForPageToBeReady();
        click(editRoleBtnByName(oldName));
        sendKeys(roleNameInput, newName);
        click(saveRoleButton);
        return this;
    }

    public RolesPage openEditPermissionsByName(String roleName) {
        logger.info("Opening permissions for role: {}", roleName);
        waitForPageToBeReady();
        click(editPermissionsBtnByName(roleName));
        return this;
    }

    public RolesPage selectPermissionsAndMove(String... permissionNames) {
        for (String permissionName : permissionNames) {
            try {
                By labelBy = permissionLabelByName(permissionName);
                WaitUtils.waitForElementToBeVisible(driver, labelBy, 10);
                WebElement label = driver.findElement(labelBy);
                String checkboxId = label.getAttribute("for");
                By checkboxBy;
                if (checkboxId != null && !checkboxId.isEmpty()) {
                    checkboxBy = By.id(checkboxId);
                } else {
                    checkboxBy = By.xpath("//label[normalize-space(.)='" + permissionName + "']//following::input[1]");
                }
                actionBot.scrollTo(checkboxBy);
                WebElement checkbox = driver.findElement(checkboxBy);
                if (!checkbox.isSelected()) {
                    actionBot.click(checkboxBy);
                }
                actionBot.click(moveRightButton);
                logger.debug("Permission moved: {}", permissionName);
            } catch (Exception e) {
                logger.warn("Could not select permission '{}': {}", permissionName, e.getMessage());
            }
        }
        return this;
    }

    public RolesPage savePermissions() {
        logger.info("Saving permissions");
        click(savePermissionsButton);
        return this;
    }

    public RolesPage deleteRoleByName(String roleName) {
        logger.info("Deleting role: {}", roleName);
        waitForPageToBeReady();
        click(deleteRoleBtnByName(roleName));
        click(confirmDeleteBtn);
        return this;
    }
}