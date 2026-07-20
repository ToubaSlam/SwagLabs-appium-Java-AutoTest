const BasePage = require('./BasePage');

/**
 * Page Object for the myQ login screen.
 *
 * NOTE: accessibility ids below are placeholders. Inspect the real app
 * with Appium Inspector / Xcode Accessibility Inspector and update these
 * to match the actual `accessibilityIdentifier` (iOS) / `content-desc`
 * (Android) values before running against a real build.
 */
class LoginPage extends BasePage {
  constructor(driver) {
    super(driver);

    this.selectors = {
      emailInput: this.accessibilityId('email-input'),
      passwordInput: this.accessibilityId('password-input'),
      loginButton: this.accessibilityId('login-button'),
      errorMessage: this.accessibilityId('login-error-message'),
      forgotPasswordLink: this.accessibilityId('forgot-password-link'),
      dashboardScreen: this.accessibilityId('dashboard-screen'),
    };
  }

  async enterEmail(email) {
    await this.setValue(this.selectors.emailInput, email);
  }

  async enterPassword(password) {
    await this.setValue(this.selectors.passwordInput, password);
  }

  async tapLogin() {
    await this.tap(this.selectors.loginButton);
  }

  /**
   * Full login flow: fill credentials and submit.
   */
  async login(email, password) {
    await this.enterEmail(email);
    await this.enterPassword(password);
    await this.tapLogin();
  }

  async getErrorMessage() {
    return this.getText(this.selectors.errorMessage);
  }

  async isErrorDisplayed() {
    return this.isDisplayed(this.selectors.errorMessage);
  }

  async isLoginScreenDisplayed() {
    return this.isDisplayed(this.selectors.loginButton);
  }

  async waitForDashboard(timeout) {
    return this.waitForDisplayed(this.selectors.dashboardScreen, timeout);
  }
}

module.exports = LoginPage;
