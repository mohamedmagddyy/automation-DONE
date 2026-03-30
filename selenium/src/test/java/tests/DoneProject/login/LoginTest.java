package tests.DoneProject.login;

import com.DoneProject.Pages.LoginPage;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import tests.DoneProject.BaseTest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class LoginTest extends BaseTest {

    private static final Logger logger = LoggerFactory.getLogger(LoginTest.class);
    private LoginPage loginPage;

    @BeforeMethod
    public void setupTest() {
        logger.info("Setting up LoginTest: initializing LoginPage");
        loginPage = new LoginPage();
    }

    // --- Valid Login Test ---
    @Test(dataProvider = "validLoginData", description = "Verify successful login changes the URL")
    public void testSuccessfulLogin(String username, String password) {
        logger.info("Executing testSuccessfulLogin for user: {}", username);

        loginPage.login(username, password);

        // Assert: Login should be successful (navbar visible)
        Assert.assertTrue(loginPage.isLoginSuccessful(), "Login was not successful; Navbar not detected!");
        
        // Assert: URL should transition away from login page
        String currentUrl = driver.getCurrentUrl();
        Assert.assertFalse(currentUrl.contains("login"),
                "Expected URL to transition away from /login upon successful entry. Current URL: " + currentUrl);
    }

    // --- Invalid Login Test ---
    @Test(dataProvider = "invalidLoginData", description = "Verify invalid logins do not proceed")
    public void testInvalidLogin(String username, String password) {
        logger.info("Executing testInvalidLogin for user: {}", username);

        loginPage.login(username, password);

        // Assert: Login should NOT be successful
        Assert.assertFalse(loginPage.isLoginSuccessful(), "Login should have failed for invalid credentials!");
    }

    // --- Empty Fields Validation ---
    @Test(description = "Verify empty fields show validation errors")
    public void testEmptyFieldsValidation() {
        logger.info("Executing testEmptyFieldsValidation");

        loginPage.login("", "");

        // Assert: Still on login page and not successful
        Assert.assertTrue(loginPage.isPageLoaded(), "Login page should still be loaded for empty fields");
        Assert.assertFalse(loginPage.isLoginSuccessful(), "Login should not be successful for empty fields");
    }

    // --- Data Providers ---
    @DataProvider(name = "validLoginData")
    public Object[][] getValidLoginData() {
        return new Object[][] {
                { "ismealadmin", "123456" } 
        };
    }

    @DataProvider(name = "invalidLoginData")
    public Object[][] getInvalidLoginData() {
        return new Object[][] {
                { "invalidUser@example.com", "wrongPass!" },
                { "ismealadmin", "wrongPass!" },
                { " ", " " }
        };
    }
}