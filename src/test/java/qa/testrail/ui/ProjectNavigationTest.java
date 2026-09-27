package qa.testrail.ui;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import qa.testrail.ui.pages.ProjectOverviewPage;
import qa.testrail.ui.pages.TestCasePage;
import qa.testrail.ui.pages.TestCasesPage;

public class ProjectNavigationTest extends BaseTest {

    private final ProjectOverviewPage projectOverviewPage = new ProjectOverviewPage();
    private final TestCasesPage testCasesPage = new TestCasesPage();
    private final TestCasePage testCasePage = new TestCasePage();

    @Test
    @DisplayName("C48: Открытие проекта Automation Homework")
    void openAutomationHomeworkProject() {
        loginToTestRail();

        projectOverviewPage
                .openProject(2)
                .shouldBeOpened(2, "Automation Homework");
    }

    @Test
    @DisplayName("C49: Открытие набора тестов")
    void openTestSuite() {
        loginToTestRail();

        testCasesPage
                .openSuite(6)
                .shouldBeSuiteOpened(6);
    }

    @Test
    @DisplayName("C50: Отображение секции Test Cases")
    void openTestCaseSection() {
        loginToTestRail();

        testCasesPage
                .openCaseInSection(46, 42)
                .shouldShowSection("Test Cases", 46, 42);
    }

    @Test
    @DisplayName("C51: Открытие карточки тест-кейса")
    void openTestCaseCard() {
        loginToTestRail();

        testCasePage
                .openCase(51)
                .shouldBeOpened(51);
    }
}
