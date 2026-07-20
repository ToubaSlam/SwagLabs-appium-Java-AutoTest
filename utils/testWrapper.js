/**
 * Thin wrapper around Mocha's global BDD functions so specs can be written
 * in a Playwright-like style: test.describe(...) / test.it(...) instead of
 * bare describe(...) / it(...).
 */
const test = {
  describe,
  it,
  beforeAll: before,
  afterAll: after,
  beforeEach,
  afterEach,
};

module.exports = { test };
