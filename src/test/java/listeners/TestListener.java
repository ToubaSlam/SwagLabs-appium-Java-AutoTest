package listeners;

import io.appium.java_client.AppiumDriver;
import io.qameta.allure.Allure;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.testng.ITestListener;
import org.testng.ITestResult;
import utils.DriverManager;

import java.io.ByteArrayInputStream;

/**
 * TestNG listener wired via each XML suite's &lt;listeners&gt; block.
 * Logs suite/test/method-level events and attaches a screenshot to the
 * Allure report on failure.
 */
public class TestListener implements ITestListener {

    private static final Logger LOGGER = LogManager.getLogger(TestListener.class);

    @Override
    public void onTestStart(ITestResult result) {
        LOGGER.info("Starting test: {}", result.getMethod().getMethodName());
    }

    @Override
    public void onTestSuccess(ITestResult result) {
        LOGGER.info("Passed: {}", result.getMethod().getMethodName());
    }

    @Override
    public void onTestFailure(ITestResult result) {
        LOGGER.error("Failed: {}", result.getMethod().getMethodName(), result.getThrowable());
        attachScreenshot(result.getMethod().getMethodName());
    }

    @Override
    public void onTestSkipped(ITestResult result) {
        LOGGER.warn("Skipped: {}", result.getMethod().getMethodName());
    }

    private void attachScreenshot(String testName) {
        try {
            AppiumDriver driver = DriverManager.getDriver();
            byte[] screenshot = ((TakesScreenshot) driver).getScreenshotAs(OutputType.BYTES);
            Allure.addAttachment(testName + "-failure", new ByteArrayInputStream(screenshot));
        } catch (Exception e) {
            LOGGER.warn("Could not capture failure screenshot for {}", testName, e);
        }
    }
}
