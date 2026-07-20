package NativeMobileActions.pages;

import io.appium.java_client.AppiumBy;
import io.appium.java_client.AppiumDriver;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.ios.IOSDriver;
import io.qameta.allure.Step;
import org.openqa.selenium.By;
import org.testng.Assert;

public class CheckoutSuccessPage extends _BasePage {
    //Variables

    //Constructor
    public CheckoutSuccessPage(AppiumDriver driver) {
        super(driver);
        super.initializeLocator();
    }

    //Locators
    private By successMessageText;
    private By successImage;


    //Initialize Locators Based on Android or IOS
    public void initializeLocator() {
        if (driver instanceof AndroidDriver) {
            successMessageText = AppiumBy.xpath("//android.widget.ScrollView[@content-desc=\"test-CHECKOUT: COMPLETE!\"]//android.widget.TextView[1]");
            successImage = AppiumBy.xpath("//android.widget.ScrollView[@content-desc=\"test-CHECKOUT: COMPLETE!\"]//android.widget.ImageView");
        } else if (driver instanceof IOSDriver) {
            successMessageText = AppiumBy.xpath("(//XCUIElementTypeOther[@name=\"test-CHECKOUT: COMPLETE!\"]//XCUIElementTypeStaticText)[1]");
            successImage = AppiumBy.accessibilityId("assets/src/img/pony-express.png");
        }
    }

    //Actions

    //Validations
    @Step
    public CheckoutSuccessPage verifySuccessMessageIsDisplayed(String expectedMessage) {
        String actual = action.readTextFromElement(successMessageText);
        Assert.assertEquals(actual, expectedMessage);
        return this;
    }

    @Step
    public CheckoutSuccessPage verifySuccessImageIsDisplayed() {
        boolean flag = action.isElementDisplayed(successImage);
        Assert.assertTrue(flag);
        return this;
    }

}
