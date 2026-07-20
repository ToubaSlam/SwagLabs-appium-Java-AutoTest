package utilities;

import io.appium.java_client.AppiumDriver;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Optional;
import org.testng.annotations.Parameters;
import utils.DriverManager;

/**
 * Base class for all TestNG test classes: starts a fresh Appium session
 * before each test method and tears it down after, mirroring a
 * Playwright-style per-test browser/page lifecycle.
 *
 * The "platform" suite parameter lets CrossPlatform.xml run the same test
 * classes against both android and ios by declaring two &lt;test&gt; blocks
 * with different parameter values; Positive.xml/Negative.xml can omit it
 * to fall back to config.properties.
 */
public abstract class BaseTest {

    protected AppiumDriver driver;

    @Parameters({"platform"})
    @BeforeMethod(alwaysRun = true)
    public void setUp(@Optional("") String platform) {
        driver = platform.isBlank() ? DriverManager.initDriver() : DriverManager.initDriver(platform);
    }

    @AfterMethod(alwaysRun = true)
    public void tearDown() {
        DriverManager.quitDriver();
    }
}
