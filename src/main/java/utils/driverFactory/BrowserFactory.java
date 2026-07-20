package utils.driverFactory;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.edge.EdgeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;

import static utils.PropertiesReader.getPropertiesValue;

public class BrowserFactory {
    static WebDriver driver;

    public static WebDriver openBrowser() {
        driver = null;

        switch (getPropertiesValue("browserType")) {
            case "Chrome":
                driver = new ChromeDriver(getChromeOptions());
                break;

            case "Firefox":
                driver = new FirefoxDriver(getFireFoxOptions());
                break;

            case "Edge":
                driver = new EdgeDriver(getEdgeOptions());
                break;
        }

        return driver;
    }

    public static WebDriver openBrowser(String browserType) {
        driver = null;

        switch (browserType) {
            case "Chrome":
                driver = new ChromeDriver(getChromeOptions());
                break;

            case "Firefox":
                driver = new FirefoxDriver(getFireFoxOptions());
                break;

            case "Edge":
                driver = new EdgeDriver(getEdgeOptions());
                break;
        }

        return driver;
    }


    private static ChromeOptions getChromeOptions() {
        ChromeOptions option = new ChromeOptions();
        if (getPropertiesValue("browserWindowMode").equalsIgnoreCase("Maximized"))
            option.addArguments("--start-maximized");
        if (getPropertiesValue("executionType").equalsIgnoreCase("Headless"))
            option.addArguments("--headless=new");

        return option;
    }

    private static EdgeOptions getEdgeOptions() {
        EdgeOptions option = new EdgeOptions();
        if (getPropertiesValue("browserWindowMode").equalsIgnoreCase("Maximized"))
            option.addArguments("--start-maximized");
        if (getPropertiesValue("executionType").equalsIgnoreCase("Headless"))
            option.addArguments("--headless=new");

        return option;
    }

    private static FirefoxOptions getFireFoxOptions() {
        FirefoxOptions option = new FirefoxOptions();
        if (getPropertiesValue("browserWindowMode").equalsIgnoreCase("Maximized"))
            option.addArguments("--start-maximized");
        if (getPropertiesValue("executionType").equalsIgnoreCase("Headless"))
            option.addArguments("--headless");

        return option;
    }

    public static void closeBrowser() {
        driver.quit();
    }
}
