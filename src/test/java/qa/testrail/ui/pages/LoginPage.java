package qa.testrail.ui.pages;

import com.codeborne.selenide.SelenideElement;

import static com.codeborne.selenide.Condition.*;
import static com.codeborne.selenide.Selenide.*;
import static com.codeborne.selenide.WebDriverConditions.urlContaining;

public class LoginPage {

    private final SelenideElement emailInput = $("#name");
    private final SelenideElement passwordInput = $("#password");
    private final SelenideElement loginButton = $("#button_primary");
    private final SelenideElement body = $("body");

    public LoginPage openPage() {
        open("/index.php?/auth/login");
        emailInput.shouldBe(visible);
        return this;
    }

    public LoginPage enterEmail(String email) {
        emailInput.shouldBe(visible).click();
        emailInput.sendKeys(email);
        return this;
    }

    public LoginPage enterPassword(String password) {
        passwordInput.shouldBe(visible).click();
        passwordInput.sendKeys(password);
        return this;
    }

    public LoginPage shouldHaveEmail(String email) {
        emailInput.shouldHave(value(email));
        return this;
    }

    public LoginPage shouldHavePassword(String password) {
        passwordInput.shouldHave(value(password));
        return this;
    }

    public LoginPage submit() {
        loginButton.shouldBe(enabled).click();
        return this;
    }

    public LoginPage login(String email, String password) {
        return enterEmail(email)
                .enterPassword(password)
                .submit();
    }

    public LoginPage shouldBeLoggedIn() {
        webdriver().shouldNotHave(urlContaining("/auth/login"));
        emailInput.shouldNot(exist);
        return this;
    }

    public LoginPage shouldShowInvalidCredentials() {
        body.shouldHave(text("incorrect"));
        return this;
    }
}
