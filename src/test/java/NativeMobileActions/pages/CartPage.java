package NativeMobileActions.pages;

import io.appium.java_client.AppiumBy;
import io.appium.java_client.AppiumDriver;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.ios.IOSDriver;
import io.qameta.allure.Step;
import org.openqa.selenium.By;
import org.testng.Assert;

public class CartPage extends _BasePage {
    //Variables
    String productName;

    //Constructor
    public CartPage(AppiumDriver driver) {
        super(driver);
        super.initializeLocator();
    }

    //Locators
    private By cartPageTitle;
    private By productCard;
    private By productNameText;
    private By removeFromCartButton;
    private By removeFromCartRedButton;
    private By checkoutButton;

    //Initialize Locators Based on Android or IOS
    @Override
    public void initializeLocator() {
        if (driver instanceof AndroidDriver) {
            cartPageTitle = AppiumBy.xpath("//android.widget.TextView[@text=\"YOUR CART\"]");
            productCard = AppiumBy.xpath("//android.widget.TextView[@text=\"%s\"]/ancestor::android.view.ViewGroup[@content-desc=\"test-Item\"]".formatted(productName));
            productNameText = AppiumBy.xpath("//android.widget.TextView[@text=\"%s\"]".formatted(productName));
            removeFromCartButton = AppiumBy.xpath("//android.widget.TextView[@text=\"%s\"]/ancestor::android.view.ViewGroup[@content-desc=\"test-Item\"]//android.view.ViewGroup[@content-desc=\"test-REMOVE\"]".formatted(productName));
            removeFromCartRedButton = AppiumBy.accessibilityId("test-Delete");
            checkoutButton = AppiumBy.accessibilityId("test-CHECKOUT");

        } else if (driver instanceof IOSDriver) {
            cartPageTitle = AppiumBy.xpath("//XCUIElementTypeStaticText[@label=\"YOUR CART\"]");
            productCard = AppiumBy.xpath("//XCUIElementTypeStaticText[@label=\"%s\"]/ancestor::XCUIElementTypeOther[@name=\"test-Item\"]".formatted(productName));
            productNameText = AppiumBy.xpath("//XCUIElementTypeStaticText[@label=\"%s\"]".formatted(productName));
            removeFromCartButton = AppiumBy.xpath("//XCUIElementTypeStaticText[@label=\"%s\"]/ancestor::XCUIElementTypeOther[@name=\"test-Item\"]//XCUIElementTypeOther[@name=\"test-REMOVE\"]".formatted(productName));
            removeFromCartRedButton = AppiumBy.xpath("//XCUIElementTypeStaticText[@label=\"%s\"]/ancestor::*[@name=\"test-Item\"]/parent::*/following-sibling::*//*[@name=\"test-Delete\"]".formatted(productName));
            checkoutButton = AppiumBy.accessibilityId("test-CHECKOUT");
        }

    }

    //Actions
    @Step
    public CartPage removeFromCartByButton(String productName) {
        //we pass productName from test case, then we convert it from local variable to global variable
        this.productName = productName;
        //we need to initialize the locators again because we make change in the locator
        initializeLocator();
        //we perform remove action through utils or w3c touch actions
        action.tap(removeFromCartButton,"Vertical");
        return this;
    }

    @Step
    public CartPage removeFromCartBySwipe(String productName) {
        //we pass productName from test case, then we convert it from local variable to global variable
        this.productName = productName;
        //we need to initialize the locators again because we make change in the locator
        initializeLocator();
        //we perform remove action through utils or w3c touch actions
        action.scrollUntilElementDisplayed(productCard, "Vertical");
        action.singleSwipeIntoElement("Right", productCard);
        action.tap(removeFromCartRedButton);
        return this;
    }

    @Step
    public CartPage selectToCheckoutTheCart() {
        action.tap(checkoutButton, "Vertical");
        return this;
    }


    //Validations
    @Step
    public CartPage verifyCartPageTitleIsDisplayed() {
        Assert.assertTrue(action.isElementDisplayed(cartPageTitle));
        return this;
    }

    @Step
    public CartPage verifyCheckoutButtonIsRemovedFromPage() {
        Assert.assertTrue(action.isElementNotDisplayed(checkoutButton, "Vertical"));
        return this;
    }

    @Step
    public CartPage verifyProductIsAddedToCart(String productName) {
        //we pass productName from test case, then we convert it from local variable to global variable
        this.productName = productName;
        //we need to initialize the locators again because we make change in the locator
        initializeLocator();
        //we take validation through utils or w3c touch actions
        boolean actual = action.isElementDisplayed(productNameText, "Vertical");
        Assert.assertTrue(actual);
        return this;
    }

    @Step
    public CartPage verifyProductIsRemovedFromCart(String productName) {
        //we pass productName from test case, then we convert it from local variable to global variable
        this.productName = productName;
        //we need to initialize the locators again because we make change in the locator
        initializeLocator();
        //we take validation through utils or w3c touch actions
        boolean actual = action.isElementNotDisplayed(productNameText, "Vertical");
        Assert.assertTrue(actual);
        return this;
    }

}
