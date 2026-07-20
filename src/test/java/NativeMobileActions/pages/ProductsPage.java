package NativeMobileActions.pages;

import io.appium.java_client.AppiumBy;
import io.appium.java_client.AppiumDriver;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.ios.IOSDriver;
import io.qameta.allure.Step;
import org.openqa.selenium.By;
import org.testng.Assert;

public class ProductsPage extends _BasePage {
    //Variables
    String productName;

    //Constructor
    public ProductsPage(AppiumDriver driver) {
        super(driver);
        super.initializeLocator();
    }

    //Locators
    private By productsPageTitle;
    private By productNameText;
    private By addToCartButton;
    private By dragButton;
    private By dropButton;

    //Initialize Locators Based on Android or IOS
    @Override
    public void initializeLocator() {
        if (driver instanceof AndroidDriver) {
            productsPageTitle = AppiumBy.xpath("//android.widget.TextView[@text=\"PRODUCTS\"]");
            productNameText = AppiumBy.xpath("//android.widget.TextView[@text=\"%s\"]".formatted(productName));
            addToCartButton = AppiumBy.xpath("//android.widget.TextView[@text=\"%s\"]/following-sibling::android.view.ViewGroup[@content-desc=\"test-ADD TO CART\"]".formatted(productName));
            dragButton = AppiumBy.xpath("//android.widget.TextView[@text=\"%s\"]/following-sibling::android.view.ViewGroup[@content-desc=\"test-Drag Handle\"]//android.widget.TextView".formatted(productName));
            dropButton = AppiumBy.accessibilityId("test-Cart drop zone");
        } else if (driver instanceof IOSDriver) {
            productsPageTitle = AppiumBy.xpath("//XCUIElementTypeStaticText[@label=\"PRODUCTS\"]");
            productNameText = AppiumBy.xpath("//XCUIElementTypeStaticText[@label=\"%s\"]".formatted(productName));
            addToCartButton = AppiumBy.xpath("//XCUIElementTypeStaticText[@label=\"%s\"]/ancestor::XCUIElementTypeOther[@name=\"test-Item\"]//XCUIElementTypeOther[@name=\"ADD TO CART\"]".formatted(productName));
            dragButton = AppiumBy.xpath("//XCUIElementTypeStaticText[@label=\"%s\"]/ancestor::XCUIElementTypeOther[@name=\"test-Item\"]//XCUIElementTypeOther[@name=\"test-Drag Handle\"]".formatted(productName));
            dropButton = AppiumBy.accessibilityId("test-Cart drop zone");
        }

    }

    //Actions
    @Step
    public ProductsPage addProductToCartByButton(String productName) {
        //we pass productName from test case, then we convert it from local variable to global variable
        this.productName = productName;
        //we need to initialize the locators again because we make change in the locator
        initializeLocator();
        //we take tap action through utils
        action.tap(addToCartButton, "Vertical");
        return this;
    }

    @Step
    public ProductsPage addProductToCartByDragDrop(String productName) {
        //we pass productName from test case, then we convert it from local variable to global variable
        this.productName = productName;
        //we need to initialize the locators again because we make change in the locator
        initializeLocator();
        //we take tap action through utils
        action.dragAndDrop(dragButton, dropButton, "Vertical");
        return this;
    }

    @Step
    public ProductsPage selectProduct(String productName) {
        //we pass productName from test case, then we convert it from local variable to global variable
        this.productName = productName;
        //we need to re-initialize the locators again because we make change in the locator
        initializeLocator();
        //we take tap action through utils
        action.tap(productNameText, "Vertical");
        return this;
    }

    //Validations
    @Step
    public ProductsPage verifyProductsPageTitleIsDisplayed() {
        Assert.assertTrue(action.isElementDisplayed(productsPageTitle));
        return this;
    }

    @Step
    public ProductsPage verifyAddToCartButtonIsRemovedFromPage(String productName) {
        //we pass productName from test case, then we convert it from local variable to global variable
        this.productName = productName;
        //we need to re-initialize the locators again because we make change in the locator
        initializeLocator();
        //we take tap action through utils
        boolean flag = action.isElementNotDisplayed(addToCartButton, "Vertical");
        Assert.assertTrue(flag);
        return this;
    }

}
