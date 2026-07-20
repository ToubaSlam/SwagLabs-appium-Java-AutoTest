package utils.driverFactory;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.InteractsWithApps;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.options.UiAutomator2Options;
import io.appium.java_client.ios.IOSDriver;
import io.appium.java_client.ios.options.XCUITestOptions;
import io.appium.java_client.service.local.AppiumDriverLocalService;
import io.appium.java_client.service.local.AppiumServiceBuilder;
import io.appium.java_client.service.local.flags.GeneralServerFlag;
import io.qameta.allure.Step;

import java.io.File;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.time.Duration;

import static utils.logging_reporting.LogHelper.logErrorStep;
import static utils.logging_reporting.LogHelper.logInfoStep;
import static utils.PropertiesReader.getPropertiesValue;

public class AppiumFactory {
    public static AppiumDriver driver;
    private static AppiumDriverLocalService service;

    @Step
    public static AppiumDriver openApp(String platformType) {
        try {
            driver = null;

            switch (platformType) {
                case "Android":
                    driver = new AndroidDriver(getAppiumServerURL(), getAndroidCapabilities());
                    logInfoStep("Starting the App [%s]".formatted(getPropertiesValue("android_appPackage")));
                    break;

                case "IOS":
                    driver = new IOSDriver(getAppiumServerURL(), getIOSCapabilities());
                    logInfoStep("Starting the App [%s]".formatted(getPropertiesValue("ios_bundleId")));
                    break;
            }

            return driver;
        } catch (Exception e) {
            logErrorStep("Failed to start the App", e);
            return null;
        }
    }

    @Step
    private static UiAutomator2Options getAndroidCapabilities() {
        try {
            UiAutomator2Options option = new UiAutomator2Options();
            //Generic Capabilities
            option.setCapability("noReset", Boolean.parseBoolean(getPropertiesValue("noReset")));
            option.setCapability("fullReset", Boolean.parseBoolean(getPropertiesValue("fullReset")));
            option.setCapability("autoGrantPermissions", Boolean.parseBoolean(getPropertiesValue("autoGrantPermission")));

            option.setCapability("uiautomator2ServerInstallTimeout", 180000);
            option.setNewCommandTimeout(Duration.ofSeconds(120000));
            option.setCapability("appium:adbExecTimeout", 120000);

            // Platform /OS Capabilities
            option.setPlatformName("Android");
            option.setPlatformVersion(getPropertiesValue("android_platformVersion"));
            option.setAutomationName(getPropertiesValue("android_automationDriver"));

            // Device Capabilities
            option.setDeviceName(getPropertiesValue("android_deviceName"));
            option.setUdid(getPropertiesValue("android_deviceID"));

            // Application Capabilities
            option.setAppPackage(getPropertiesValue("android_appPackage"));
            option.setAppActivity(getPropertiesValue("android_appActivity"));
            option.setApp(System.getProperty("user.dir") + "/src/test/resources/Apps/" + getPropertiesValue("android_appInstaller"));

            logInfoStep("Getting Android Capabilities.....");
            return option;
        } catch (Exception e) {
            logErrorStep("Failed to Get Android Capabilities", e);
            return null;
        }

    }

