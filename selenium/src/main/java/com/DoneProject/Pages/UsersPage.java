package com.DoneProject.Pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.Select;

public class UsersPage extends BasePage {

    private final By addUserButton = By.id("dropdownMenuButton1");
    private final By newUserOption = By.xpath("//a[normalize-space()='Add User']");

    private final By userNameInput = By.id("userName");
    private final By userFullnameInput = By.id("fullName");
    private final By userEmailInput = By.id("email");
    private final By userPhoneInput = By.id("phone");
    private final By userDateOfBirthInput = By.id("contractDate");
    private final By userRoleDropdown = By.xpath("//mat-select[@formcontrolname='roleIds']");
    private final By userTypeDropdown = By.id("userType");
    private final By userHourlyCost = By.id("monthlySalary");
    private final By userEmployeeCode = By.id("employeeCode");
    private final By userPasswordInput = By.id("password");
    private final By userConfirmPasswordInput = By.id("confirmPassword");
    private final By saveUserButton = By.cssSelector("button.btn-submitdone");

    private final By firstUserActionBtn = By.xpath("//table//tbody//tr[1]//button");
    private final By editUserOption = By.xpath("//ul[@class='dropdown-menu show']//span[contains(text(),'Edit')]");
    private final By deleteUserOption = By.xpath("//ul[@class='dropdown-menu show']//span[contains(text(),'Delete employee')]");
    private final By confirmDeleteUserBtn = By.cssSelector("button.btn.btn-danger");

    public UsersPage() {
        super();
    }

    public void clickAddUser() {
        click(addUserButton);
        click(newUserOption);
    }

    private By userActionBtnByName(String username) {
        return By.xpath("//tr[td[normalize-space()='" + username + "']]//button");
    }

    public void fillUserForm(String username, String fullname, String email, String phone,
                             String dob, String role, String type, String hourlyCost,
                             String employeeCode, String password) {

        sendKeys(userNameInput, username);
        sendKeys(userFullnameInput, fullname);
        sendKeys(userEmailInput, email);
        sendKeys(userPhoneInput, phone);
        sendKeys(userDateOfBirthInput, dob);

        // Angular dropdown
        selectFromAngularDropdown(userRoleDropdown, role);

        // HTML <select>
        Select select = new Select(driver.findElement(userTypeDropdown));
        select.selectByVisibleText(type);

        sendKeys(userHourlyCost, hourlyCost);
        sendKeys(userEmployeeCode, employeeCode);
        sendKeys(userPasswordInput, password);
        sendKeys(userConfirmPasswordInput, password);
    }

    public void saveUser() {
        click(saveUserButton);
        waitForToastToDisappear();
    }

    public void editFirstUser(String newFullname) {
        click(firstUserActionBtn);
        click(editUserOption);
        sendKeys(userFullnameInput, newFullname);
        click(saveUserButton);
        waitForToastToDisappear();
    }

    public void deleteUserByName(String username) {
        click(userActionBtnByName(username));
        click(deleteUserOption);
        click(confirmDeleteUserBtn);
        waitForToastToDisappear();
    }

    // Angular dropdown helper
    private void selectFromAngularDropdown(By dropdownLocator, String value) {
        WebElement dropdown = driver.findElement(dropdownLocator);
        dropdown.click();
        WebElement option = driver.findElement(By.xpath("//mat-option//span[normalize-space()='" + value + "']"));
        option.click();
    }
}
