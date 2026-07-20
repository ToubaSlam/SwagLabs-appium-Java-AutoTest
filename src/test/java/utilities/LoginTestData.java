package utilities;

/** POJO mapping of TestData/login.json. */
public class LoginTestData {

    public Credentials validUser;
    public Credentials invalidPasswordUser;
    public Credentials unregisteredUser;
    public Credentials malformedEmailUser;
    public ExpectedMessages expectedMessages;

    public static class Credentials {
        public String email;
        public String password;
    }

    public static class ExpectedMessages {
        public String invalidCredentials;
        public String sessionTimeout;
    }
}
