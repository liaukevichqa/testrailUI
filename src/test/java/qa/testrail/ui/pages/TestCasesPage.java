package qa.testrail.ui.pages;

import static com.codeborne.selenide.Condition.*;
import static com.codeborne.selenide.Selenide.*;
import static com.codeborne.selenide.WebDriverConditions.urlContaining;

public class TestCasesPage {

    public TestCasesPage openSuite(int suiteId) {
        open("/index.php?/suites/view/" + suiteId
                + "&group_by=cases:section_id"
                + "&group_order=asc"
                + "&display=tree"
                + "&display_deleted_cases=0");
        return this;
    }

    public TestCasesPage shouldBeSuiteOpened(int suiteId) {
        webdriver().shouldHave(urlContaining("/suites/view/" + suiteId));
        $("body").shouldBe(visible);
        return this;
    }

    public TestCasesPage openCaseInSection(int caseId, int groupId) {
        open("/index.php?/cases/view/" + caseId
                + "&group_by=cases:section_id"
                + "&group_order=asc"
                + "&display=tree"
                + "&display_deleted_cases=0"
                + "&group_id=" + groupId);
        return this;
    }

    public TestCasesPage shouldShowSection(String sectionName, int caseId, int groupId) {
        webdriver().shouldHave(urlContaining("group_id=" + groupId));
        $("body").shouldHave(
                text("In section " + sectionName + "."),
                text("C" + caseId),
                text(sectionName)
        );
        return this;
    }

    public TestCaseFormPage openAddCaseFromMenu() {
        $("a.dynamic_add.dropdownLink")
                .shouldBe(visible)
                .click();

        $("#navigation-test-case")
                .shouldBe(visible)
                .click();

        webdriver().shouldHave(urlContaining("/cases/add/"));
        return new TestCaseFormPage();
    }

    public TestCasesPage shouldNotContainCase(String caseTitle, String caseId) {
        $("body").shouldNotHave(
                text(caseTitle),
                text("C" + caseId)
        );
        return this;
    }

    public TestCasesPage shouldContainCases(String firstCaseId, String secondCaseId) {
        $("body").shouldHave(
                text(firstCaseId),
                text(secondCaseId)
        );
        return this;
    }
}
