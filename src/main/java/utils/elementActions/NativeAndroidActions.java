package utils.elementActions;

import io.appium.java_client.AppiumDriver;
import io.qameta.allure.Step;
import org.openqa.selenium.*;
import org.openqa.selenium.remote.RemoteWebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.FluentWait;
import org.openqa.selenium.support.ui.Wait;

import java.time.Duration;
import java.util.HashMap;
import java.util.Map;

import static utils.logging_reporting.LogHelper.logErrorStep;
import static utils.logging_reporting.LogHelper.logInfoStep;


public class NativeAndroidActions implements MobileActions {

    //Define Global Variables
    AppiumDriver driver;
    Wait<AppiumDriver> wait;

    //Constructor
    public NativeAndroidActions(AppiumDriver driver) {
        //Assign driver from page class to Action class
        this.driver = driver;
        //define the wait type and wait configuration
        wait = new FluentWait<>(driver)
                .withTimeout(Duration.ofSeconds(20))
                .pollingEvery(Duration.ofMillis(300))
                .ignoring(ElementNotInteractableException.class)
                .ignoring(NoSuchElementException.class)
                .ignoring(StaleElementReferenceException.class);
    }

    //Actions Methods
    @Step
    public void tap(By locator) {
        try {
            //wait until the element is visible on page on GUI
            wait.until(ExpectedConditions.visibilityOfElementLocated(locator));
            //wait until the element is enabled or clickable on page
            wait.until(ExpectedConditions.elementToBeClickable(locator));

            //perform the tap action
            Map<String, Object> param = new HashMap<>();
            Dimension elementSize = driver.findElement(locator).getSize();
            param.put("elementId", ((RemoteWebElement) driver.findElement(locator)).getId());
            param.put("x", elementSize.getWidth() / 2);
            param.put("y", elementSize.getHeight() / 2);
            driver.executeScript("mobile: clickGesture", param);

            logInfoStep("Tapping on Element [%s]".formatted(locator));
        } catch (Exception e) {
            logErrorStep("Failed to Tap on Element [%s]".formatted(locator), e);
        }

    }

    public void tap(By locator, String direction) {
        //Scroll into screen until the element is visible into viewPort
        scrollUntilElementDisplayed(locator, direction);

        tap(locator);
    }

    @Step
    public void doubleTap(By locator) {
        try {
            //wait until the element is visible on page on GUI
            wait.until(ExpectedConditions.visibilityOfElementLocated(locator));
            //wait until the element is enabled or clickable on page
            wait.until(ExpectedConditions.elementToBeClickable(locator));

            //perform the double tap action
            Map<String, Object> param = new HashMap<>();
            Dimension elementSize = driver.findElement(locator).getSize();
            param.put("elementId", ((RemoteWebElement) driver.findElement(locator)).getId());
            param.put("x", elementSize.getWidth() / 2);
            param.put("y", elementSize.getHeight() / 2);
            driver.executeScript("mobile: doubleClickGesture", param);

            logInfoStep("Double Tapping on Element [%s]".formatted(locator));
        } catch (Exception e) {
            logErrorStep("Failed to Double Tap on Element [%s]".formatted(locator), e);
        }

    }

    public void doubleTap(By locator, String direction) {
        //Scroll into screen until the element is visible into viewPort
        scrollUntilElementDisplayed(locator, direction);

        doubleTap(locator);
    }

    @Step
    public void tapAndHold(By locator) {
        try {
            //wait until the element is visible on page on GUI
            wait.until(ExpectedConditions.visibilityOfElementLocated(locator));
            //wait until the element is enabled or clickable on page
            wait.until(ExpectedConditions.elementToBeClickable(locator));

            //perform the tapAndHold action
            Map<String, Object> param = new HashMap<>();
            Dimension elementSize = driver.findElement(locator).getSize();
            param.put("elementId", ((RemoteWebElement) driver.findElement(locator)).getId());
            param.put("duration", 2000);
            param.put("x", elementSize.getWidth() / 2);
            param.put("y", elementSize.getHeight() / 2);
            driver.executeScript("mobile: longClickGesture", param);

            logInfoStep("Tapping and Hold on Element [%s]".formatted(locator));
        } catch (Exception e) {
            logErrorStep("Failed to Tap and Hop on Element [%s]".formatted(locator), e);
        }

    }

