package utils;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.options.UiAutomator2Options;
import io.appium.java_client.ios.IOSDriver;
import io.appium.java_client.ios.options.XCUITestOptions;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.net.MalformedURLException;
import java.net.URL;
import java.time.Duration;
import java.util.Properties;

/**
 * Owns the Appium session lifecycle. Holds the driver in a ThreadLocal so
 * TestNG suites can run tests in parallel without sessions bleeding into
 * each other.
 */
public final class DriverManager {

    private static final Logger LOGGER = LogManager.getLogger(DriverManager.class);
    private static final ThreadLocal<AppiumDriver> DRIVER = new ThreadLocal<>();

    private DriverManager() {
    }

    public static AppiumDriver getDriver() {
        AppiumDriver driver = DRIVER.get();
        if (driver == null) {
            throw new IllegalStateException("Driver has not been initialized for this thread. Call initDriver() first.");
        }
        return driver;
    }

    public static String getPlatform() {
        return ConfigReader.resolvePlatform();
    }

    public static AppiumDriver initDriver() {
        return initDriver(ConfigReader.resolvePlatform());
    }

    public static AppiumDriver initDriver(String platform) {
        Properties config = ConfigReader.get();
        URL appiumUrl = toUrl(config.getProperty("appium.url", "http://127.0.0.1:4723"));

        AppiumDriver driver = "ios".equalsIgnoreCase(platform)
                ? new IOSDriver(appiumUrl, buildIosOptions(config))
                : new AndroidDriver(appiumUrl, buildAndroidOptions(config));

        int implicitWaitSeconds = Integer.parseInt(config.getProperty("implicit.wait.seconds", "10"));
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(implicitWaitSeconds));

        DRIVER.set(driver);
        LOGGER.info("Initialized {} Appium session", platform);
        return driver;
    }

    public static void quitDriver() {
        AppiumDriver driver = DRIVER.get();
        if (driver == null) {
            return;
        }
        try {
            driver.quit();
        } catch (Exception e) {
            LOGGER.warn("Error while quitting driver session", e);
        } finally {
            DRIVER.remove();
        }
    }

    private static UiAutomator2Options buildAndroidOptions(Properties config) {
        UiAutomator2Options options = new UiAutomator2Options();
        options.setPlatformName(config.getProperty("platformName", "Android"));
        options.setDeviceName(config.getProperty("deviceName", "Android Emulator"));
        options.setPlatformVersion(config.getProperty("platformVersion"));
        options.setNewCommandTimeout(Duration.ofSeconds(
                Long.parseLong(config.getProperty("new.command.timeout.seconds", "240"))));
        options.setNoReset(Boolean.parseBoolean(config.getProperty("noReset", "false")));
        options.setFullReset(Boolean.parseBoolean(config.getProperty("fullReset", "false")));
        options.setAutoGrantPermissions(Boolean.parseBoolean(config.getProperty("autoGrantPermissions", "true")));

        String app = config.getProperty("app", "");
        if (!app.isBlank()) {
            options.setApp(app);
        } else {
            options.setAppPackage(config.getProperty("appPackage"));
            options.setAppActivity(config.getProperty("appActivity"));
        }
        return options;
    }

    private static XCUITestOptions buildIosOptions(Properties config) {
        XCUITestOptions options = new XCUITestOptions();
        options.setPlatformName(config.getProperty("platformName", "iOS"));
        options.setDeviceName(config.getProperty("deviceName", "iPhone 15"));
        options.setPlatformVersion(config.getProperty("platformVersion"));
        options.setNewCommandTimeout(Duration.ofSeconds(
                Long.parseLong(config.getProperty("new.command.timeout.seconds", "240"))));
        options.setNoReset(Boolean.parseBoolean(config.getProperty("noReset", "false")));
        options.setFullReset(Boolean.parseBoolean(config.getProperty("fullReset", "false")));
        options.setAutoAcceptAlerts(Boolean.parseBoolean(config.getProperty("autoAcceptAlerts", "true")));

        String udid = config.getProperty("udid", "");
        if (!udid.isBlank()) {
            options.setUdid(udid);
        }

        String app = config.getProperty("app", "");
        if (!app.isBlank()) {
            options.setApp(app);
        } else {
            options.setBundleId(config.getProperty("bundleId"));
        }
        return options;
    }

    private static URL toUrl(String url) {
        try {
            return new URL(url);
        } catch (MalformedURLException e) {
            throw new IllegalArgumentException("Invalid Appium server URL: " + url, e);
        }
    }
}
