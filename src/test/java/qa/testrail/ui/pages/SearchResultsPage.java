package qa.testrail.ui.pages;

import java.time.Duration;

import static com.codeborne.selenide.Condition.*;
import static com.codeborne.selenide.Selenide.*;

public class SearchResultsPage {

    public SearchResultsPage shouldContainCase(String caseTitle, String caseId) {
        $("body").shouldHave(text(caseTitle), Duration.ofSeconds(30));
        $("body").shouldHave(text("C" + caseId), Duration.ofSeconds(30));
        return this;
    }

    public TestCasePage openCase(String caseId) {
        $$("a[href*='/cases/view/" + caseId + "']")
                .findBy(visible)
                .shouldBe(visible, Duration.ofSeconds(30))
                .click();
        return new TestCasePage();
    }
}
