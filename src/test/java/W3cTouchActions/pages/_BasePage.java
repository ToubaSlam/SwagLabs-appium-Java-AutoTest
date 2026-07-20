package W3cTouchActions.pages;

import io.appium.java_client.AppiumBy;
import io.appium.java_client.AppiumDriver;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.ios.IOSDriver;
import io.qameta.allure.Step;
import org.openqa.selenium.By;
import org.testng.Assert;
import utils.elementActions.MobileActions;
import utils.elementActions.W3CTouchActions;


public class _BasePage {

    //Common Variables
    AppiumDriver driver;
    MobileActions finger;

    //Constructor
    public _BasePage(AppiumDriver driver) {
        finger = new W3CTouchActions(driver);
        this.driver = driver;
        initializeLocator();
    }

    //Header Locators
    private By appLogo;
    private By cartIcon;

    //Initialize Locators Based on Android or IOS
    public void initializeLocator() {
        if (driver instanceof AndroidDriver) {
            appLogo = AppiumBy.xpath("//android.widget.ImageView[1]");
            cartIcon = AppiumBy.accessibilityId("test-Cart");
        } else if (driver instanceof IOSDriver) {
            appLogo = AppiumBy.accessibilityId("assets/src/img/swag-labs-logo.png");
            cartIcon = AppiumBy.accessibilityId("test-Cart");
        }
    }

    //Header Actions
    @Step
    public _BasePage navigateToCart() {
        finger.doubleTap(cartIcon);
        return this;
    }

    //Header Validation
    @Step
    public _BasePage verifyLogoIsDisplayed() {
        boolean actualStatus = finger.isElementDisplayed(appLogo);
        Assert.assertTrue(actualStatus);
        return this;
    }


}
