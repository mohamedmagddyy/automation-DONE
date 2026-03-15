package com.DoneProject.Pages;

import org.openqa.selenium.By;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class LoginPage extends BasePage {

    private static final Logger logger = LoggerFactory.getLogger(LoginPage.class);

    private final By usernameInput = By.id("validationServer01");
    private final By passwordInput = By.id("validationServer02");
    private final By loginBtn      = By.cssSelector("button.btn-login");

    public LoginPage() {
        super();
    }

    public void login(String username, String password) {
        waitForPageToBeReady();

        sendKeys(usernameInput, username);
        sendKeys(passwordInput, password);
        click(loginBtn);

        waitForPageToBeReady();
        logger.info("✅ تم تسجيل الدخول بنجاح: {}", username);
    }
}