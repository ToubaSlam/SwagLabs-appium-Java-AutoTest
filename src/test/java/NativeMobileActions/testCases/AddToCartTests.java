package NativeMobileActions.testCases;


import NativeMobileActions.pages.CartPage;
import NativeMobileActions.pages.LoginPage;
import NativeMobileActions.pages.ProductsPage;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

public class AddToCartTests extends _BaseTest {

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
    public void addProductToCartByButton() {

        new ProductsPage(driver)
                .addProductToCartByButton(json.readTestData("products[0].name"))
                .addProductToCartByButton(json.readTestData("products[1].name"))
                .addProductToCartByButton(json.readTestData("products[2].name"))
                .navigateToCart();

        new CartPage(driver)
                .verifyCartPageTitleIsDisplayed()
                .verifyProductIsAddedToCart(json.readTestData("products[0].name"))
                .verifyProductIsAddedToCart(json.readTestData("products[1].name"))
                .verifyProductIsAddedToCart(json.readTestData("products[2].name"));
    }

    @Test(groups = {"Positive"})
    public void addProductToCartByDragDrop() {

        new ProductsPage(driver)
                .addProductToCartByDragDrop(json.readTestData("products[0].name"))
                .addProductToCartByDragDrop(json.readTestData("products[1].name"))
                .addProductToCartByDragDrop(json.readTestData("products[2].name"))
                .navigateToCart();

        new CartPage(driver)
                .verifyCartPageTitleIsDisplayed()
                .verifyProductIsAddedToCart(json.readTestData("products[0].name"))
                .verifyProductIsAddedToCart(json.readTestData("products[1].name"))
                .verifyProductIsAddedToCart(json.readTestData("products[2].name"));
    }

    @Test(groups = {"Negative"})
    public void addProductToCartTwice() {
        new ProductsPage(driver)
                .addProductToCartByButton(json.readTestData("products[0].name"))
                .addProductToCartByDragDrop(json.readTestData("products[2].name"))
                .verifyAddToCartButtonIsRemovedFromPage(json.readTestData("products[0].name"))
                .verifyAddToCartButtonIsRemovedFromPage(json.readTestData("products[2].name"));
    }

}

