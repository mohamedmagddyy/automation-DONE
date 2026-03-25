package tests.DoneProject.login;

import com.DoneProject.Pages.LoginPage;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import tests.DoneProject.BaseTest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

public class LoginTest extends BaseTest {

    private static final Logger logger = LoggerFactory.getLogger(LoginTest.class);
    private LoginPage loginPage;

    @BeforeMethod
    public void setupTest() {
        logger.info("Setting up LoginTest: initializing LoginPage");
        loginPage = new LoginPage(driver);
    }

    // --- Valid Login Test ---
    @Test(dataProvider = "validLoginData", description = "Verify successful login changes the URL")
    public void testSuccessfulLogin(String username, String password) {
        logger.info("Executing testSuccessfulLogin for user: {}", username);
        
        List<String> errors = loginPage.login(username, password);

        // Assert: No error messages
        Assert.assertTrue(errors.isEmpty(), "Unexpected error messages: " + errors);

        // Assert: URL should transition away from login page
        String currentUrl = driver.getCurrentUrl();
        Assert.assertFalse(currentUrl.contains("login"),
                "Expected URL to transition away from /login upon successful entry. Current URL: " + currentUrl);
    }

    // --- Invalid Login Test ---
    @Test(dataProvider = "invalidLoginData", description = "Verify invalid logins trigger error messages")
    public void testInvalidLogin(String username, String password) {
        logger.info("Executing testInvalidLogin for user: {}", username);
        
        List<String> errors = loginPage.login(username, password);

        Assert.assertTrue(
            errors.stream().anyMatch(e -> 
                e.contains("incorrect") ||
                e.contains("Enter User name") ||
                e.contains("Enter Password") ||
                e.contains("Username must")
            ),
            "Expected error message not found! Actual: " + errors
        );
    }

    // --- Empty Fields Validation ---
    @Test(description = "Verify empty fields show validation errors")
    public void testEmptyFieldsValidation() {
        logger.info("Executing testEmptyFieldsValidation");
        
        List<String> errors = loginPage.login("", "");

        Assert.assertTrue(
            errors.stream().anyMatch(e -> 
                e.contains("Enter User name") ||
                e.contains("Enter Password")
            ),
            "Expected validation messages for empty fields not found! Actual: " + errors
        );
    }

    // --- Data Providers ---
    @DataProvider(name = "validLoginData")
    public Object[][] getValidLoginData() {
        return new Object[][] {
            { "ismealadmin", "123456" } // تأكد من البيانات حسب البيئة
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