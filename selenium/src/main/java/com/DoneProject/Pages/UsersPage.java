package com.DoneProject.Pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.Select;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

public class UsersPage extends BasePage {

    private static final Logger logger = LoggerFactory.getLogger(UsersPage.class);

    private final By addUserButton = By.id("dropdownMenuButton1");
    private final By addUserOption = By.xpath("//a[normalize-space()='Add User']");
    private final By userNameInput = By.id("userName");
    private final By userFullNameInput = By.id("fullName");
    private final By userEmailInput = By.id("email");
    private final By userPhoneInput = By.id("phone");
    private final By userDateOfBirthInput = By.id("contractDate");
    private final By userRoleDropdown = By.xpath("//mat-select[@formcontrolname='roleIds']");
    private final By userTypeDropdown = By.id("userType");
    private final By userHourlyCostInput = By.id("monthlySalary");
    private final By userEmployeeCodeInput = By.id("employeeCode");
    private final By userPasswordInput = By.id("password");
    private final By userConfirmPasswordInput = By.id("confirmPassword");
    private final By saveUserButton = By.cssSelector("button.btn-submitdone");
    private final By deleteUserOption = By
            .xpath("//ul[@class='dropdown-menu show']//span[contains(text(),'Delete employee')]");
    private final By employeeDataOption = By
            .xpath("//ul[@class='dropdown-menu show']//span[contains(text(),'Employee Data')]");
    private final By confirmDeleteBtn = By.cssSelector("button.btn.btn-danger");
    private final By tableHeaders = By.xpath("//table//th");

    public UsersPage() {
        super();
    }

    private By userActionBtnByName(String username) {
        return By.xpath("//tr[td[normalize-space()='" + username + "']]//button");
    }

    private By editUserOptionByName(String username) {
        return By.xpath("//ul[@class='dropdown-menu show']//span[contains(text(),'Edit')]");
    }

    public UsersPage clickAddUser() {
        logger.info("Opening Add User form");
        click(addUserButton);
        click(addUserOption);
        return this;
    }

    public UsersPage fillUserForm(String username, String fullName, String email, String phone,
            String dateOfBirth, String role, String type, String hourlyCost,
            String employeeCode, String password) {
        logger.info("Filling user form for: {}", username);
        sendKeys(userNameInput, username);
        sendKeys(userFullNameInput, fullName);
        sendKeys(userEmailInput, email);
        sendKeys(userPhoneInput, phone);
        sendKeys(userDateOfBirthInput, dateOfBirth);
        selectFromAngularDropdown(userRoleDropdown, role);
        selectFromNativeDropdown(userTypeDropdown, type);
        sendKeys(userHourlyCostInput, hourlyCost);
        sendKeys(userEmployeeCodeInput, employeeCode);
        sendKeys(userPasswordInput, password);
        sendKeys(userConfirmPasswordInput, password);
        return this;
    }

    public UsersPage saveUser() {
        logger.info("Saving user");
        click(saveUserButton);
        waitForToastToDisappear();
        return this;
    }

    public UsersPage addUser(String username, String password, String fullName, String role) {
        logger.info("Adding user: {}", username);
        clickAddUser();
        // Providing default values for other fields
        fillUserForm(username, fullName, username + "@example.com", "0123456789", "2024-01-01", role, "Staff", "100",
                "E101", password);
        saveUser();
        return this;
    }

    public UsersPage editUserByName(String username, String newFullName) {
        logger.info("Editing user: {} -> {}", username, newFullName);
        click(userActionBtnByName(username));
        click(editUserOptionByName(username));
        sendKeys(userFullNameInput, newFullName);
        click(saveUserButton);
        waitForToastToDisappear();
        return this;
    }

    public UsersPage editUser(String initialUserName, String updatedUserName, String password, String fullName,
            String role) {
        logger.info("Editing user: {} -> {}", initialUserName, updatedUserName);
        openEmployeeData(initialUserName);
        updateEmployeeData(password, password, role, "2024-01-01", "0123456789", updatedUserName, fullName,
                updatedUserName + "@example.com");
        waitForToastToDisappear();
        return this;
    }

    public UsersPage deleteUser(String username) {
        logger.info("Deleting user: {}", username);
        click(userActionBtnByName(username));
        click(deleteUserOption);
        click(confirmDeleteBtn);
        waitForToastToDisappear();
        return this;
    }

    public UsersPage openEmployeeData(String username) {
        logger.info("Opening Employee Data for user: {}", username);
        click(userActionBtnByName(username));
        click(employeeDataOption);
        return this;
    }

    public UsersPage updateEmployeeData(
            String password,
            String confirmPassword,
            String roleName,
            String contractDate,
            String phoneNumber,
            String username,
            String fullName,
            String email) {
        logger.info("Updating Employee Data fields");
        if (password != null)
            sendKeys(userPasswordInput, password);
        if (confirmPassword != null)
            sendKeys(userConfirmPasswordInput, confirmPassword);
        if (roleName != null)
            selectFromAngularDropdown(userRoleDropdown, roleName);
        if (contractDate != null)
            sendKeys(userDateOfBirthInput, contractDate);
        if (phoneNumber != null)
            sendKeys(userPhoneInput, phoneNumber);
        if (username != null)
            sendKeys(userNameInput, username);
        if (fullName != null)
            sendKeys(userFullNameInput, fullName);
        if (email != null)
            sendKeys(userEmailInput, email);

        click(saveUserButton);
        return this;
    }

    public String getCellValue(String username, String columnName) {
        logger.info("Getting value for column: {} for user: {}", columnName, username);
        List<WebElement> headers = driver.findElements(tableHeaders);
        int columnIndex = -1;
        for (int i = 0; i < headers.size(); i++) {
            if (headers.get(i).getText().trim().equalsIgnoreCase(columnName)) {
                columnIndex = i + 1;
                break;
            }
        }
        if (columnIndex == -1) {
            throw new RuntimeException("Column '" + columnName + "' not found in users table");
        }
        By cellLocator = By.xpath("//tr[td[normalize-space()='" + username + "']]//td[" + columnIndex + "]");
        return getText(cellLocator);
    }

    public String getValidationMessage(String fieldId) {
        logger.info("Getting validation message for field: {}", fieldId);
        By locator = By
                .xpath("//input[@id='" + fieldId + "']/following-sibling::div[contains(@class, 'invalid-feedback')]");
        return getText(locator);
    }

    private void selectFromAngularDropdown(By locator, String value) {
        WebElement dropdown = driver.findElement(locator);
        dropdown.click();
        By option = By.xpath("//mat-option//span[normalize-space()='" + value + "']");
        driver.findElement(option).click();
        logger.debug("Angular dropdown selected: {}", value);
    }

    private void selectFromNativeDropdown(By locator, String value) {
        WebElement element = driver.findElement(locator);
        new Select(element).selectByVisibleText(value);
        logger.debug("Native dropdown selected: {}", value);
    }

    public boolean isUserDisplayed(String username) {
        By userRow = By.xpath("//tr[td[normalize-space()='" + username + "']]");
        try {
            return driver.findElement(userRow).isDisplayed();
        } catch (Exception e) {
            return false;
        }
    }

}