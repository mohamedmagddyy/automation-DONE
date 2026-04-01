package com.DoneProject.pages;

import org.openqa.selenium.By;

public class LoginPage extends BasePage {

    // --- Locators (Private & Stable) ---
    private final By emailInput      = By.id("validationServer01");
    private final By passwordInput   = By.id("validationServer02");
    private final By signInButton    = By.cssSelector("button.btn-login");
    private final By rememberMeCheck = By.xpath("//input[@type='checkbox']");

    public LoginPage() {
        super();
    }

    /**
     * @deprecated Use default constructor instead. Driver is handled by BasePage.
     */
    @Deprecated
    public LoginPage(org.openqa.selenium.WebDriver driver) {
        this();
    }

    // --- Actions ---

    /**
     * Performs a complete login flow.
     * @param email user email/username
     * @param password user password
     * @return NavBarPage representing the post-login state
     */
    public NavBarPage login(String email, String password) {
        enterEmail(email);
        enterPassword(password);
        clickSignIn();
        waitForPageToBeReady();
        return new NavBarPage();
    }

    public void enterEmail(String email) {
        sendKeys(emailInput, email);
    }

    public void enterPassword(String password) {
        sendKeys(passwordInput, password);
    }

    public void clickSignIn() {
        click(signInButton);
    }

    public void clickRememberMe() {
        click(rememberMeCheck);
    }

    /**
     * Checks if the login was successful by verifying if a post-login element is visible.
     * @return true if navbar/dashboard is visible, false otherwise.
     */
    public boolean isLoginSuccessful() {
        try {
            // Using a stable element from the NavBarPage to verify success
            return waitForElement(By.id("notifictions")).isDisplayed();
        } catch (Exception e) {
            return false;
        }
    }

    /**
     * Checks if the login page itself is loaded.
     */
    public boolean isPageLoaded() {
        try {
            return waitForElement(emailInput).isDisplayed();
        } catch (Exception e) {
            return false;
        }
    }
}
