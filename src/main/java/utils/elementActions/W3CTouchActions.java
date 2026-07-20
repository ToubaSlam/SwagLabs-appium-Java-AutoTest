package utils.elementActions;

import io.appium.java_client.AppiumDriver;
import io.qameta.allure.Step;
import org.openqa.selenium.*;
import org.openqa.selenium.interactions.*;
import org.openqa.selenium.support.ui.*;
import utils.PropertiesReader;

import java.time.Duration;
import java.util.List;

import static org.openqa.selenium.interactions.PointerInput.Kind.TOUCH;
import static org.openqa.selenium.interactions.PointerInput.Origin.viewport;
import static utils.logging_reporting.LogHelper.logErrorStep;
import static utils.logging_reporting.LogHelper.logInfoStep;


public class W3CTouchActions implements MobileActions {

    //Define Global Variables
    AppiumDriver driver;
    Wait<AppiumDriver> wait;

    //Constructor
    public W3CTouchActions(AppiumDriver driver) {
        //Assign driver from page class to Action class
        this.driver = driver;
        //define the wait type and wait configuration
        wait = new FluentWait<>(driver)
                .withTimeout(Duration.ofSeconds(30))
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

            //Find the element by locator
            WebElement element = driver.findElement(locator);

            //Find the Coordinates of Center of Element
            Point elementCenterCoordinates = getElementCenter(element);

            //Declare the finger object and sequence object
            PointerInput finger = new PointerInput(TOUCH, "finger 1");
            Sequence sequence = new Sequence(finger, 0);

            //List all moves taken by the finger to make the tap action
            sequence.addAction(finger.createPointerMove(Duration.ofMillis(0), viewport(), elementCenterCoordinates.getX(), elementCenterCoordinates.getY()));
            sequence.addAction(finger.createPointerDown(PointerInput.MouseButton.LEFT.asArg()));
            sequence.addAction(new Pause(finger, Duration.ofMillis(250)));
            sequence.addAction(finger.createPointerUp(PointerInput.MouseButton.LEFT.asArg()));

            //Run the sequence of the finger moves
            driver.perform(List.of(sequence));

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

            //Find the element by locator
            WebElement element = driver.findElement(locator);

            //Find the Coordinates of Center of Element
            Point elementCenterCoordinates = getElementCenter(element);

            //Declare the finger object and sequence object
            PointerInput finger = new PointerInput(TOUCH, "finger 1");
            Sequence sequence = new Sequence(finger, 0);

            //List all moves taken by the finger to make the double tap action
            sequence.addAction(finger.createPointerMove(Duration.ofMillis(0), viewport(), elementCenterCoordinates.getX(), elementCenterCoordinates.getY()));
            sequence.addAction(finger.createPointerDown(PointerInput.MouseButton.LEFT.asArg()));
            sequence.addAction(finger.createPointerUp(PointerInput.MouseButton.LEFT.asArg()));
            sequence.addAction(new Pause(finger, Duration.ofMillis(100)));
            sequence.addAction(finger.createPointerDown(PointerInput.MouseButton.LEFT.asArg()));
            sequence.addAction(finger.createPointerUp(PointerInput.MouseButton.LEFT.asArg()));
            driver.perform(List.of(sequence));

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

            //Find the element by locator
            WebElement element = driver.findElement(locator);

            //Find the Coordinates of Center of Element
            Point elementCenterCoordinates = getElementCenter(element);

            //Declare the finger object and sequence object
            PointerInput finger = new PointerInput(TOUCH, "finger 1");
            Sequence sequence = new Sequence(finger, 0);

            //List all moves taken by the finger to make the long tap action
            sequence.addAction(finger.createPointerMove(Duration.ofMillis(0), viewport(), elementCenterCoordinates.getX(), elementCenterCoordinates.getY()));
            sequence.addAction(finger.createPointerDown(PointerInput.MouseButton.LEFT.asArg()));
            sequence.addAction(new Pause(finger, Duration.ofMillis(3000)));
            sequence.addAction(finger.createPointerUp(PointerInput.MouseButton.LEFT.asArg()));
            driver.perform(List.of(sequence));

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

            //Find the element by locator
            WebElement startElement = driver.findElement(startLocator);
            WebElement destinationElement = driver.findElement(destinationLocator);

            //Find the Coordinates of Center of Element
            Point startElement_CenterCoordinates = getElementCenter(startElement);
            Point destinationElement_CenterCoordinates = getElementCenter(destinationElement);

            //Declare the finger object and sequence object
            PointerInput finger = new PointerInput(TOUCH, "finger 1");
            Sequence sequence = new Sequence(finger, 0);

            //List all moves taken by the finger to make the drag&drop action
            sequence.addAction(finger.createPointerMove(Duration.ofMillis(0), viewport(), startElement_CenterCoordinates.getX(), startElement_CenterCoordinates.getY()));
            sequence.addAction(finger.createPointerDown(PointerInput.MouseButton.LEFT.asArg()));
            sequence.addAction(new Pause(finger, Duration.ofMillis(250)));
            sequence.addAction(finger.createPointerMove(Duration.ofMillis(Long.parseLong(PropertiesReader.getPropertiesValue("swipeRate"))), viewport(), destinationElement_CenterCoordinates.getX(), destinationElement_CenterCoordinates.getY()));
            sequence.addAction(finger.createPointerUp(PointerInput.MouseButton.LEFT.asArg()));

            //Run the sequence of the finger moves
            driver.perform(List.of(sequence));

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

            //Find the element by locator
            WebElement element = driver.findElement(locator);
            int elementWidth = driver.findElement(locator).getSize().getWidth();

            //Find the start point of each finger
            Point elementCenterCoordinates = getElementCenter(element);
            Point start1 = new Point(elementCenterCoordinates.getX() - elementWidth / 6, elementCenterCoordinates.getY());
            Point start2 = new Point(elementCenterCoordinates.getX() + elementWidth / 6, elementCenterCoordinates.getY());

            //Find the end point of each finger
            int x = (int) (start1.getX() - zoomingPercentage * ((double) elementWidth / 3));
            int y = start1.getY();
            Point end1 = new Point(x, y);

            int a = (int) (start2.getX() + zoomingPercentage * ((double) elementWidth / 3));
            int b = start2.getY();
            Point end2 = new Point(a, b);

            //Declare the finger object and sequence object for Finger 1
            PointerInput finger1 = new PointerInput(TOUCH, "finger-1");
            Sequence sequence1 = new Sequence(finger1, 0);

            //List all moves taken by the Finger 1 to make the zoom in action
            sequence1.addAction(finger1.createPointerMove(Duration.ofMillis(0), viewport(), start1.getX(), start1.getY()));
            sequence1.addAction(finger1.createPointerDown(PointerInput.MouseButton.LEFT.asArg()));
            sequence1.addAction(new Pause(finger1, Duration.ofMillis(250)));
            sequence1.addAction(finger1.createPointerMove(Duration.ofMillis(Long.parseLong(PropertiesReader.getPropertiesValue("swipeRate"))), viewport(), end1.getX(), end1.getY()));
            sequence1.addAction(finger1.createPointerUp(PointerInput.MouseButton.LEFT.asArg()));

            //Declare the finger object and sequence object for Finger 2
            PointerInput finger2 = new PointerInput(TOUCH, "finger-2");
            Sequence sequence2 = new Sequence(finger2, 0);

            //List all moves taken by the Finger 2 to make the zoom in action
            sequence2.addAction(finger2.createPointerMove(Duration.ofMillis(0), viewport(), start2.getX(), start2.getY()));
            sequence2.addAction(finger2.createPointerDown(PointerInput.MouseButton.LEFT.asArg()));
            sequence2.addAction(new Pause(finger2, Duration.ofMillis(250)));
            sequence2.addAction(finger2.createPointerMove(Duration.ofMillis(Long.parseLong(PropertiesReader.getPropertiesValue("swipeRate"))), viewport(), end2.getX(), end2.getY()));
            sequence2.addAction(finger2.createPointerUp(PointerInput.MouseButton.LEFT.asArg()));

            //Execute the 2 Sequences
            driver.perform(List.of(sequence1, sequence2));
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

            //Find the element by locator
            WebElement element = driver.findElement(locator);
            int elementWidth = driver.findElement(locator).getSize().getWidth();

            //Find the start point of each finger
            Point elementCenterCoordinates = getElementCenter(element);
            Point start1 = new Point(elementCenterCoordinates.getX() - elementWidth / 6, elementCenterCoordinates.getY());
            Point start2 = new Point(elementCenterCoordinates.getX() + elementWidth / 6, elementCenterCoordinates.getY());

            //Find the end point of each finger
            int x = (int) (start1.getX() - zoomingPercentage * ((double) elementWidth / 3));
            int y = start1.getY();
            Point end1 = new Point(x, y);

            int a = (int) (start2.getX() + zoomingPercentage * ((double) elementWidth / 3));
            int b = start2.getY();
            Point end2 = new Point(a, b);

            //Declare the finger object and sequence object for Finger 1
            PointerInput finger1 = new PointerInput(TOUCH, "finger-1");
            Sequence sequence1 = new Sequence(finger1, 0);

            //List all moves taken by the Finger 1 to make the zoom in action
            sequence1.addAction(finger1.createPointerMove(Duration.ofMillis(0), viewport(), end1.getX(), end1.getY()));
            sequence1.addAction(finger1.createPointerDown(PointerInput.MouseButton.LEFT.asArg()));
            sequence1.addAction(new Pause(finger1, Duration.ofMillis(250)));
            sequence1.addAction(finger1.createPointerMove(Duration.ofMillis(Long.parseLong(PropertiesReader.getPropertiesValue("swipeRate"))), viewport(), start1.getX(), start1.getY()));
            sequence1.addAction(finger1.createPointerUp(PointerInput.MouseButton.LEFT.asArg()));

            //Declare the finger object and sequence object for Finger 2
            PointerInput finger2 = new PointerInput(TOUCH, "finger-2");
            Sequence sequence2 = new Sequence(finger2, 0);

            //List all moves taken by the Finger 2 to make the zoom in action
            sequence2.addAction(finger2.createPointerMove(Duration.ofMillis(0), viewport(), end2.getX(), end2.getY()));
            sequence2.addAction(finger2.createPointerDown(PointerInput.MouseButton.LEFT.asArg()));
            sequence2.addAction(new Pause(finger2, Duration.ofMillis(250)));
            sequence2.addAction(finger2.createPointerMove(Duration.ofMillis(Long.parseLong(PropertiesReader.getPropertiesValue("swipeRate"))), viewport(), start2.getX(), start2.getY()));
            sequence2.addAction(finger2.createPointerUp(PointerInput.MouseButton.LEFT.asArg()));

            //Execute the Zoom Out
            driver.perform(List.of(sequence1, sequence2));

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

            //take the Type action inside wait with lambda function

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
        //Start Point in the Center of Screen
        Point startPoint;
        Dimension screenSize = driver.manage().window().getSize();
        int x = screenSize.getWidth() / 2;
        int y = screenSize.getHeight() / 2;
        startPoint = new Point(x, y);

        //End Point based on the Scrolling Direction
        Point endPoint;
        int a = 0;
        int b = 0;

        switch (direction) {
            case "Up":
                a = x;
                b = y + screenSize.getHeight() / 3;
                break;

            case "Down":
                a = x;
                b = y - screenSize.getHeight() / 3;
                ;
                break;

            case "Left":
                a = x + screenSize.getWidth() / 3;
                b = y;
                break;

            case "Right":
                a = x - screenSize.getWidth() / 3;
                b = y;
                break;

            case null:
                a = x;
                b = y;
                break;

            default:
                logErrorStep("Direction value is not correct, it must be Up or Down or Left or Right or null");
                break;
        }

        endPoint = new Point(a, b);

        //Declare the finger object and sequence object
        PointerInput finger = new PointerInput(TOUCH, "finger 1");
        Sequence sequence = new Sequence(finger, 0);

        //List all moves taken by the finger to make the swipe action
        sequence.addAction(finger.createPointerMove(Duration.ofMillis(0), viewport(), startPoint.getX(), startPoint.getY()));
        sequence.addAction(finger.createPointerDown(PointerInput.MouseButton.LEFT.asArg()));
        sequence.addAction(new Pause(finger, Duration.ofMillis(250)));
        sequence.addAction(finger.createPointerMove(Duration.ofMillis(Long.parseLong(PropertiesReader.getPropertiesValue("swipeRate"))), viewport(), endPoint.getX(), endPoint.getY()));
        sequence.addAction(finger.createPointerUp(PointerInput.MouseButton.LEFT.asArg()));

        //Run the sequence of the finger moves
        driver.perform(List.of(sequence));
    }

