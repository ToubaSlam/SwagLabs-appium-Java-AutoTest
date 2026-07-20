package NativeMobileActions.pages;

import io.appium.java_client.AppiumBy;
import io.appium.java_client.AppiumDriver;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.ios.IOSDriver;
import io.qameta.allure.Step;
import org.openqa.selenium.By;
import org.testng.Assert;

public class CheckoutInfoPage extends _BasePage {
    //Variables

    //Constructor
    public CheckoutInfoPage(AppiumDriver driver) {
        super(driver);
        super.initializeLocator();
    }

    //Locators
    private By firstNameField;
    private By lastNameField;
    private By postalCodeField;
    private By errorMessageForEmptyField;
    private By pageTitleText;
    private By continueButton;

    //Initialize Locators Based on Android or IOS
    public void initializeLocator() {
        if (driver instanceof AndroidDriver) {
            firstNameField = AppiumBy.accessibilityId("test-First Name");
            lastNameField = AppiumBy.accessibilityId("test-Last Name");
            postalCodeField = AppiumBy.accessibilityId("test-Zip/Postal Code");
            errorMessageForEmptyField = AppiumBy.xpath("//android.view.ViewGroup[@content-desc=\"test-Error message\"]//android.widget.TextView");
            pageTitleText = AppiumBy.xpath("//android.widget.TextView[@text=\"CHECKOUT: INFORMATION\"]");
            continueButton = AppiumBy.accessibilityId("test-CONTINUE");

        } else if (driver instanceof IOSDriver) {
            firstNameField = AppiumBy.accessibilityId("test-First Name");
            lastNameField = AppiumBy.accessibilityId("test-Last Name");
            postalCodeField = AppiumBy.accessibilityId("test-Zip/Postal Code");
            errorMessageForEmptyField = AppiumBy.accessibilityId("test-Error message");
            pageTitleText = AppiumBy.xpath("//XCUIElementTypeStaticText[@name=\"CHECKOUT: INFORMATION\"]");
            continueButton = AppiumBy.accessibilityId("test-CONTINUE");
        }
    }

    //Actions
    @Step
    public CheckoutInfoPage typeFirstname(String name) {
        action.type(firstNameField, name);
        return this;
    }

    @Step
    public CheckoutInfoPage typeLastname(String name) {
        action.type(lastNameField, name);
        return this;
    }

    @Step
    public CheckoutInfoPage typePostalCode(String postalCode) {
        action.type(postalCodeField, postalCode);
        return this;
    }

    @Step
    public CheckoutInfoPage confirmCheckoutInfo() {
        action.tap(continueButton);
        return this;
    }

    //Validations
    @Step
    public CheckoutInfoPage verifyCheckoutInfoPageTitleIsDisplayed() {
        boolean flag = action.isElementDisplayed(pageTitleText);
        Assert.assertTrue(flag);
        return this;
    }

    @Step
    public CheckoutInfoPage verifyErrorMessageIsDisplayed(String expected) {
        Assert.assertTrue(action.isElementDisplayed(errorMessageForEmptyField));
        String actual = action.readTextFromElement(errorMessageForEmptyField);
        Assert.assertEquals(actual, expected);
        return this;
    }

}
