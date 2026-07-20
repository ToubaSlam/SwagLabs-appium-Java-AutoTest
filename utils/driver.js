const { remote } = require('webdriverio');
const { ios, android, wdOpts } = require('../config/capabilities');

/**
 * Resolves which platform to run against.
 * Priority: explicit arg > PLATFORM env var > 'android' default.
 */
function resolvePlatform(platform) {
  const resolved = (platform || process.env.PLATFORM || 'android').toLowerCase();
  if (resolved !== 'ios' && resolved !== 'android') {
    throw new Error(`Unsupported platform "${resolved}". Use "ios" or "android".`);
  }
  return resolved;
}

/**
 * Creates and returns a new Appium/webdriverio session for the given
 * platform ("ios" | "android"). Mirrors the Playwright "browser/page"
 * setup step: call this in a beforeEach/before hook.
 */
async function initDriver(platform) {
  const resolvedPlatform = resolvePlatform(platform);
  const capabilities = resolvedPlatform === 'ios' ? ios : android;

  const driver = await remote({
    ...wdOpts,
    capabilities,
  });

  driver.platform = resolvedPlatform;
  return driver;
}

/**
 * Ends the Appium session cleanly. Safe to call even if the session
 * already died (e.g. app crash) - errors are swallowed so teardown never
 * masks the real test failure.
 */
async function quitDriver(driver) {
  if (!driver) return;
  try {
    await driver.deleteSession();
  } catch (err) {
    // Session may already be gone; nothing actionable to do here.
  }
}

module.exports = {
  initDriver,
  quitDriver,
  resolvePlatform,
};
