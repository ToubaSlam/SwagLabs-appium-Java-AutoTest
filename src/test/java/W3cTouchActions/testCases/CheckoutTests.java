package W3cTouchActions.testCases;

import W3cTouchActions.pages.*;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import static utils.DataGenerator.generateRandomName;
import static utils.DataGenerator.generateRandomPostalCode;

public class CheckoutTests extends _BaseTest {

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
    public void verifyCheckoutOverviewDetails() {
        new ProductsPage(driver)
                .addProductToCartByButton(json.readTestData("products[0].name"))
                .addProductToCartByButton(json.readTestData("products[2].name"))
                .navigateToCart();

        new CartPage(driver)
                .verifyCartPageTitleIsDisplayed()
                .selectToCheckoutTheCart();

        new CheckoutInfoPage(driver)
                .verifyCheckoutInfoPageTitleIsDisplayed()
                .typeFirstname(generateRandomName())
                .typeLastname(generateRandomName())
                .typePostalCode(generateRandomPostalCode())
                .confirmCheckoutInfo();

        new CheckoutOverviewPage(driver)
                .verifyCheckoutCheckoutPageTitleIsDisplayed()
                .verifyProductIsAddedToCart(json.readTestData("products[0].name"))
                .verifyProductDescription(json.readTestData("products[0].name"), json.readTestData("products[0].description"))
                .verifyProductPrice(json.readTestData("products[0].name"), json.readTestData("products[0].price"))
                .verifyProductQuantity(json.readTestData("products[0].name"), json.readTestData("products[0].quantity"))

                .verifyProductIsAddedToCart(json.readTestData("products[2].name"))
                .verifyProductDescription(json.readTestData("products[2].name"),json.readTestData("products[2].description"))
                .verifyProductPrice(json.readTestData("products[2].name"), json.readTestData("products[2].price"))
                .verifyProductQuantity(json.readTestData("products[2].name"), json.readTestData("products[2].quantity"))

                .verifyPaymentMethod(json.readTestData("paymentMethod"))
                .verifyShippingMethod(json.readTestData("shippingMethod"))
                .verifyTotalPriceOfProducts(
                        Double.parseDouble(json.readTestData("products[0].price")) + Double.parseDouble(json.readTestData("products[2].price"))
                );

    }

    @Test(groups = {"Positive"})
    public void verifySuccessfulCheckoutCart() {
        new ProductsPage(driver)
                .addProductToCartByDragDrop(json.readTestData("products[0].name"))
                .addProductToCartByButton(json.readTestData("products[2].name"))
                .selectProduct(json.readTestData("products[1].name"));

        new ProductDetailsPage(driver)
                .verifyProductImageIsDisplayed()
                .zoomInProductImage(0.75)
                .zoomOutProductImage(0.75)
                .addProductToCart()
                .navigateToCart();

        new CartPage(driver)
                .verifyCartPageTitleIsDisplayed()
                .removeFromCartBySwipe(json.readTestData("products[0].name"))
                .selectToCheckoutTheCart();

        new CheckoutInfoPage(driver)
                .verifyCheckoutInfoPageTitleIsDisplayed()
                .typeFirstname(generateRandomName())
                .typeLastname(generateRandomName())
                .typePostalCode(generateRandomPostalCode())
                .confirmCheckoutInfo();

        new CheckoutOverviewPage(driver)
                .verifyProductIsAddedToCart(json.readTestData("products[2].name"))
                .verifyProductIsAddedToCart(json.readTestData("products[1].name"))
                .verifyTotalPriceOfProducts(
                        Double.parseDouble(json.readTestData("products[2].price")) + Double.parseDouble(json.readTestData("products[1].price")))
                .finishCartCheckOut();

        new CheckoutSuccessPage(driver)
                .verifySuccessImageIsDisplayed()
                .verifySuccessMessageIsDisplayed(json.readTestData("successMessages.checkout"));

    }

    @Test(groups = {"Negative"})
    public void verifyCheckoutAfterEmptyTheCart() {
        new ProductsPage(driver)
                .addProductToCartByButton(json.readTestData("products[0].name"))
                .addProductToCartByButton(json.readTestData("products[2].name"))
                .navigateToCart();

        new CartPage(driver)
                .verifyCartPageTitleIsDisplayed()
                .removeFromCartByButton(json.readTestData("products[0].name"))
                .removeFromCartByButton(json.readTestData("products[2].name"))
                .verifyCheckoutButtonIsRemovedFromPage();

    }

    @Test(groups = {"Negative"})
    public void verifyTotalPriceIsUpdatedInCheckoutAfterRemovingProducts() {
        new ProductsPage(driver)
                .addProductToCartByButton(json.readTestData("products[0].name"))
                .addProductToCartByButton(json.readTestData("products[2].name"))
                .navigateToCart();

        new CartPage(driver)
                .verifyCartPageTitleIsDisplayed()
                .selectToCheckoutTheCart();

        new CheckoutInfoPage(driver)
                .verifyCheckoutInfoPageTitleIsDisplayed()
                .typeFirstname(generateRandomName())
                .typeLastname(generateRandomName())
                .typePostalCode(generateRandomPostalCode())
                .confirmCheckoutInfo();

        new CheckoutOverviewPage(driver)
                .verifyProductIsAddedToCart(json.readTestData("products[0].name"))
                .verifyProductIsAddedToCart(json.readTestData("products[2].name"))
                .verifyTotalPriceOfProducts(
                        Double.parseDouble(json.readTestData("products[0].price")) + Double.parseDouble(json.readTestData("products[2].price"))
                );

        new CheckoutOverviewPage(driver)
                .removeFromCartBySwipe(json.readTestData("products[0].name"))
                .verifyProductIsRemovedFromCart(json.readTestData("products[0].name"))
                .verifyTotalPriceOfProducts(
                        Double.parseDouble(json.readTestData("products[2].price"))
                );
    }

}
