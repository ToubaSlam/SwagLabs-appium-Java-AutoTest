/**
 * Desired capabilities for running the myQ app under Appium.
 *
 * Values pulled from environment variables so the same config works for
 * local runs, CI, and real-device / device-farm runs. Fill in the
 * MYQ_IOS_APP / MYQ_ANDROID_APP paths (or bundle/app IDs for
 * already-installed apps) before running.
 */

const APPIUM_HOST = process.env.APPIUM_HOST || '127.0.0.1';
const APPIUM_PORT = Number(process.env.APPIUM_PORT || 4723);

const iosCapabilities = {
  platformName: 'iOS',
  'appium:automationName': 'XCUITest',
  'appium:deviceName': process.env.IOS_DEVICE_NAME || 'iPhone 15',
  'appium:platformVersion': process.env.IOS_PLATFORM_VERSION || '17.5',
  // Path to the .app / .ipa build, or set appium:bundleId to reuse an
  // already-installed app instead of installing one from `app`.
  'appium:app': process.env.MYQ_IOS_APP || '',
  'appium:bundleId': process.env.MYQ_IOS_BUNDLE_ID || 'com.chamberlain.myq',
  'appium:udid': process.env.IOS_UDID || undefined,
  'appium:noReset': process.env.NO_RESET === 'true',
  'appium:fullReset': process.env.FULL_RESET === 'true',
  'appium:newCommandTimeout': 240,
  'appium:autoAcceptAlerts': true,
  'appium:wdaLaunchTimeout': 120000,
};

const androidCapabilities = {
  platformName: 'Android',
  'appium:automationName': 'UiAutomator2',
  'appium:deviceName': process.env.ANDROID_DEVICE_NAME || 'Android Emulator',
  'appium:platformVersion': process.env.ANDROID_PLATFORM_VERSION || '14',
  // Path to the .apk build, or set appVackage/appActivity to reuse an
  // already-installed app instead of installing one from `app`.
  'appium:app': process.env.MYQ_ANDROID_APP || '',
  'appium:appPackage': process.env.MYQ_ANDROID_APP_PACKAGE || 'com.chamberlain.myq.iosandroid',
  'appium:appActivity': process.env.MYQ_ANDROID_APP_ACTIVITY || '.MainActivity',
  'appium:udid': process.env.ANDROID_UDID || undefined,
  'appium:noReset': process.env.NO_RESET === 'true',
  'appium:fullReset': process.env.FULL_RESET === 'true',
  'appium:newCommandTimeout': 240,
  'appium:autoGrantPermissions': true,
};

const wdOpts = {
  hostname: APPIUM_HOST,
  port: APPIUM_PORT,
  path: '/',
  connectionRetryTimeout: 120000,
  connectionRetryCount: 3,
  logLevel: process.env.WDIO_LOG_LEVEL || 'error',
};

module.exports = {
  ios: iosCapabilities,
  android: androidCapabilities,
  wdOpts,
};