    public void singleSwipeIntoElement(String direction, By scrollableElement) {
        //Start Point is the Center of Element
        Point startPoint;
        startPoint = getElementCenter(driver.findElement(scrollableElement));
        int x = startPoint.getX();
        int y = startPoint.getY();

        //End Point based on the Scrolling Direction
        Dimension elementSize = driver.findElement(scrollableElement).getSize();
        int elementHeight = elementSize.getHeight();
        int elementWidth = elementSize.getWidth();

        Point endPoint;
        int a = 0;
        int b = 0;

        switch (direction) {
            case "Up" -> {
                a = x;
                b = y + elementHeight / 3;
            }
            case "Down" -> {
                a = x;
                b = y - elementHeight / 3;
            }
            case "Left" -> {
                a = x + elementWidth / 3;
                b = y;
            }
            case "Right" -> {
                a = x - elementWidth / 3;
                b = y;
            }
            case null -> {
                a = x;
                b = y;
            }
            default ->
                    logErrorStep("Direction value is not correct, it must be Up or Down or Left or Right or null", new Exception());
        }

        endPoint = new Point(a, b);

        //Declare the finger object and sequence object
        PointerInput finger = new PointerInput(TOUCH, "finger 1");
        Sequence sequence = new Sequence(finger, 0);

        //List all moves taken by the finger to make the swipe action
        sequence.addAction(finger.createPointerMove(Duration.ofMillis(0), viewport(), startPoint.getX(), startPoint.getY()));
        sequence.addAction(finger.createPointerDown(PointerInput.MouseButton.LEFT.asArg()));
        sequence.addAction(new Pause(finger, Duration.ofMillis(250)));
        sequence.addAction(finger.createPointerMove(Duration.ofMillis(Long.parseLong(PropertiesReader.getPropertiesValue("swipeRate"))), viewport(), endPoint.getX(), endPoint.getY()));
        sequence.addAction(finger.createPointerUp(PointerInput.MouseButton.LEFT.asArg()));

        //Run the sequence of the finger moves
        driver.perform(List.of(sequence));

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
