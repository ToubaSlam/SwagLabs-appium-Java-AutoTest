package NativeMobileActions.testCases;

import NativeMobileActions.pages.CartPage;
import NativeMobileActions.pages.LoginPage;
import NativeMobileActions.pages.ProductsPage;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

public class RemoveFromCartTests extends _BaseTest {

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
    public void removeOneProductFromCartByButton() {

        new ProductsPage(driver)
                .addProductToCartByButton(json.readTestData("products[0].name"))
                .addProductToCartByButton(json.readTestData("products[2].name"))
                .addProductToCartByButton(json.readTestData("products[1].name"))
                .navigateToCart();

        new CartPage(driver)
                .verifyCartPageTitleIsDisplayed()
                .removeFromCartByButton(json.readTestData("products[0].name"))
                .verifyProductIsAddedToCart(json.readTestData("products[1].name"))
                .verifyProductIsAddedToCart(json.readTestData("products[2].name"))
                .verifyProductIsRemovedFromCart(json.readTestData("products[0].name"));
    }

    @Test(groups = {"Positive"})
    public void removeAllProductsFromCartByButton() {

        new ProductsPage(driver)
                .addProductToCartByButton(json.readTestData("products[0].name"))
                .addProductToCartByButton(json.readTestData("products[2].name"))
                .addProductToCartByButton(json.readTestData("products[1].name"))
                .navigateToCart();

        new CartPage(driver)
                .verifyCartPageTitleIsDisplayed()
                .removeFromCartByButton(json.readTestData("products[0].name"))
                .removeFromCartByButton(json.readTestData("products[2].name"))
                .removeFromCartByButton(json.readTestData("products[1].name"))
                .verifyProductIsRemovedFromCart(json.readTestData("products[0].name"))
                .verifyProductIsRemovedFromCart(json.readTestData("products[2].name"))
                .verifyProductIsRemovedFromCart(json.readTestData("products[1].name"));
    }

    @Test(groups = {"Positive"})
    public void removeAllProductsFromCartBySwipe() {

        new ProductsPage(driver)
                .addProductToCartByButton(json.readTestData("products[0].name"))
                .addProductToCartByButton(json.readTestData("products[2].name"))
                .addProductToCartByButton(json.readTestData("products[1].name"))
                .navigateToCart();

        new CartPage(driver)
                .verifyCartPageTitleIsDisplayed()
                .removeFromCartBySwipe(json.readTestData("products[0].name"))
                .removeFromCartBySwipe(json.readTestData("products[2].name"))
                .removeFromCartBySwipe(json.readTestData("products[1].name"))
                .verifyProductIsRemovedFromCart(json.readTestData("products[0].name"))
                .verifyProductIsRemovedFromCart(json.readTestData("products[2].name"))
                .verifyProductIsRemovedFromCart(json.readTestData("products[1].name"));
    }

}