    public void tapAndHold(By locator, String direction) {
        //Scroll into screen until the element is visible into viewPort
        scrollUntilElementDisplayed(locator, direction);

        tapAndHold(locator);
    }

    @Step
    public void dragAndDrop(By startLocator, By destinationLocator) {
        try {
            //wait until the element is visible on page on GUI
            wait.until(ExpectedConditions.visibilityOfElementLocated(startLocator));
            //wait until the element is enabled or clickable on page
            wait.until(ExpectedConditions.elementToBeClickable(startLocator));


            //perform the drag and drop action
            Dimension elementSize = driver.findElement(startLocator).getSize();
            Point endPoint = getElementCenter(driver.findElement(destinationLocator));
            Map<String, Object> param = new HashMap<>();
            param.put("elementId", ((RemoteWebElement) driver.findElement(startLocator)).getId());
            param.put("startX", elementSize.getWidth() / 2);
            param.put("startY", elementSize.getHeight() / 2);
            param.put("endX", endPoint.getX());
            param.put("endY", endPoint.getY());
            driver.executeScript("mobile: dragGesture", param);

            logInfoStep("Drag the Element [%s] into Element [%s]".formatted(startLocator, destinationLocator));
        } catch (Exception e) {
            logErrorStep("Failed to Drag the Element [%s] into Element [%s]".formatted(startLocator, destinationLocator), e);
        }
    }

    public void dragAndDrop(By startLocator, By destinationLocator, String direction) {
        //Scroll into screen until the element is visible into viewPort
        scrollUntilElementDisplayed(startLocator, direction);

        dragAndDrop(startLocator, destinationLocator);
    }

    @Step
    public void zoomIn(By locator, double zoomingPercentage) {
        try {
            //wait until the element is visible on page on GUI
            wait.until(ExpectedConditions.visibilityOfElementLocated(locator));
            //wait until the element is enabled or clickable on page
            wait.until(ExpectedConditions.elementToBeClickable(locator));

            //perform the zoom in action
            Dimension elementSize = driver.findElement(locator).getSize();

            Map<String, Object> param = new HashMap<>();
            param.put("elementId", ((RemoteWebElement) driver.findElement(locator)).getId());
            param.put("percent", zoomingPercentage);
            param.put("width", elementSize.getWidth());
            param.put("height", elementSize.getHeight());
            driver.executeScript("mobile: pinchOpenGesture", param);

            logInfoStep("Zooming In into the Element [%s]".formatted(locator));
        } catch (Exception e) {
            logErrorStep("Failed to Zoom In into the Element [%s]".formatted(locator), e);
        }
    }

    public void zoomIn(By locator, double zoomingPercentage, String direction) {
        //Scroll into screen until the element is visible into viewPort
        scrollUntilElementDisplayed(locator, direction);

        zoomIn(locator, zoomingPercentage);
    }

    @Step
    public void zoomOut(By locator, double zoomingPercentage) {
        try {
            //wait until the element is visible on page on GUI
            wait.until(ExpectedConditions.visibilityOfElementLocated(locator));
            //wait until the element is enabled or clickable on page
            wait.until(ExpectedConditions.elementToBeClickable(locator));

            //perform the zoom out action
            Map<String, Object> param = new HashMap<>();

            param.put("elementId", ((RemoteWebElement) driver.findElement(locator)).getId());
            param.put("percent", zoomingPercentage);
            Dimension elementSize = driver.findElement(locator).getSize();
            param.put("width", elementSize.getWidth());
            param.put("height", elementSize.getHeight());
            driver.executeScript("mobile: pinchCloseGesture", param);

            logInfoStep("Zooming Out into the Element [%s]".formatted(locator));
        } catch (Exception e) {
            logErrorStep("Failed to Zoom Out into the Element [%s]".formatted(locator), e);
        }
    }

