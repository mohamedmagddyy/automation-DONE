package com.DoneProject.Pages;

import com.DoneProject.utils.WaitUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class RolesPage extends BasePage {

    private static final Logger logger = LoggerFactory.getLogger(RolesPage.class);

    // ===== Static Locators =====
    private final By addRoleBtn   = By.cssSelector(".btn-add");
    private final By roleNameInput = By.id("name");
    private final By saveRoleBtn  = By.xpath("//div[@id='offcanvasposition']//button[@type='submit'][normalize-space()='Save']");
    private final By confirmDeleteBtn = By.cssSelector("button.btn.btn-danger");
    private final By moveRightButton  = By.xpath("//i[contains(@class,'icon-chevron-right')]");
    private final By saveButton       = By.xpath("//button[contains(@class,'btn-success') and normalize-space()='Save']");

    public RolesPage() {
        super();
    }

    // ===== Dynamic Locators =====
    private By editRoleBtnByName(String roleName) {
        return By.xpath("//tr[td[normalize-space()='" + roleName + "']]//button[@data-bs-target='#rollslist']");
    }

    private By deleteRoleBtnByName(String roleName) {
        return By.xpath("//tr[td[normalize-space()='" + roleName + "']]//button[@data-bs-target='#confirmdelete']");
    }

    private By permissionLabelByName(String permissionName) {
        return By.xpath("//label[contains(@class,'form-check-label') and normalize-space(.)='" + permissionName + "']");
    }

    // ===== Business Actions (Fluent) =====
    public RolesPage addRole(String roleName) {
        logger.info("➕ Adding Role: {}", roleName);
        waitForPageToBeReady();
        click(addRoleBtn);
        sendKeys(roleNameInput, roleName);
        click(saveRoleBtn);
        waitForToastToDisappear();
        return this;
    }

    public RolesPage editRole(String oldName, String newName) {
        logger.info("✏️ Editing Role: {} -> {}", oldName, newName);
        waitForPageToBeReady();
        By editBtn = By.xpath("//tr[td[normalize-space()='" + oldName + "']]//button[@title='Edit']");
        click(editBtn);
        sendKeys(roleNameInput, newName);
        click(saveRoleBtn);
        waitForToastToDisappear();
        return this;
    }

    public RolesPage openEditRoleByName(String roleName) {
        logger.info("🛡️ Modifying Permissions for Role: {}", roleName);
        waitForPageToBeReady();
        click(editRoleBtnByName(roleName));
        return this;
    }

    public RolesPage selectPermissionsAndMove(String... permissionNames) {
        for (String permissionName : permissionNames) {
            try {
                By labelBy = permissionLabelByName(permissionName);
                WaitUtils.waitForElementToBeVisible(driver, labelBy, 10);
                WebElement label = driver.findElement(labelBy);

                String checkboxId = label.getAttribute("for");
                if (checkboxId != null && !checkboxId.isEmpty()) {
                    By checkboxBy = By.id(checkboxId);
                    actionBot.scrollTo(checkboxBy);
                    WebElement checkbox = driver.findElement(checkboxBy);
                    if (!checkbox.isSelected()) {
                        actionBot.click(checkboxBy);
                    }
                } else {
                    By fallback = By.xpath("//label[normalize-space(.)='" + permissionName + "']//following::input[1]");
                    actionBot.scrollTo(fallback);
                    WebElement checkbox = driver.findElement(fallback);
                    if (!checkbox.isSelected()) {
                        actionBot.click(fallback);
                    }
                }

                actionBot.click(moveRightButton);
                logger.debug("✅ Permission moved: {}", permissionName);

            } catch (Exception e) {
                logger.warn("⚠️ Could not select permission '{}': {}", permissionName, e.getMessage());
            }
        }
        return this;
    }

    public RolesPage clickSave() {
        logger.info("💾 Saving Permissions");
        click(saveButton);
        return this;
    }

    public RolesPage deleteRoleByName(String roleName) {
        logger.info("🗑️ Deleting Role: {}", roleName);
        waitForPageToBeReady();
        click(deleteRoleBtnByName(roleName));
        click(confirmDeleteBtn);
        waitForToastToDisappear();
        return this;
    }
}