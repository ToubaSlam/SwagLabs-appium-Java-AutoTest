package testCases;

import io.qameta.allure.Feature;
import io.qameta.allure.Story;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import pages.LoginPage;
import utils.ConfigReader;
import utils.DriverManager;
import utilities.BaseTest;
import utilities.JsonDataReader;
import utilities.LoginTestData;
import utilities.RetryAnalyzer;

import java.util.Map;

@Feature("Login")
public class LoginTests extends BaseTest {

    private LoginTestData testData;

    @BeforeClass(alwaysRun = true)
    public void loadTestData() {
        testData = JsonDataReader.read("login.json", LoginTestData.class);
    }

    @Test(retryAnalyzer = RetryAnalyzer.class, description = "Valid credentials land on the dashboard")
    @Story("Positive login")
    public void loginWithValidCredentials() {
        LoginPage loginPage = new LoginPage();
        loginPage.login(testData.validUser.email, testData.validUser.password);

        Assert.assertTrue(loginPage.isDashboardDisplayed(20),
                "Dashboard screen was not displayed after valid login");
    }

    @Test(retryAnalyzer = RetryAnalyzer.class, description = "Wrong password shows an inline error")
    @Story("Negative login")
    public void loginWithIncorrectPassword() {
        LoginPage loginPage = new LoginPage();
        loginPage.login(testData.invalidPasswordUser.email, testData.invalidPasswordUser.password);

        Assert.assertTrue(loginPage.isErrorDisplayed(), "Error message was not displayed for incorrect password");
        Assert.assertEquals(loginPage.getErrorMessage(), testData.expectedMessages.invalidCredentials);
    }

    @Test(retryAnalyzer = RetryAnalyzer.class, description = "Unregistered email shows an inline error")
    @Story("Negative login")
    public void loginWithUnregisteredEmail() {
        LoginPage loginPage = new LoginPage();
        loginPage.login(testData.unregisteredUser.email, testData.unregisteredUser.password);

        Assert.assertTrue(loginPage.isErrorDisplayed(), "Error message was not displayed for unregistered email");
    }

    @Test(retryAnalyzer = RetryAnalyzer.class, description = "Malformed email is rejected client-side")
    @Story("Negative login")
    public void loginWithMalformedEmail() {
        LoginPage loginPage = new LoginPage();
        loginPage.login(testData.malformedEmailUser.email, testData.malformedEmailUser.password);

        Assert.assertTrue(loginPage.isErrorDisplayed(), "Error message was not displayed for malformed email");
    }

    @Test(retryAnalyzer = RetryAnalyzer.class, description = "Expired session redirects back to the login screen")
    @Story("Session handling")
    public void sessionTimeoutRedirectsToLogin() {
        LoginPage loginPage = new LoginPage();
        loginPage.login(testData.validUser.email, testData.validUser.password);
        Assert.assertTrue(loginPage.isDashboardDisplayed(20));

        // Simulate an expired session by terminating and relaunching the
        // app, then confirm the user lands back on the login screen
        // rather than straight on the dashboard.
        String appId = "ios".equalsIgnoreCase(DriverManager.getPlatform())
                ? ConfigReader.get("bundleId")
                : ConfigReader.get("appPackage");

        driver.executeScript("mobile: terminateApp", Map.of("appId", appId));
        driver.executeScript("mobile: activateApp", Map.of("appId", appId));

        Assert.assertTrue(loginPage.isLoginScreenDisplayed(),
                "Expected to be redirected to the login screen after session timeout");
    }
}
