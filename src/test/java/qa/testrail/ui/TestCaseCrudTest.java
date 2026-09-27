package qa.testrail.ui;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import qa.testrail.ui.pages.SearchResultsPage;
import qa.testrail.ui.pages.TestCaseFormPage;
import qa.testrail.ui.pages.TestCasePage;
import qa.testrail.ui.pages.TestCasesPage;

import java.util.UUID;

public class TestCaseCrudTest extends BaseTest {

    private final TestCaseFormPage testCaseFormPage = new TestCaseFormPage();
    private final TestCasePage testCasePage = new TestCasePage();
    private final TestCasesPage testCasesPage = new TestCasesPage();

    @Test
    @DisplayName("C52: Изменение приоритета временного кейса")
    void changeTemporaryCasePriority() {
        String caseTitle = "auto-priority-" + UUID.randomUUID();
        String caseId = null;

        loginToTestRail();

        try {
            testCaseFormPage
                    .openAdd(6)
                    .enterTitle(caseTitle)
                    .shouldHaveSection(42)
                    .save()
                    .shouldShowTitle(caseTitle)
                    .shouldShowValue("Medium");

            caseId = testCasePage.currentCaseId();
            String createdCaseId = caseId;

            testCasePage.shouldShowCase(createdCaseId, caseTitle);

            testCaseFormPage
                    .openEdit(createdCaseId)
                    .selectPriority("High")
                    .saveEditedCase(createdCaseId)
                    .shouldShowCase(createdCaseId, caseTitle, "High")
                    .refreshPage()
                    .shouldShowCase(createdCaseId, caseTitle, "High");
        } finally {
            if (caseId != null) {
                testCaseFormPage
                        .openEdit(caseId)
                        .deleteCase()
                        .shouldBeDeleted();
            }
        }
    }

    @Test
    @DisplayName("C53: Нельзя создать кейс без обязательного заголовка")
    void cannotCreateCaseWithoutTitle() {
        loginToTestRail();

        testCasesPage
                .openSuite(6)
                .shouldBeSuiteOpened(6)
                .openAddCaseFromMenu()
                .shouldHaveEmptyTitle()
                .shouldHaveSection(42)
                .shouldHaveDisabledSave()
                .cancel()
                .shouldContainCases("C46", "C65");
    }

    @Test
    @DisplayName("C54: Поиск временного кейса по уникальному названию")
    void searchTemporaryCaseByUniqueTitle() {
        String caseTitle = "auto-search-" + UUID.randomUUID();
        String caseId = null;

        loginToTestRail();

        try {
            testCaseFormPage
                    .openAdd(6)
                    .enterTitle(caseTitle)
                    .shouldHaveSection(42)
                    .save()
                    .shouldShowTitle(caseTitle);

            caseId = testCasePage.currentCaseId();
            String createdCaseId = caseId;

            testCasePage.shouldShowCase(createdCaseId, caseTitle);

            SearchResultsPage searchResultsPage = testCasePage.search(caseTitle);

            searchResultsPage
                    .shouldContainCase(caseTitle, createdCaseId)
                    .openCase(createdCaseId)
                    .shouldBeOpened(Integer.parseInt(createdCaseId))
                    .shouldShowCase(createdCaseId, caseTitle);
        } finally {
            if (caseId != null) {
                testCaseFormPage
                        .openEdit(caseId)
                        .deleteCase()
                        .shouldBeDeleted();
            }
        }
    }

    @Test
    @DisplayName("C55: Удаление собственного временного кейса")
    void deleteOwnTemporaryTestCase() {
        String caseTitle = "auto-delete-" + UUID.randomUUID();

        loginToTestRail();

        testCaseFormPage
                .openAdd(6)
                .enterTitle(caseTitle)
                .shouldHaveSection(42)
                .save()
                .shouldShowTitle(caseTitle);

        String caseId = testCasePage.currentCaseId();
        testCasePage.shouldShowCase(caseId, caseTitle);

        testCaseFormPage
                .openEdit(caseId)
                .deleteCase()
                .shouldBeDeleted(caseTitle);

        testCasesPage
                .openSuite(6)
                .shouldBeSuiteOpened(6)
                .shouldNotContainCase(caseTitle, caseId);

        testCasePage
                .openCase(56)
                .shouldBeOpened(56)
                .shouldShowValue("API — получить проект Automation Homework по ID");
    }
}
