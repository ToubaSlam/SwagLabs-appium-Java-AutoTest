package W3cTouchActions.pages;

import io.appium.java_client.AppiumBy;
import io.appium.java_client.AppiumDriver;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.ios.IOSDriver;
import io.qameta.allure.Step;
import org.openqa.selenium.By;
import org.testng.Assert;

import java.text.DecimalFormat;

public class CheckoutOverviewPage extends _BasePage {
    //Variables
    String productName;

    //Constructor
    public CheckoutOverviewPage(AppiumDriver driver) {
        super(driver);
        super.initializeLocator();
    }

    //Locators
    private By pageTitleText;
    private By productCard;
    private By productNameText;
    private By productPriceText;
    private By productDescriptionText;
    private By productQuantityText;
    private By removeFromCartRedButton;
    private By paymentMethodText;
    private By shippingMethodText;
    private By totalPriceText;
    private By finishButton;

    //Initialize Locators Based on Android or IOS
    public void initializeLocator() {
        if (driver instanceof AndroidDriver) {
            pageTitleText = AppiumBy.xpath("//android.widget.TextView[@text=\"CHECKOUT: OVERVIEW\"]");
            productCard = AppiumBy.xpath("//android.widget.TextView[@text=\"%s\"]/ancestor::android.view.ViewGroup[@content-desc=\"test-Item\"]".formatted(productName));
            productNameText = AppiumBy.xpath("//android.widget.TextView[@text=\"%s\"]".formatted(productName));
            productPriceText = AppiumBy.xpath("//android.widget.TextView[@text=\"%s\"]/ancestor::android.view.ViewGroup[@content-desc=\"test-Item\"]//android.view.ViewGroup[@content-desc=\"test-Price\"]//android.widget.TextView".formatted(productName));
            productDescriptionText = AppiumBy.xpath("//android.widget.TextView[@text=\"%s\"]/following-sibling::android.widget.TextView".formatted(productName));
            productQuantityText = AppiumBy.xpath("//android.widget.TextView[@text=\"%s\"]/ancestor::android.view.ViewGroup[@content-desc=\"test-Item\"]//android.view.ViewGroup[@content-desc=\"test-Amount\"]//android.widget.TextView".formatted(productName));
            removeFromCartRedButton = AppiumBy.accessibilityId("test-Delete");
            paymentMethodText = AppiumBy.xpath("//android.widget.TextView[@text=\"Payment Information:\"]/following-sibling::android.widget.TextView[1]");
            shippingMethodText = AppiumBy.xpath("//android.widget.TextView[@text=\"Shipping Information:\"]/following-sibling::android.widget.TextView[1]");
            totalPriceText = AppiumBy.xpath("//android.widget.TextView[contains(@text,\"Total\")]");
            finishButton = AppiumBy.accessibilityId("test-FINISH");

        } else if (driver instanceof IOSDriver) {
            pageTitleText = AppiumBy.xpath("//XCUIElementTypeStaticText[@name=\"CHECKOUT: OVERVIEW\"]");
            productCard = AppiumBy.xpath("//XCUIElementTypeStaticText[@label=\"%s\"]/ancestor::XCUIElementTypeOther[@name=\"test-Item\"]".formatted(productName));
            productNameText = AppiumBy.xpath("//XCUIElementTypeStaticText[@label=\"%s\"]".formatted(productName));
            productPriceText = AppiumBy.xpath("//XCUIElementTypeStaticText[@label=\"%s\"]/ancestor::XCUIElementTypeOther[@name=\"test-Item\"]//XCUIElementTypeOther[@name=\"test-Price\"]".formatted(productName));
            productDescriptionText = AppiumBy.xpath("//XCUIElementTypeStaticText[@label=\"%s\"]/following-sibling::XCUIElementTypeStaticText".formatted(productName));
            productQuantityText = AppiumBy.xpath("//XCUIElementTypeStaticText[@label=\"%s\"]/ancestor::XCUIElementTypeOther[@name=\"test-Item\"]//XCUIElementTypeOther[@name=\"test-Amount\"]".formatted(productName));
            removeFromCartRedButton = AppiumBy.xpath("//XCUIElementTypeStaticText[@label=\"%s\"]/ancestor::*[@name=\"test-Item\"]/parent::*/following-sibling::*//*[@name=\"test-Delete\"]".formatted(productName));
            paymentMethodText = AppiumBy.xpath("//XCUIElementTypeStaticText[@name=\"Payment Information:\"]/following-sibling::XCUIElementTypeStaticText");
            shippingMethodText = AppiumBy.xpath("//XCUIElementTypeStaticText[@name=\"Shipping Information:\"]/following-sibling::XCUIElementTypeStaticText");
            totalPriceText = AppiumBy.xpath("//XCUIElementTypeStaticText[contains(@name,\"Total:\")]");
            finishButton = AppiumBy.accessibilityId("test-FINISH");

        }
    }

