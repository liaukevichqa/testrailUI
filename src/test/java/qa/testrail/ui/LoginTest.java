package qa.testrail.ui;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import qa.testrail.ui.pages.LoginPage;

public class LoginTest extends BaseTest {

    private final LoginPage loginPage = new LoginPage();

    @Test
    @DisplayName("C46: Успешная авторизация")
    void successfulLogin() {
        loginPage
                .openPage()
                .enterEmail(EMAIL)
                .shouldHaveEmail(EMAIL)
                .enterPassword(PASSWORD)
                .shouldHavePassword(PASSWORD)
                .submit()
                .shouldBeLoggedIn();
    }

    @Test
    @DisplayName("C47: Авторизация с неверным паролем")
    void loginWithWrongPassword() {
        loginPage
                .openPage()
                .enterEmail(EMAIL)
                .enterPassword("WrongPassword123!")
                .submit()
                .shouldShowInvalidCredentials();
    }
}