    public void zoomOut(By locator, double zoomingPercentage, String direction) {
        //Scroll into screen until the element is visible into viewPort
        scrollUntilElementDisplayed(locator, direction);

        zoomOut(locator, zoomingPercentage);
    }

    @Step
    public void type(By locator, String value) {
        try {
            //wait until the element is visible on page on GUI
            wait.until(ExpectedConditions.visibilityOfElementLocated(locator));
            //wait until the element is enabled or clickable on page
            wait.until(ExpectedConditions.elementToBeClickable(locator));

            wait.until(d -> {
                driver.findElement(locator).sendKeys(value);

                //if the value is not typed correctly, clear and retype again
                //don't apply it in passwords
                while(!driver.findElement(locator).getText().equalsIgnoreCase(value) && !driver.findElement(locator).getText().contains("•")){
                    driver.findElement(locator).clear();
                    driver.findElement(locator).sendKeys(value);
                }
                return true;
            });

            logInfoStep("Typing text [%s] on Element [%s]".formatted(value, locator));
        } catch (Exception e) {
            logErrorStep("Failed to Type text [%s] on Element [%s]".formatted(value, locator), e);
        }

    }

    public void type(By locator, String value, String direction) {
        //Scroll into screen until the element is visible into viewPort
        scrollUntilElementDisplayed(locator, direction);

        type(locator, value);
    }

    @Step
    public String readTextFromElement(By locator) {

        try {
            //wait until the element is visible on page on GUI
            wait.until(ExpectedConditions.visibilityOfElementLocated(locator));
            //wait until the element is enabled or clickable on page
            wait.until(ExpectedConditions.elementToBeClickable(locator));
            //take the Type action inside wait with lambda function
            String value = wait.until(d -> driver.findElement(locator).getText());

            logInfoStep("Reading text [%s] from Element [%s]".formatted(value, locator));
            return value;
        } catch (Exception e) {
            logErrorStep("Failed to Read text from Element [%s]".formatted(locator), e);
            return null;
        }
    }

    public String readTextFromElement(By locator, String direction) {
        //Scroll into screen until the element is visible into viewPort
        scrollUntilElementDisplayed(locator, direction);

        return readTextFromElement(locator);
    }

    @Step
    public boolean isElementDisplayed(By locator) {
        //take the isDisplayed action inside wait with lambda function
        try {
            wait.until(ExpectedConditions.visibilityOfElementLocated(locator));
            logInfoStep("The Element [%s] is Displayed".formatted(locator));
            return true;
        } catch (Exception e) {
            logErrorStep("The Element [%s] is Not Displayed".formatted(locator));
            return false;
        }
    }

    @Step
    public boolean isElementDisplayed(By locator, String direction) {

        try {

            scrollUntilElementDisplayed(locator,direction);
            wait.until(ExpectedConditions.visibilityOfElementLocated(locator));
            logInfoStep("the Element [%s] is Displayed".formatted(locator));
            return true;
        }catch (TimeoutException e){
            logErrorStep("the Element [%s] is Not Displayed".formatted(locator));
            return false;
        }
    }

