package utils.listeners;

import org.testng.ISuite;
import org.testng.ISuiteListener;
import utils.driverFactory.AppiumFactory;
import utils.logging_reporting.AllureReportHelper;

import java.io.File;

import static utils.PropertiesReader.loadConfigurationsIntoSystemProperties;

public class SuiteListener implements ISuiteListener {

    public void onStart (ISuite suite) {
        //Load All Properties and save it into System Properties
        loadConfigurationsIntoSystemProperties();

        //Start the Appium Server
        AppiumFactory.startAppiumServer();

        //Clear Old Allure Results before Every Run
        File file = new File("target/allure-results");
        AllureReportHelper.deleteOldFiles(file);
    }

    public void onFinish (ISuite suite) {
        // Stop Appium Server
        AppiumFactory.stopAppiumServer();

        // Open Allure Report Automatically After Run
        AllureReportHelper.autoOpenAllureReport();

    }

}
