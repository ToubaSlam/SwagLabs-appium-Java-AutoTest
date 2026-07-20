const { test } = require('../utils/testWrapper');
const { initDriver, quitDriver } = require('../utils/driver');
const LoginPage = require('../pages/LoginPage');
const { users, expectedMessages } = require('../config/test-data');
const { ios, android } = require('../config/capabilities');

test.describe('Login', () => {
  let driver;
  let loginPage;

  test.beforeEach(async () => {
    driver = await initDriver();
    loginPage = new LoginPage(driver);
  });

  test.afterEach(async () => {
    await quitDriver(driver);
  });

  test.it('logs in successfully with valid credentials', async () => {
    await loginPage.login(users.valid.email, users.valid.password);

    const dashboard = await loginPage.waitForDashboard(20000);
    expect(await dashboard.isDisplayed()).to.be.true;
  });

  test.it('shows an error with an incorrect password', async () => {
    await loginPage.login(users.invalidPassword.email, users.invalidPassword.password);

    expect(await loginPage.isErrorDisplayed()).to.be.true;
    const message = await loginPage.getErrorMessage();
    expect(message).to.equal(expectedMessages.invalidCredentials);
  });

  test.it('shows an error for an unregistered email', async () => {
    await loginPage.login(users.unregistered.email, users.unregistered.password);

    expect(await loginPage.isErrorDisplayed()).to.be.true;
  });

  test.it('rejects a malformed email address', async () => {
    await loginPage.login(users.malformedEmail.email, users.malformedEmail.password);

    expect(await loginPage.isErrorDisplayed()).to.be.true;
  });

  test.it('redirects to login after a session timeout', async () => {
    await loginPage.login(users.valid.email, users.valid.password);
    await loginPage.waitForDashboard(20000);

    // Simulate an expired session by terminating and relaunching the app,
    // then confirm the user is bounced back to the login screen rather
    // than straight to the dashboard.
    const appId = driver.platform === 'ios'
      ? { bundleId: ios['appium:bundleId'] }
      : { appId: android['appium:appPackage'] };

    await driver.execute('mobile: terminateApp', appId);
    await driver.execute('mobile: launchApp', appId);

    expect(await loginPage.isLoginScreenDisplayed()).to.be.true;
  });
});
