package actions;

import io.appium.java_client.AppiumBy;
import io.appium.java_client.AppiumDriver;
import org.openqa.selenium.By;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.PointerInput;
import org.openqa.selenium.interactions.Sequence;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import utils.ConfigReader;
import utils.DriverManager;

import java.time.Duration;
import java.util.Collections;
import java.util.List;

/**
 * Base of the Fluent Page Object Model: every Page class extends
 * BaseActions&lt;ConcretePage&gt; so action methods can return `this` typed
 * as the concrete page, enabling chains like:
 *
 *   loginPage.enterEmail(email).enterPassword(password).tapLogin();
 *
 * All locators are accessibility ids, resolved cross-platform via
 * AppiumBy.accessibilityId (maps to accessibilityIdentifier on iOS and
 * content-desc/resource-id on Android).
 */
public abstract class BaseActions<T extends BaseActions<T>> {

    protected final AppiumDriver driver;
    private final int explicitWaitSeconds;

    protected BaseActions() {
        this.driver = DriverManager.getDriver();
        this.explicitWaitSeconds = Integer.parseInt(
                ConfigReader.get("explicit.wait.seconds", "20"));
    }

    @SuppressWarnings("unchecked")
    private T self() {
        return (T) this;
    }

    protected By accessibilityId(String id) {
        return AppiumBy.accessibilityId(id);
    }

    protected WebElement waitForVisibility(By locator) {
        return waitForVisibility(locator, explicitWaitSeconds);
    }

    protected WebElement waitForVisibility(By locator, int timeoutSeconds) {
        return new WebDriverWait(driver, Duration.ofSeconds(timeoutSeconds))
                .until(ExpectedConditions.visibilityOfElementLocated(locator));
    }

    public T tap(By locator) {
        waitForVisibility(locator).click();
        return self();
    }

    public T type(By locator, String text) {
        WebElement element = waitForVisibility(locator);
        element.clear();
        element.sendKeys(text);
        return self();
    }

    public String getText(By locator) {
        return waitForVisibility(locator).getText();
    }

    public boolean isDisplayed(By locator) {
        return isDisplayed(locator, explicitWaitSeconds);
    }

    public boolean isDisplayed(By locator, int timeoutSeconds) {
        try {
            return waitForVisibility(locator, timeoutSeconds).isDisplayed();
        } catch (Exception e) {
            return false;
        }
    }

    /**
     * Smart scroll: swipes up repeatedly until the target element is found
     * and visible, or the max attempts are exhausted. Cross-platform via
     * W3C pointer actions (no dependence on the retired touch-action API).
     */
    public T scrollToElement(By locator) {
        return scrollToElement(locator, 5);
    }

    public T scrollToElement(By locator, int maxSwipes) {
        for (int attempt = 0; attempt < maxSwipes; attempt++) {
            List<WebElement> matches = driver.findElements(locator);
            if (!matches.isEmpty() && matches.get(0).isDisplayed()) {
                return self();
            }
            swipeUp();
        }
        // Final check so a genuinely-missing element still fails with a
        // clear "not found" error rather than silently continuing.
        waitForVisibility(locator, 2);
        return self();
    }

    public T swipeUp() {
        Dimension size = driver.manage().window().getSize();
        int startX = size.width / 2;
        int startY = (int) (size.height * 0.8);
        int endY = (int) (size.height * 0.2);
        swipe(startX, startY, startX, endY);
        return self();
    }

    public T swipeDown() {
        Dimension size = driver.manage().window().getSize();
        int startX = size.width / 2;
        int startY = (int) (size.height * 0.2);
        int endY = (int) (size.height * 0.8);
        swipe(startX, startY, startX, endY);
        return self();
    }

    private void swipe(int startX, int startY, int endX, int endY) {
        PointerInput finger = new PointerInput(PointerInput.Kind.TOUCH, "finger");
        Sequence swipe = new Sequence(finger, 0);
        swipe.addAction(finger.createPointerMove(Duration.ZERO, PointerInput.Origin.viewport(), startX, startY));
        swipe.addAction(finger.createPointerDown(PointerInput.MouseButton.LEFT.asArg()));
        swipe.addAction(finger.createPointerMove(Duration.ofMillis(500), PointerInput.Origin.viewport(), endX, endY));
        swipe.addAction(finger.createPointerUp(PointerInput.MouseButton.LEFT.asArg()));
        driver.perform(Collections.singletonList(swipe));
    }

    public void pause(long millis) {
        try {
            Thread.sleep(millis);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }
}