    //Actions
    @Step
    public CheckoutOverviewPage finishCartCheckOut() {
        finger.tap(finishButton, "Vertical");
        return this;
    }

    @Step
    public CheckoutOverviewPage removeFromCartBySwipe(String productName) {
        //we pass productName from test case, then we convert it from local variable to global variable
        this.productName = productName;
        //we need to initialize the locators again because we make change in the locator
        initializeLocator();
        //we perform remove action through utils or w3c touch actions
        finger.scrollUntilElementDisplayed(productCard, "Vertical");
        finger.singleSwipeIntoElement("Right", productCard);
        finger.tap(removeFromCartRedButton);
        return this;
    }

    //Validations
    @Step
    public CheckoutOverviewPage verifyCheckoutCheckoutPageTitleIsDisplayed() {
        boolean flag = finger.isElementDisplayed(pageTitleText);
        Assert.assertTrue(flag);
        return this;
    }

    @Step
    public CheckoutOverviewPage verifyProductIsAddedToCart(String productName) {
        //we pass productName from test case, then we convert it from local variable to global variable
        this.productName = productName;
        //we need to initialize the locators again because we make change in the locator
        initializeLocator();
        //we take validation through utils
        boolean flag = finger.isElementDisplayed(productNameText, "Vertical");
        Assert.assertTrue(flag);
        return this;
    }

    @Step
    public CheckoutOverviewPage verifyProductIsRemovedFromCart(String productName ) {
        //we pass productName from test case, then we convert it from local variable to global variable
        this.productName = productName;
        //we need to initialize the locators again because we make change in the locator
        initializeLocator();
        //we take validation through utils
        boolean flag = finger.isElementNotDisplayed(productNameText, "Vertical");
        Assert.assertTrue(flag);
        return this;
    }

    @Step
    public CheckoutOverviewPage verifyProductPrice(String productName, String price) {
        //we pass productName from test case, then we convert it from local variable to global variable
        this.productName = productName;
        //we need to initialize the locators again because we make change in the locator
        initializeLocator();
        //we take validation through utils
        String actual = finger.readTextFromElement(productPriceText,"Vertical");
        Assert.assertTrue(actual.contains(price));
        return this;
    }

    @Step
    public CheckoutOverviewPage verifyProductQuantity(String productName, String quantity) {
        //we pass productName from test case, then we convert it from local variable to global variable
        this.productName = productName;
        //we need to initialize the locators again because we make change in the locator
        initializeLocator();
        //we take validation through utils
        String actual = finger.readTextFromElement(productQuantityText, "Vertical");
        Assert.assertEquals(actual, quantity);
        return this;
    }

    @Step
    public CheckoutOverviewPage verifyProductDescription(String productName, String description) {
        //we pass productName from test case, then we convert it from local variable to global variable
        this.productName = productName;
        //we need to initialize the locators again because we make change in the locator
        initializeLocator();
        //we take validation through utils
        String actual = finger.readTextFromElement(productDescriptionText, "Vertical");
        Assert.assertEquals(actual, description);
        return this;
    }

    @Step
    public CheckoutOverviewPage verifyTotalPriceOfProducts(double expectedTotalPrice) {
        //Read Actual Price from Page and Remove the dollar sign
        String actualString = finger.readTextFromElement(totalPriceText, "Vertical").split("\\$", 2)[1];

        //Convert he Actual Price from String to double
        double actualPrice = Double.parseDouble(actualString);

        //Get the Total Expected Price from Test Case and Multiply it with Tax Rate
        double expectedPriceAfterTax = expectedTotalPrice * 1.08;

        //Approximate the Total Expected Price with only 2 decimal places
        double expectedPriceWithTwoDecimalPlaces = Double.parseDouble(new DecimalFormat("0.00").format(expectedPriceAfterTax));

        //Compare between the Actual Price with Expected Price
        Assert.assertEquals(actualPrice, expectedPriceWithTwoDecimalPlaces);
        return this;
    }

    @Step
    public CheckoutOverviewPage verifyPaymentMethod(String paymentMethod) {
        String actual = finger.readTextFromElement(paymentMethodText, "Vertical");
        Assert.assertEquals(actual, paymentMethod);
        return this;
    }

    @Step
    public CheckoutOverviewPage verifyShippingMethod(String shippingMethod) {
        String actual = finger.readTextFromElement(shippingMethodText, "Vertical");
        Assert.assertEquals(actual, shippingMethod);
        return this;
    }

}
