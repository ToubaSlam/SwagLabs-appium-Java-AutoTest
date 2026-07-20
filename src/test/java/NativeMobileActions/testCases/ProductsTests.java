package NativeMobileActions.testCases;

import NativeMobileActions.pages.LoginPage;
import NativeMobileActions.pages.ProductDetailsPage;
import NativeMobileActions.pages.ProductsPage;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

public class ProductsTests extends _BaseTest {

    @BeforeMethod(alwaysRun = true)
    public void loginWithValidUser() {

        new LoginPage(driver)
                .typeUsername(json.readTestData("validUser.username"))
                .typePassword(json.readTestData("validUser.password"))
                .clickOnLoginButton();

        new ProductsPage(driver)
                .verifyProductsPageTitleIsDisplayed();

    }

    @Test(groups = {"Positive"})
    public void verifyAllProductDetailsAreDisplayed() {

        new ProductsPage(driver)
                .selectProduct(json.readTestData("products[0].name"));

        new ProductDetailsPage(driver)
                .verifyProductImageIsDisplayed()
                .verifyProductTitle(json.readTestData("products[0].name"))
                .verifyProductDescription(json.readTestData("products[0].description"))
                .verifyProductPrice(json.readTestData("products[0].price"))
                .verifyAddToCartButtonDisplayed();
    }

    @Test(groups = {"Positive"})
    public void verifyZoomActionOnProductImage() {

        new ProductsPage(driver)
                .selectProduct(json.readTestData("products[0].name"));

        new ProductDetailsPage(driver)
                .zoomInProductImage(0.5)
                .verifyProductImageIsDisplayed()
                .zoomOutProductImage(0.5)
                .verifyProductImageIsDisplayed();
    }
}