    @Step
    public boolean isElementNotDisplayed(By locator, String direction) {

        try {
            String[] previousPageSource = {""};
            final int[] flag = {0};
            wait.until(f -> {
                if (flag[0] == 1) {
                    // If the element is not found, perform a scroll action using Mobile Swipe of XCUITest
                    if (direction.equalsIgnoreCase("Vertical"))
                        singleSwipeIntoScreen("Down");
                    else if (direction.equalsIgnoreCase("Horizontal"))
                        singleSwipeIntoScreen("Right");
                }

                String currentPageSource = driver.getPageSource();
                if (currentPageSource.equalsIgnoreCase(previousPageSource[0]))
                    // The page source hasn't changed, so we've reached the bottom
                    throw new TimeoutException();
                else
                    previousPageSource[0] = currentPageSource;

                flag[0] = 1;
                return driver.findElement(locator).isDisplayed();
            });
            logErrorStep("The Element [%s] is Displayed".formatted(locator));
            return false;
        } catch (TimeoutException e) {
            // Scroll in Opposite Direction till Element is Displayed into View or till Timeout or till Reach end of Page
            try {
                String[] previousPageSource = {""};
                wait.until(f -> {
                    if (direction.equalsIgnoreCase("Vertical"))
                        singleSwipeIntoScreen("Up");
                    else if (direction.equalsIgnoreCase("Horizontal"))
                        singleSwipeIntoScreen("Left");

                    String currentPageSource = driver.getPageSource();
                    if (currentPageSource.equalsIgnoreCase(previousPageSource[0]))
                        // The page source hasn't changed, so we've reached the bottom
                        throw new TimeoutException();
                    else
                        previousPageSource[0] = currentPageSource;

                    return driver.findElement(locator).isDisplayed();
                });
                logErrorStep("The Element [%s] is Displayed".formatted(locator));
                return false;
            } catch (TimeoutException f) {
                logInfoStep("The Element [%s] is Not Displayed".formatted(locator));

                return true;
            }
        }
    }

    public void singleSwipeIntoScreen(String direction) {
        int screenHeight = driver.manage().window().getSize().getHeight();
        int screenWidth = driver.manage().window().getSize().getWidth();

        //Execute the Single Swipe Action
        Map<String, Object> param = new HashMap<>();
        param.put("percent", 1);
        param.put("width", screenWidth * 0.75);
        param.put("height", screenHeight * 0.5);
        param.put("top", screenHeight * 0.2);
        param.put("left", screenWidth * 0.2);

        switch (direction) {
            case "Down" -> param.put("direction", "down");
            case "Up" -> param.put("direction", "up");
            case "Left" -> param.put("direction", "left");
            case "Right" -> param.put("direction", "right");
            case null -> {
            }
            default ->
                    logErrorStep("Direction value is not correct, it must be Up or Down or Left or Right or null", new Exception());
        }

        driver.executeScript("mobile: scrollGesture", param);

    }

    public void singleSwipeIntoElement(String direction, By scrollableElement) {

        Map<String, Object> param = new HashMap<>();
        param.put("percent", 1);
        param.put("elementId", ((RemoteWebElement) driver.findElement(scrollableElement)).getId());

        switch (direction) {
            case "Down" -> param.put("direction", "down");
            case "Up" -> param.put("direction", "up");
            case "Left" -> param.put("direction", "left");
            case "Right" -> param.put("direction", "right");
            case null -> {
            }
            default ->
                    logErrorStep("Direction value is not correct, it must be Up or Down or Left or Right or null", new Exception());
        }

        driver.executeScript("mobile: scrollGesture", param);

    }

