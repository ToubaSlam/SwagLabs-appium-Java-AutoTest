package NativeMobileActions.pages;

import io.appium.java_client.AppiumBy;
import io.appium.java_client.AppiumDriver;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.ios.IOSDriver;
import io.qameta.allure.Step;
import org.openqa.selenium.By;
import org.testng.Assert;

public class ProductDetailsPage extends _BasePage {
    //Variables

    //Constructor
    public ProductDetailsPage(AppiumDriver driver) {
        super(driver);
        super.initializeLocator();
    }

    //Locators
    private By productImage;
    private By productTitleText;
    private By productDescriptionText;
    private By productPriceText;
    private By addToCartButton;

    //Initialize Locators Based on Android or IOS
    public void initializeLocator() {
        if (driver instanceof AndroidDriver) {
            productImage = AppiumBy.accessibilityId("test-Image Container");
            productTitleText = AppiumBy.xpath("//android.view.ViewGroup[@content-desc=\"test-Description\"]//android.widget.TextView[1]");
            productDescriptionText = AppiumBy.xpath("//android.view.ViewGroup[@content-desc=\"test-Description\"]//android.widget.TextView[2]");
            productPriceText = AppiumBy.accessibilityId("test-Price");
            addToCartButton = AppiumBy.accessibilityId("test-ADD TO CART");
        } else if (driver instanceof IOSDriver) {
            productImage = AppiumBy.accessibilityId("test-Image Container");
            productTitleText = AppiumBy.xpath("//XCUIElementTypeOther[@name=\"test-Description\"]//XCUIElementTypeStaticText[1]");
            productDescriptionText = AppiumBy.xpath("//XCUIElementTypeOther[@name=\"test-Description\"]//XCUIElementTypeStaticText[2]");
            productPriceText = AppiumBy.accessibilityId("test-Price");
            addToCartButton = AppiumBy.accessibilityId("test-ADD TO CART");
        }

    }

    //Actions
    @Step
    public ProductDetailsPage addProductToCart() {
        action.tap(addToCartButton,"Vertical");
        return this;
    }

    @Step
    public ProductDetailsPage zoomInProductImage(double zoomingPercentage) {
        action.zoomIn(productImage, zoomingPercentage);
        return this;
    }

    @Step
    public ProductDetailsPage zoomOutProductImage(double zoomingPercentage) {
        action.zoomOut(productImage, zoomingPercentage);
        return this;
    }

    //Validations
    @Step
    public ProductDetailsPage verifyProductImageIsDisplayed() {
        boolean actual = action.isElementDisplayed(productImage);
        Assert.assertTrue(actual);
        return this;
    }

    @Step
    public ProductDetailsPage verifyProductTitle(String expectedTitle) {
        String actual = action.readTextFromElement(productTitleText, "Vertical");
        Assert.assertEquals(actual, expectedTitle);
        return this;
    }

    @Step
    public ProductDetailsPage verifyProductDescription(String expectedDescription) {
        String actual = action.readTextFromElement(productDescriptionText, "Vertical");
        Assert.assertEquals(actual, expectedDescription);
        return this;
    }

    @Step
    public ProductDetailsPage verifyProductPrice(String expectedPrice) {
        String actual = action.readTextFromElement(productPriceText, "Vertical");
        Assert.assertTrue(actual.contains(expectedPrice));
        return this;
    }

    @Step
    public ProductDetailsPage verifyAddToCartButtonDisplayed() {
        Assert.assertTrue(action.isElementDisplayed(addToCartButton, "Vertical"));
        return this;
    }
}
