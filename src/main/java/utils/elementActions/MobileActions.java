package utils.elementActions;

import org.openqa.selenium.*;

public interface MobileActions {

    //Actions Methods

    void tap(By locator);

    void tap(By locator, String direction);


    void doubleTap(By locator);

    void doubleTap(By locator, String direction);


    void tapAndHold(By locator);

    void tapAndHold(By locator, String direction);


    void dragAndDrop(By startLocator, By destinationLocator);

    void dragAndDrop(By startLocator, By destinationLocator, String direction);


    void zoomIn(By locator, double zoomingPercentage);

    void zoomIn(By locator, double zoomingPercentage, String direction);


    void zoomOut(By locator, double zoomingPercentage);

    void zoomOut(By locator, double zoomingPercentage, String direction);


    void type(By locator, String value);

    void type(By locator, String value, String direction);


    String readTextFromElement(By locator);

    String readTextFromElement(By locator, String direction);


    boolean isElementDisplayed(By locator);

    boolean isElementDisplayed(By locator, String direction);


    boolean isElementNotDisplayed(By locator, String direction);


    void singleSwipeIntoScreen(String direction);

    void singleSwipeIntoElement(String direction, By scrollableElement);


    void scrollUntilElementDisplayed(By targetLocator, String direction);

    void scrollUntilElementDisplayed(By targetLocator, String direction, By scrollableElement);


    Point getElementCenter(WebElement element);
}
