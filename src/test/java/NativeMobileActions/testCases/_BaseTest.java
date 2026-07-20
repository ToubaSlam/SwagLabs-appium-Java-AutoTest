package NativeMobileActions.testCases;

import io.appium.java_client.AppiumDriver;
import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Optional;
import org.testng.annotations.Parameters;
import utils.JsonReader;
import utils.driverFactory.AppiumFactory;

import static utils.PropertiesReader.getPropertiesValue;

public class _BaseTest {
    //Objects and Variables
    AppiumDriver driver;
    JsonReader json = new JsonReader("src/test/resources/TestData/testData.json");

    @Parameters({"platformName"})
    @BeforeMethod (alwaysRun = true)
    public void beforeMethod(@Optional("") String platform) {
        // Open the Application
        if (platform == null || platform.isBlank())
            platform = getPropertiesValue("platformType");

        this.driver = AppiumFactory.openApp(platform);

    }

    @AfterMethod (alwaysRun = true)
    public void afterMethod(ITestResult result) {

        //Close the Application
        AppiumFactory.closeApp();
    }
}
