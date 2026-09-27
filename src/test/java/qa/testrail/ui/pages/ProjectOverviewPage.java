package qa.testrail.ui.pages;

import static com.codeborne.selenide.Condition.text;
import static com.codeborne.selenide.Selenide.*;
import static com.codeborne.selenide.WebDriverConditions.urlContaining;

public class ProjectOverviewPage {

    public ProjectOverviewPage openProject(int projectId) {
        open("/index.php?/projects/overview/" + projectId);
        return this;
    }

    public ProjectOverviewPage shouldBeOpened(int projectId, String projectName) {
        webdriver().shouldHave(urlContaining("/projects/overview/" + projectId));
        $("body").shouldHave(text(projectName));
        return this;
    }
}
