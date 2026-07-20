package utils.listeners;

import org.testng.IAnnotationTransformer;
import org.testng.ITestResult;
import org.testng.Reporter;
import org.testng.TestListenerAdapter;
import org.testng.annotations.ITestAnnotation;
import utils.RetryAnalyzer;

import java.lang.reflect.Constructor;
import java.lang.reflect.Method;

public class TestListener extends TestListenerAdapter implements IAnnotationTransformer {

    public void onTestFailure(ITestResult result) {
        Reporter.setCurrentTestResult(result);

        if (result.getMethod().getRetryAnalyzer(result).retry(result))
            result.setStatus(ITestResult.SKIP);

        Reporter.setCurrentTestResult(null);
    }

    @Override
    public void transform(ITestAnnotation annotation, Class testClass, Constructor testConstructor, Method testMethod) {
        annotation.setRetryAnalyzer(RetryAnalyzer.class);
    }
}
