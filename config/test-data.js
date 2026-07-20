/**
 * Centralized test data. Values can be overridden via env vars so real
 * credentials never need to be hardcoded/committed.
 *
 * This currently only covers login-related data; device codes and
 * expected garage/doorbell states will be added alongside the
 * garage/doorbell test suites.
 */

const users = {
  valid: {
    email: process.env.MYQ_VALID_EMAIL || 'valid.user@example.com',
    password: process.env.MYQ_VALID_PASSWORD || 'ValidPassword123!',
  },
  invalidPassword: {
    email: process.env.MYQ_VALID_EMAIL || 'valid.user@example.com',
    password: 'WrongPassword!',
  },
  unregistered: {
    email: 'no.such.user@example.com',
    password: 'DoesNotMatter123!',
  },
  malformedEmail: {
    email: 'not-an-email',
    password: 'DoesNotMatter123!',
  },
};

const expectedMessages = {
  invalidCredentials: 'The email or password you entered is incorrect.',
  sessionTimeout: 'Your session has expired. Please log in again.',
};

module.exports = {
  users,
  expectedMessages,
};