    public void scrollUntilElementDisplayed(By targetLocator, String direction) {
        try {
            if (!driver.findElement(targetLocator).isDisplayed())
                throw new NotFoundException();

        } catch (Exception e) {
            try {
                String[] previousPageSource = {""};
                wait.until(d -> {

                    if (direction.equalsIgnoreCase("Vertical"))
                        singleSwipeIntoScreen("Down");
                    else if (direction.equalsIgnoreCase("Horizontal"))
                        singleSwipeIntoScreen("Right");

                    String currentPageSource = driver.getPageSource();
                    if (currentPageSource.equalsIgnoreCase(previousPageSource[0]))
                        // The page source hasn't changed, so we've reached the bottom
                        throw new TimeoutException();
                    else
                        previousPageSource[0] = currentPageSource;

                    return driver.findElement(targetLocator).isDisplayed();
                });

                logInfoStep("Scrolling into Screen in direction [%s] until Element [%s] is Displayed".formatted(direction, targetLocator));
                Thread.sleep(1000);
            }catch (TimeoutException | InterruptedException f){
                try{
                    //Scroll in Opposite Direction
                    String[] previousPageSource = {""};
                    wait.until(g -> {
                        // If the element is not found, perform a scroll action using Mobile Swipe of AndroidUIAutomator2
                        if (direction.equalsIgnoreCase("Vertical"))
                            singleSwipeIntoScreen("Up");
                        else if (direction.equalsIgnoreCase("Horizontal"))
                            singleSwipeIntoScreen("Left");

                        String currentPageSource = driver.getPageSource();
                        if (currentPageSource.equalsIgnoreCase(previousPageSource[0]))
                        // The page source hasn't changed, so we've reached the bottom
                        {
                            throw new TimeoutException();
                        } else
                            previousPageSource[0] = currentPageSource;
                        return driver.findElement(targetLocator).isDisplayed();
                    });

                    logInfoStep("Scrolling into Screen in direction [%s] until Element [%s] is Displayed".formatted(direction, targetLocator));
                    Thread.sleep(1000);
                }catch (TimeoutException | InterruptedException g){
                    logErrorStep("Failed to Scroll into Screen in direction [%s] until Element [%s] is Displayed".formatted(direction, targetLocator), g);
                }
            }
        }
    }

    public void scrollUntilElementDisplayed(By targetLocator, String direction, By scrollableElement) {
        try {
            if (!driver.findElement(targetLocator).isDisplayed())
                throw new NotFoundException();

        } catch (Exception e) {
            try {
                String[] previousPageSource = {""};
                wait.until(d -> {

                    if (direction.equalsIgnoreCase("Vertical"))
                        singleSwipeIntoElement("Down",scrollableElement);
                    else if (direction.equalsIgnoreCase("Horizontal"))
                        singleSwipeIntoElement("Right",scrollableElement);

                    String currentPageSource = driver.getPageSource();
                    if (currentPageSource.equalsIgnoreCase(previousPageSource[0]))
                        // The page source hasn't changed, so we've reached the bottom
                        throw new TimeoutException();
                    else
                        previousPageSource[0] = currentPageSource;

                    return driver.findElement(targetLocator).isDisplayed();
                });

                logInfoStep("Scrolling into Screen in direction [%s] until Element [%s] is Displayed".formatted(direction, targetLocator));
                Thread.sleep(1000);
            }catch (TimeoutException | InterruptedException f){
                try{
                    //Scroll in Opposite Direction
                    String[] previousPageSource = {""};
                    wait.until(g -> {
                        // If the element is not found, perform a scroll action using Mobile Swipe of AndroidUIAutomator2
                        if (direction.equalsIgnoreCase("Vertical"))
                            singleSwipeIntoElement("Up",scrollableElement);
                        else if (direction.equalsIgnoreCase("Horizontal"))
                            singleSwipeIntoElement("Left",scrollableElement);

                        String currentPageSource = driver.getPageSource();
                        if (currentPageSource.equalsIgnoreCase(previousPageSource[0]))
                        // The page source hasn't changed, so we've reached the bottom
                        {
                            throw new TimeoutException();
                        } else
                            previousPageSource[0] = currentPageSource;
                        return driver.findElement(targetLocator).isDisplayed();
                    });

                    logInfoStep("Scrolling into Screen in direction [%s] until Element [%s] is Displayed".formatted(direction, targetLocator));
                    Thread.sleep(1000);
                }catch (TimeoutException | InterruptedException g){
                    logErrorStep("Failed to Scroll into Screen in direction [%s] until Element [%s] is Displayed".formatted(direction, targetLocator), g);
                }
            }
        }

    }

    public Point getElementCenter(WebElement element) {
        Point location = element.getLocation();
        Dimension size = element.getSize();
        int x = (size.getWidth() / 2) + location.getX();
        int y = (size.getHeight() / 2) + location.getY();
        return new Point(x, y);
    }

}
