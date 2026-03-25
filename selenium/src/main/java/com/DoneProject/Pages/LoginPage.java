package com.DoneProject.Pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import java.time.Duration;
import java.util.List;

public class LoginPage extends BasePage {

    private final WebDriver driver;
    private final WebDriverWait wait;

    // --- Locators ---
    private final By pageTitle = By.xpath("//*[contains(text(), 'Sign in') and (self::h1 or self::h2 or contains(@class, 'title'))]");
    private final By usernameInput = By.id("validationServer01");
    private final By passwordInput = By.id("validationServer02");
    private final By rememberMeCheckbox = By.xpath("//input[@type='checkbox' and following-sibling::label[contains(text(), 'Remember me')]]");
    private final By signInButton = By.cssSelector("button.btn-login");

    private final By errorMessages = By.xpath(
            "//div[contains(@class,'alert')]" +
            " | //p[contains(@style,'color: red')]" +
            " | //div[contains(text(),'Username must')]"
    );

    public LoginPage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(10));
    }

    // --- Actions ---
    public void enterUsername(String username) {
        if (username != null && !username.isEmpty()) {
            WebElement input = wait.until(ExpectedConditions.visibilityOfElementLocated(usernameInput));
            input.clear();
            input.sendKeys(username);
        }
    }

    public void enterPassword(String password) {
        if (password != null && !password.isEmpty()) {
            WebElement input = wait.until(ExpectedConditions.visibilityOfElementLocated(passwordInput));
            input.clear();
            input.sendKeys(password);
        }
    }

    public void clickRememberMe() {
        WebElement checkbox = wait.until(ExpectedConditions.presenceOfElementLocated(rememberMeCheckbox));
        if (!checkbox.isSelected()) {
            checkbox.click();
        }
    }

    public void clickSignIn() {
        WebElement button = wait.until(ExpectedConditions.elementToBeClickable(signInButton));
        button.click();
    }

    /**
     * Composite login method.
     * Returns a list of error messages if login fails, or empty list if login likely successful.
     */
    public List<String> login(String username, String password) {
        enterUsername(username);
        enterPassword(password);
        clickSignIn();

        try {
            // Wait a short time for errors to appear
            List<WebElement> errors = new WebDriverWait(driver, Duration.ofSeconds(3))
                    .until(ExpectedConditions.visibilityOfAllElementsLocatedBy(errorMessages));
            return errors.stream()
                    .map(WebElement::getText)
                    .map(String::trim)
                    .filter(t -> !t.isEmpty())
                    .toList();
        } catch (Exception e) {
            return java.util.Collections.emptyList(); // no errors found, assume success
        }
    }

    public boolean isPageLoaded() {
        try {
            return wait.until(ExpectedConditions.visibilityOfElementLocated(usernameInput)).isDisplayed();
        } catch (Exception e) {
            return false;
        }
    }
}