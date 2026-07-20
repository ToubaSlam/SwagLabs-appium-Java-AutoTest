package utils.listeners;

import org.testng.*;
import utils.logging_reporting.Screenshots;

import static utils.driverFactory.AppiumFactory.driver;

public class MethodListener implements IInvokedMethodListener, IConfigurationListener {

    public void beforeInvocation(IInvokedMethod method, ITestResult testResult, ITestContext context) {

    }

    public void afterInvocation(IInvokedMethod method, ITestResult testResult, ITestContext context) {

        if (method.isTestMethod()) {
            //Take Screenshot after every succeeded test
            if (ITestResult.SUCCESS == testResult.getStatus() && driver != null)
                Screenshots.captureSuccess(driver, testResult.getMethod().getMethodName());
            else if (ITestResult.FAILURE == testResult.getStatus() && driver != null)
                Screenshots.captureFailure(driver, testResult.getMethod().getMethodName());
        }
    }
}