    @Step
    private static XCUITestOptions getIOSCapabilities() {
        try {
            XCUITestOptions option = new XCUITestOptions();
            //Generic Capabilities
            option.setCapability("noReset", Boolean.parseBoolean(getPropertiesValue("noReset")));
            option.setCapability("fullReset", Boolean.parseBoolean(getPropertiesValue("fullReset")));
            option.setAutoAcceptAlerts(Boolean.parseBoolean(getPropertiesValue("autoGrantPermission")));

            option.setCapability("usePrebuiltWDA", false);
            option.setCapability("appium:useNewWDA", true);
            option.setCapability("wdaStartupRetries", 2);
            option.setCapability("wdaStartupRetryInterval", 5000);

            // Platform /OS Capabilities
            option.setPlatformName("IOS");
            option.setPlatformVersion(getPropertiesValue("ios_platformVersion"));
            option.setAutomationName(getPropertiesValue("ios_automationDriver"));

            // Device Capabilities
            option.setDeviceName(getPropertiesValue("ios_deviceName"));
            option.setUdid(getPropertiesValue("ios_deviceID"));

            // Application Capabilities
            option.setBundleId(getPropertiesValue("ios_bundleId"));
            option.setApp(System.getProperty("user.dir") + "/src/test/resources/Apps/" + getPropertiesValue("ios_appInstaller"));

            logInfoStep("Getting IOS Capabilities.....");
            return option;
        } catch (Exception e) {
            logErrorStep("Failed to Get IOS Capabilities", e);
            return null;
        }

    }

    @Step
    private static URL getAppiumServerURL() {
        try {
            return new URI(getPropertiesValue("appiumServerURL")).toURL();

        } catch (MalformedURLException | URISyntaxException e) {
            throw new RuntimeException(e);
        }
    }

    @Step
    public static void closeApp() {
        try {
            if (driver instanceof AndroidDriver mydriver) {
                String appID = (String) mydriver.getCapabilities().getCapability("appium:appPackage");
                ((InteractsWithApps) driver).terminateApp(appID);

                mydriver.quit();
                logInfoStep("Closing the App [%s]".formatted(getPropertiesValue("android_appPackage")));
            } else if (driver instanceof IOSDriver mydriver) {
                mydriver.quit();
                logInfoStep("Closing the App [%s]".formatted(getPropertiesValue("ios_bundleId")));

            }

        } catch (Exception e) {
            logErrorStep("Failed to Close the App", e);
        }

    }

    public static void startAppiumServer() {
        String withoutHttp = getPropertiesValue("appiumServerURL").split("://", 2)[1];

        try {
            if (System.getProperty("os.name").contains("Windows")) {
                service = new AppiumServiceBuilder()
                        .withAppiumJS(new File(System.getenv("APPDATA") + "/npm/node_modules/appium/build/lib/main.js"))
                        .withIPAddress(withoutHttp.split(":", 2)[0])
                        .usingPort(Integer.parseInt(withoutHttp.split(":", 2)[1]))
                        .withArgument(() -> "--allow-cors")
                        .withArgument(() -> "--use-drivers", getPropertiesValue("android_automationDriver").toLowerCase())
                        .withArgument(GeneralServerFlag.LOG_LEVEL, "error")
                        //        .withArgument(()->"--use-plugins","relaxed-caps")
                        .withLogFile(new File("AppiumServerLogs" + File.separator + "Server.log"))
                        .build();

            } else if (System.getProperty("os.name").contains("Mac")) {
                service = new AppiumServiceBuilder()
                        .withAppiumJS(new File("/usr/local/lib/node_modules/appium/build/lib/main.js"))
                        .withIPAddress(withoutHttp.split(":", 2)[0])
                        .usingPort(Integer.parseInt(withoutHttp.split(":", 2)[1]))
                        .withArgument(() -> "--allow-cors")
                        .withArgument(() -> "--use-drivers", getPropertiesValue("ios_automationDriver").toLowerCase())
                        .withLogFile(new File("AppiumServerLogs" + File.separator + "Server.log"))
                        .withArgument(GeneralServerFlag.LOG_LEVEL, "error")
                        //    .withArgument(()->"--use-plugins","relaxed-caps")
                        .build();
            }

            service.start();
            logInfoStep("Starting Appium Server.....................");


        } catch (Exception e) {
            logErrorStep("Failed to Start Appium Server", e);
        }
    }

    public static void stopAppiumServer() {
        try {
            if (service.isRunning()) {
                service.stop();
                logInfoStep("Stopping Appium Server..................");
            }
        } catch (Exception e) {
            logErrorStep("Failed to Stop Appium Server");
        }
    }
}
