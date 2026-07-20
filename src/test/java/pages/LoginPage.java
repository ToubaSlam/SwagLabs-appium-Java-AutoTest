package pages;

import actions.BaseActions;
import org.openqa.selenium.By;

/**
 * Page Object for the myQ login screen.
 *
 * NOTE: accessibility ids below are placeholders. Inspect the real app
 * with Appium Inspector / Xcode Accessibility Inspector and update these
 * to match the actual accessibilityIdentifier (iOS) / content-desc
 * (Android) values before running against a real build.
 */
public class LoginPage extends BaseActions<LoginPage> {

    private final By emailInput = accessibilityId("email-input");
    private final By passwordInput = accessibilityId("password-input");
    private final By loginButton = accessibilityId("login-button");
    private final By errorMessage = accessibilityId("login-error-message");
    private final By forgotPasswordLink = accessibilityId("forgot-password-link");
    private final By dashboardScreen = accessibilityId("dashboard-screen");

    public LoginPage enterEmail(String email) {
        return type(emailInput, email);
    }

    public LoginPage enterPassword(String password) {
        return type(passwordInput, password);
    }

    public LoginPage tapLogin() {
        return tap(loginButton);
    }

    /** Full login flow, chainable: loginPage.login(email, password).isErrorDisplayed(); */
    public LoginPage login(String email, String password) {
        return enterEmail(email).enterPassword(password).tapLogin();
    }

    public String getErrorMessage() {
        return getText(errorMessage);
    }

    public boolean isErrorDisplayed() {
        return isDisplayed(errorMessage);
    }

    public boolean isLoginScreenDisplayed() {
        return isDisplayed(loginButton);
    }

    public boolean isDashboardDisplayed(int timeoutSeconds) {
        return isDisplayed(dashboardScreen, timeoutSeconds);
    }

    public LoginPage tapForgotPassword() {
        return tap(forgotPasswordLink);
    }
}
