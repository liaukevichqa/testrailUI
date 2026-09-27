package qa.testrail.ui;

import com.codeborne.selenide.Configuration;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import qa.testrail.ui.config.TestConfig;
import qa.testrail.ui.pages.LoginPage;

import static com.codeborne.selenide.Selenide.closeWebDriver;

public abstract class BaseTest {

    protected static final String EMAIL = TestConfig.email();
    protected static final String PASSWORD = TestConfig.password();

    @BeforeAll
    static void setUp() {
        Configuration.baseUrl = TestConfig.baseUrl();
        Configuration.browser = "chrome";
        Configuration.browserSize = "1920x1080";
        Configuration.timeout = 10000;
        Configuration.fastSetValue = false;
        Configuration.screenshots = true;
        Configuration.savePageSource = true;
        Configuration.reportsFolder = "build/reports/tests";
    }

    protected void loginToTestRail() {
        new LoginPage()
                .openPage()
                .login(EMAIL, PASSWORD)
                .shouldBeLoggedIn();
    }

    @AfterEach
    void tearDown() {
        closeWebDriver();
    }
}
