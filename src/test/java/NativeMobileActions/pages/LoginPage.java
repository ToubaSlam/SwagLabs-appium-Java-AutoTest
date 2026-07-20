package NativeMobileActions.pages;

import io.appium.java_client.AppiumBy;
import io.appium.java_client.AppiumDriver;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.ios.IOSDriver;
import io.qameta.allure.Step;
import org.openqa.selenium.By;
import org.testng.Assert;

public class LoginPage extends _BasePage {
    //Variables

    //Constructor
    public LoginPage(AppiumDriver driver) {
        super(driver);
        super.initializeLocator();
    }

    //Locators
    private By userNameField;
    private By passwordField;
    private By loginButton;
    private By invalidUserErrorMessage;

    //Initialize Locators Based on Android or IOS
    public void initializeLocator() {
        if (driver instanceof AndroidDriver) {
            userNameField = AppiumBy.accessibilityId("test-Username");
            passwordField = AppiumBy.accessibilityId("test-Password");
            loginButton = AppiumBy.accessibilityId("test-LOGIN");
            invalidUserErrorMessage = AppiumBy.xpath("//android.view.ViewGroup[@content-desc=\"test-Error message\"]//android.widget.TextView");
        } else if (driver instanceof IOSDriver) {
            userNameField = AppiumBy.accessibilityId("test-Username");
            passwordField = AppiumBy.accessibilityId("test-Password");
            loginButton = AppiumBy.accessibilityId("LOGIN");
            invalidUserErrorMessage = AppiumBy.accessibilityId("test-Error message");
        }
    }

    //Actions
    @Step
    public LoginPage typeUsername(String name) {
        action.type(userNameField, name);
        return this;
    }

    @Step
    public LoginPage typePassword(String password) {
        action.type(passwordField, password);
        return this;
    }

    @Step
    public LoginPage clickOnLoginButton() {
        action.tap(loginButton);
        return this;
    }

    //Validations
    @Step
    public LoginPage verifyErrorMessageIsDisplayed(String expected) {
        Assert.assertTrue(action.isElementDisplayed(invalidUserErrorMessage));
        String actual = action.readTextFromElement(invalidUserErrorMessage);
        Assert.assertEquals(actual, expected);
        return this;
    }

}
