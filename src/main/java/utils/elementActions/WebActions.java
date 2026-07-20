package utils.elementActions;

import org.openqa.selenium.By;
import org.openqa.selenium.ElementNotInteractableException;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.FluentWait;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.Wait;

import java.time.Duration;


public class WebActions {

    //Define Global Variables
    WebDriver driver;
    Wait<WebDriver> wait;

    //Constructor
    public WebActions(WebDriver driver) {
        //Assign driver from page class to Action class
        this.driver = driver;
        //define the wait type and wait configuration
        wait = new FluentWait<>(driver)
                .withTimeout(Duration.ofSeconds(30))
                .pollingEvery(Duration.ofMillis(300))
                .ignoring(ElementNotInteractableException.class);
    }

    //ActionsMethods
    public void clickWithWait(By locator) {
        //wait until the element is present in DOM or HTML
        wait.until(ExpectedConditions.presenceOfElementLocated(locator));
        //wait until the element is visible on page on GUI
        wait.until(ExpectedConditions.visibilityOfElementLocated(locator));
        //wait until the element is enabled or clickable on page
        wait.until(ExpectedConditions.elementToBeClickable(locator));

        //take the click action inside wait with lambda function
        wait.until(d -> {
            driver.findElement(locator).click();
            return true;
        });
    }

    public void typingWithWait(By locator, String value) {
        //wait until the element is present in DOM or HTML
        wait.until(ExpectedConditions.presenceOfElementLocated(locator));
        //wait until the element is visible on page on GUI
        wait.until(ExpectedConditions.visibilityOfElementLocated(locator));
        //wait until the element is enabled or clickable on page
        wait.until(ExpectedConditions.elementToBeClickable(locator));

        //take the Type action inside wait with lambda function
        wait.until(d -> {
            driver.findElement(locator).sendKeys(value);
            return true;
        });
    }

    public String readTextFromElement(By locator) {
        //wait until the element is present in DOM or HTML
        wait.until(ExpectedConditions.presenceOfElementLocated(locator));
        //wait until the element is visible on page on GUI
        wait.until(ExpectedConditions.visibilityOfElementLocated(locator));
        //wait until the element is enabled or clickable on page
        wait.until(ExpectedConditions.elementToBeClickable(locator));
        //take the Type action inside wait with lambda function
        return wait.until(d -> driver.findElement(locator).getText());
    }

    public void selectFromDropDownByText(By locator, String text) {
        //wait until the element is present in DOM or HTML
        wait.until(ExpectedConditions.presenceOfElementLocated(locator));
        //wait until the element is visible on page on GUI
        wait.until(ExpectedConditions.visibilityOfElementLocated(locator));
        //wait until the element is enabled or clickable on page
        wait.until(ExpectedConditions.elementToBeClickable(locator));

        Select select = new Select(driver.findElement(locator));

        select.selectByVisibleText(text);

    }

    public boolean isElementDisplayed(By locator) {
        //take the isDisplayed action inside wait with lambda function
        try {
            return wait.until(d -> driver.findElement(locator).isDisplayed());
        } catch (TimeoutException e) {
            return false;
        }
    }

    public void acceptAlert() {
        //wait until Alert is displayed
        wait.until(ExpectedConditions.alertIsPresent());
        //switch to alert window then click on ok button
        driver.switchTo().alert().accept();
    }

    public void dismissAlert() {
        //wait until Alert is displayed
        wait.until(ExpectedConditions.alertIsPresent());
        //switch to alert window then click on cancel button
        driver.switchTo().alert().dismiss();
    }

    public String readTextFromAlert() {
        //wait until Alert is displayed
        wait.until(ExpectedConditions.alertIsPresent());
        //switch to alert window then read text from alert
        return driver.switchTo().alert().getText();
    }

}
