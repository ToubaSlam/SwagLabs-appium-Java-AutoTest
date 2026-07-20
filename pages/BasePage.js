const DEFAULT_TIMEOUT = 15000;

/**
 * Base class for all Page Objects. Wraps common Appium/webdriverio
 * interactions behind small, readable methods (tap, swipe, waitForElement)
 * so page objects read like user actions rather than raw driver calls.
 *
 * All locators are expected to be accessibility ids, passed in the
 * `~accessibilityId` webdriverio shorthand form.
 */
class BasePage {
  constructor(driver) {
    this.driver = driver;
  }

  /**
   * Builds an accessibility id selector, e.g. accessibilityId('login-button')
   * -> '~login-button'.
   */
  accessibilityId(id) {
    return `~${id}`;
  }

  async waitForElement(selector, timeout = DEFAULT_TIMEOUT) {
    const element = await this.driver.$(selector);
    await element.waitForExist({ timeout });
    return element;
  }

  async waitForDisplayed(selector, timeout = DEFAULT_TIMEOUT) {
    const element = await this.waitForElement(selector, timeout);
    await element.waitForDisplayed({ timeout });
    return element;
  }

  async tap(selector, timeout = DEFAULT_TIMEOUT) {
    const element = await this.waitForDisplayed(selector, timeout);
    await element.click();
    return element;
  }

  async setValue(selector, value, timeout = DEFAULT_TIMEOUT) {
    const element = await this.waitForDisplayed(selector, timeout);
    await element.setValue(value);
    return element;
  }

  async getText(selector, timeout = DEFAULT_TIMEOUT) {
    const element = await this.waitForDisplayed(selector, timeout);
    return element.getText();
  }

  async isDisplayed(selector, timeout = DEFAULT_TIMEOUT) {
    try {
      const element = await this.waitForElement(selector, timeout);
      return await element.isDisplayed();
    } catch (err) {
      return false;
    }
  }

  async isExisting(selector) {
    const element = await this.driver.$(selector);
    return element.isExisting();
  }

  /**
   * Swipes from one screen coordinate to another using W3C pointer
   * actions, since Appium removed the old touchAction JSONWP swipe helper.
   */
  async swipe({ startX, startY, endX, endY, duration = 500 }) {
    await this.driver.performActions([
      {
        type: 'pointer',
        id: 'finger1',
        parameters: { pointerType: 'touch' },
        actions: [
          { type: 'pointerMove', duration: 0, x: startX, y: startY },
          { type: 'pointerDown', button: 0 },
          { type: 'pointerMove', duration, x: endX, y: endY },
          { type: 'pointerUp', button: 0 },
        ],
      },
    ]);
    await this.driver.releaseActions();
  }

  async swipeUp() {
    const { width, height } = await this.driver.getWindowRect();
    await this.swipe({
      startX: Math.round(width / 2),
      startY: Math.round(height * 0.8),
      endX: Math.round(width / 2),
      endY: Math.round(height * 0.2),
    });
  }

  async swipeDown() {
    const { width, height } = await this.driver.getWindowRect();
    await this.swipe({
      startX: Math.round(width / 2),
      startY: Math.round(height * 0.2),
      endX: Math.round(width / 2),
      endY: Math.round(height * 0.8),
    });
  }

  async pause(ms) {
    await this.driver.pause(ms);
  }
}

module.exports = BasePage;
