package utilities;

import org.testng.IRetryAnalyzer;
import org.testng.ITestResult;

/**
 * Retries a failed test up to MAX_RETRIES times before letting it report
 * as failed, to absorb flaky-device/simulator hiccups. Attach via
 * @Test(retryAnalyzer = RetryAnalyzer.class) or wire globally through an
 * IAnnotationTransformer if every test should retry.
 */
public class RetryAnalyzer implements IRetryAnalyzer {

    private static final int MAX_RETRIES = 2;
    private int attempt = 0;

    @Override
    public boolean retry(ITestResult result) {
        if (attempt < MAX_RETRIES) {
            attempt++;
            return true;
        }
        return false;
    }
}
