package com.DoneProject.Pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.Select;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class UsersPage extends BasePage {

    private static final Logger logger = LoggerFactory.getLogger(UsersPage.class);

    // ===== Static Locators =====
    private final By addUserButton   = By.id("dropdownMenuButton1");
    private final By newUserOption   = By.xpath("//a[normalize-space()='Add User']");

    private final By userNameInput        = By.id("userName");
    private final By userFullnameInput    = By.id("fullName");
    private final By userEmailInput       = By.id("email");
    private final By userPhoneInput       = By.id("phone");
    private final By userDateOfBirthInput = By.id("contractDate");
    private final By userRoleDropdown     = By.xpath("//mat-select[@formcontrolname='roleIds']");
    private final By userTypeDropdown     = By.id("userType");
    private final By userHourlyCost       = By.id("monthlySalary");
    private final By userEmployeeCode     = By.id("employeeCode");
    private final By userPasswordInput    = By.id("password");
    private final By userConfirmPassword  = By.id("confirmPassword");
    private final By saveUserButton       = By.cssSelector("button.btn-submitdone");

    private final By firstUserActionBtn   = By.xpath("//table//tbody//tr[1]//button");
    private final By editUserOption       = By.xpath("//ul[@class='dropdown-menu show']//span[contains(text(),'Edit')]");
    private final By deleteUserOption     = By.xpath("//ul[@class='dropdown-menu show']//span[contains(text(),'Delete employee')]");
    private final By confirmDeleteUserBtn = By.cssSelector("button.btn.btn-danger");

    public UsersPage() {
        super();
    }

    // ===== Dynamic Locators =====
    private By userActionBtnByName(String username) {
        return By.xpath("//tr[td[normalize-space()='" + username + "']]//button");
    }

    // ===== Business Actions (Fluent) =====
    public UsersPage clickAddUser() {
        logger.info("➕ Opening Add User form");
        click(addUserButton);
        click(newUserOption);
        return this;
    }

    public UsersPage fillUserForm(String username, String fullname, String email, String phone,
                                 String dob, String role, String type, String hourlyCost,
                                 String employeeCode, String password) {
        logger.info("📝 Filling User form: {}", username);
        sendKeys(userNameInput, username);
        sendKeys(userFullnameInput, fullname);
        sendKeys(userEmailInput, email);
        sendKeys(userPhoneInput, phone);
        sendKeys(userDateOfBirthInput, dob);

        selectFromAngularDropdown(userRoleDropdown, role);
        new Select(driver.findElement(userTypeDropdown)).selectByVisibleText(type);

        sendKeys(userHourlyCost, hourlyCost);
        sendKeys(userEmployeeCode, employeeCode);
        sendKeys(userPasswordInput, password);
        sendKeys(userConfirmPassword, password);
        return this;
    }

    public UsersPage saveUser() {
        logger.info("💾 Saving User");
        click(saveUserButton);
        waitForToastToDisappear();
        return this;
    }

    public UsersPage editFirstUser(String newFullname) {
        logger.info("✏️ Editing First User -> {}", newFullname);
        click(firstUserActionBtn);
        click(editUserOption);
        sendKeys(userFullnameInput, newFullname);
        click(saveUserButton);
        waitForToastToDisappear();
        return this;
    }

    public UsersPage deleteUserByName(String username) {
        logger.info("🗑️ Deleting User: {}", username);
        click(userActionBtnByName(username));
        click(deleteUserOption);
        click(confirmDeleteUserBtn);
        waitForToastToDisappear();
        return this;
    }

    // ===== Internal Helper (Reduced log) =====
    private void selectFromAngularDropdown(By dropdownLocator, String value) {
        WebElement dropdown = driver.findElement(dropdownLocator);
        dropdown.click();
        By option = By.xpath("//mat-option//span[normalize-space()='" + value + "']");
        driver.findElement(option).click();
        logger.debug("✅ Angular Dropdown selected: {}", value);
    }
}