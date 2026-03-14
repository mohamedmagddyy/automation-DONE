package com.DoneProject.Pages;

import org.openqa.selenium.By;

public class LoginPage extends BasePage {

    private final By usernameInput = By.id("validationServer01");
    private final By passwordInput = By.id("validationServer02");
    private final By loginBtn = By.cssSelector("button.btn-login");

    public LoginPage() {
        super();
    }

    public void login(String username, String password) {

        waitForPageToBeReady();

        sendKeys(usernameInput, username);
        sendKeys(passwordInput, password);
        click(loginBtn);

        waitForPageToBeReady();
    }
}