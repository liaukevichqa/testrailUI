package qa.testrail.ui.pages;

import com.codeborne.selenide.SelenideElement;
import com.codeborne.selenide.WebDriverRunner;
import org.openqa.selenium.Keys;

import java.time.Duration;

import static com.codeborne.selenide.Condition.*;
import static com.codeborne.selenide.Selenide.*;
import static com.codeborne.selenide.WebDriverConditions.urlContaining;

public class TestCasePage {

    private final SelenideElement body = $("body");
    private final SelenideElement searchInput = $("input[placeholder='Search']");

    public TestCasePage openCase(int caseId) {
        open("/index.php?/cases/view/" + caseId);
        return this;
    }

    public TestCasePage shouldBeOpened(int caseId) {
        webdriver().shouldHave(urlContaining("/cases/view/" + caseId));
        body.shouldHave(text("C" + caseId));
        return this;
    }

    public TestCasePage shouldShowCase(String caseId, String caseTitle) {
        body.shouldHave(
                text("C" + caseId),
                text(caseTitle)
        );
        return this;
    }

    public TestCasePage shouldShowCase(String caseId, String caseTitle, String expectedValue) {
        body.shouldHave(
                text("C" + caseId),
                text(caseTitle),
                text(expectedValue)
        );
        return this;
    }

    public TestCasePage shouldShowTitle(String caseTitle) {
        body.shouldHave(text(caseTitle));
        return this;
    }

    public TestCasePage shouldShowValue(String value) {
        body.shouldHave(text(value));
        return this;
    }

    public String currentCaseId() {
        String currentUrl = WebDriverRunner.url();
        String marker = "/cases/view/";
        int markerIndex = currentUrl.indexOf(marker);

        if (markerIndex < 0) {
            throw new IllegalStateException("URL не содержит ID тест-кейса: " + currentUrl);
        }

        String caseId = currentUrl.substring(markerIndex + marker.length());
        int paramsIndex = caseId.indexOf('&');
        if (paramsIndex >= 0) {
            caseId = caseId.substring(0, paramsIndex);
        }

        return caseId;
    }

    public SearchResultsPage search(String query) {
        searchInput.shouldBe(visible).shouldBe(enabled).click();
        searchInput.sendKeys(Keys.CONTROL, "a");
        searchInput.sendKeys(Keys.BACK_SPACE);
        searchInput.sendKeys(query);
        searchInput.sendKeys(Keys.ENTER);
        return new SearchResultsPage();
    }

    public TestCasePage shouldBeDeleted(String caseTitle) {
        body.shouldHave(
                text("Deleted Test Case"),
                text(caseTitle)
        );
        return this;
    }

    public TestCasePage shouldBeDeleted() {
        body.shouldHave(text("Deleted Test Case"));
        return this;
    }

    public TestCasePage refreshPage() {
        refresh();
        return this;
    }

    public TestCasePage shouldShowSearchableCase(String caseTitle, String caseId) {
        body.shouldHave(text(caseTitle), Duration.ofSeconds(30));
        body.shouldHave(text("C" + caseId), Duration.ofSeconds(30));
        return this;
    }
}
