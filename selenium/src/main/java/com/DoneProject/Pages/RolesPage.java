package com.DoneProject.Pages;

import com.DoneProject.utils.WaitUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class RolesPage extends BasePage {

    private final By addRoleBtn = By.cssSelector(".btn-add");
    private final By roleNameInput = By.id("name");
    private final By saveRoleBtn = By.xpath("//div[@id='offcanvasposition']//button[@type='submit'][normalize-space()='Save']");
    private final By deleteRoleBtnTemplate = By.xpath("//tr[td[normalize-space()='%s']]//button[@data-bs-target='#confirmdelete']");
    private final By confirmDeleteBtn = By.cssSelector("button.btn.btn-danger");
    private final By moveRightButton = By.xpath("//i[contains(@class,'icon-chevron-right')]");
    private final By saveButton = By.xpath("//button[contains(@class,'btn-success') and normalize-space()='Save']");

    private static final Logger logger = LoggerFactory.getLogger(RolesPage.class);

    public RolesPage() {
        super();
    }

    private By permissionLabelByName(String permissionName) {
        // use normalize-space(.) to account for inner text whitespace
        return By.xpath("//label[contains(@class,'form-check-label') and normalize-space(.)='" + permissionName + "']");
    }

    private By editRoleBtnByName(String roleName) {
        return By.xpath(
                "//tr[td[normalize-space()='" + roleName + "']]//button[@data-bs-target='#rollslist']"
        );
    }

    public void addRole(String roleName) {
        waitForPageToBeReady();
        click(addRoleBtn);
        sendKeys(roleNameInput, roleName);
        click(saveRoleBtn);
        waitForToastToDisappear();
    }

    public void editRole(String oldName, String newName) {
        waitForPageToBeReady();
        By editBtn = By.xpath("//tr[td[normalize-space()='" + oldName + "']]//button[@title='Edit']");
        click(editBtn);
        sendKeys(roleNameInput, newName);
        click(saveRoleBtn);
        waitForToastToDisappear();
    }

    public void selectPermissionsAndMove(String... permissionNames) {

        for (String permissionName : permissionNames) {
            try {
                // wait for label to be visible
                By labelBy = permissionLabelByName(permissionName);
                WaitUtils.waitForElementToBeVisible(driver, labelBy, 20);
                WebElement label = driver.findElement(labelBy);

                // try to get checkbox by 'for' attribute
                String checkboxId = label.getAttribute("for");
                if (checkboxId != null && !checkboxId.isEmpty()) {
                    By checkboxBy = By.id(checkboxId);
                    // ensure checkbox is clickable/visible before interacting
                    WaitUtils.waitForElementToBeClickable(driver, checkboxBy, 20);
                    // scroll to it and click if not selected
                    actionBot.scrollTo(checkboxBy);
                    WebElement checkbox = driver.findElement(checkboxBy);
                    if (!checkbox.isSelected()) {
                        actionBot.click(checkboxBy);
                    }
                } else {
                    // fallback: try to locate the checkbox near the label
                    By fallbackCheckbox = By.xpath("//label[normalize-space(.)='" + permissionName + "']//following::input[1]");
                    WaitUtils.waitForElementToBeClickable(driver, fallbackCheckbox, 20);
                    actionBot.scrollTo(fallbackCheckbox);
                    WebElement checkbox = driver.findElement(fallbackCheckbox);
                    if (!checkbox.isSelected()) {
                        actionBot.click(fallbackCheckbox);
                    }
                }

                // move to the right (use ActionBot click to handle waits)
                actionBot.click(moveRightButton);

            } catch (Exception e) {
                // log but continue with other permissions
                logger.warn("Could not select permission '{}': {}", permissionName, e.getMessage());
            }
        }
    }

    public void clickSave() {
        click(saveButton);
    }

    public void openEditRoleByName(String roleName) {
        waitForPageToBeReady();
        By editBtn = editRoleBtnByName(roleName);
        click(editBtn);
    }


    public void deleteRoleByName(String roleName) {
        waitForPageToBeReady();
        By deleteBtn = By.xpath(String.format("//tr[td[normalize-space()='%s']]//button[@data-bs-target='#confirmdelete']", roleName));
        click(deleteBtn);
        click(confirmDeleteBtn);
        waitForToastToDisappear();
    }
}
