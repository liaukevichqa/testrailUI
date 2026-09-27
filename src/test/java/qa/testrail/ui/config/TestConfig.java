package qa.testrail.ui.config;

public final class TestConfig {

    private static final String DEFAULT_BASE_URL = "https://potok4.testrail.io";

    private TestConfig() {
    }

    public static String baseUrl() {
        String systemProperty = System.getProperty("baseUrl");
        if (systemProperty != null && !systemProperty.isBlank()) {
            return systemProperty;
        }

        String environmentValue = System.getenv("TESTRAIL_BASE_URL");
        if (environmentValue != null && !environmentValue.isBlank()) {
            return environmentValue;
        }

        return DEFAULT_BASE_URL;
    }

    public static String email() {
        return requiredEnvironmentVariable("TESTRAIL_EMAIL");
    }

    public static String password() {
        return requiredEnvironmentVariable("TESTRAIL_PASSWORD");
    }

    private static String requiredEnvironmentVariable(String name) {
        String value = System.getenv(name);
        if (value == null || value.isBlank()) {
            throw new IllegalStateException(name + " не задан");
        }
        return value;
    }
}
