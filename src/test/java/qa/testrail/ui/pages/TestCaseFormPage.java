package qa.testrail.ui.pages;

import com.codeborne.selenide.SelenideElement;
import org.openqa.selenium.Keys;

import static com.codeborne.selenide.Condition.*;
import static com.codeborne.selenide.Selenide.*;
import static com.codeborne.selenide.WebDriverConditions.urlContaining;

public class TestCaseFormPage {

    private final SelenideElement titleInput = $("#title");
    private final SelenideElement sectionSelect = $("#section_id");
    private final SelenideElement saveButton = $("#accept");
    private final SelenideElement disabledSaveButton = $("#acceptDisabled");
    private final SelenideElement prioritySelect = $("#priority_id_chosen");
    private final SelenideElement cancelButton = $("[data-testid='addEditCaseCancelButton']");

    public TestCaseFormPage openAdd(int suiteId) {
        open("/index.php?/cases/add/" + suiteId + "&referer=suites/view/" + suiteId);
        titleInput.shouldBe(visible);
        return this;
    }

    public TestCaseFormPage openEdit(String caseId) {
        open("/index.php?/cases/edit/" + caseId);
        webdriver().shouldHave(urlContaining("/cases/edit/" + caseId));
        return this;
    }

    public TestCaseFormPage enterTitle(String title) {
        titleInput.shouldBe(visible).shouldBe(enabled).click();
        titleInput.sendKeys(Keys.CONTROL, "a");
        titleInput.sendKeys(Keys.BACK_SPACE);
        titleInput.sendKeys(title);
        titleInput.sendKeys(Keys.TAB);
        titleInput.shouldHave(value(title));
        return this;
    }

    public TestCaseFormPage shouldHaveEmptyTitle() {
        titleInput.shouldBe(visible).shouldHave(exactValue(""));
        return this;
    }

    public TestCaseFormPage shouldHaveSection(int sectionId) {
        sectionSelect.shouldHave(value(String.valueOf(sectionId)));
        return this;
    }

    public TestCaseFormPage shouldHaveDisabledSave() {
        disabledSaveButton.should(exist).shouldBe(disabled);
        return this;
    }

    public TestCasePage save() {
        saveButton.shouldBe(visible).shouldBe(enabled).click();
        webdriver().shouldHave(urlContaining("/cases/view/"));
        return new TestCasePage();
    }

    public TestCaseFormPage selectPriority(String priority) {
        prioritySelect.shouldBe(visible).click();
        $$("#priority_id_chosen .chosen-results li")
                .findBy(exactText(priority))
                .shouldBe(visible)
                .click();
        return this;
    }

    public TestCasePage saveEditedCase(String caseId) {
        saveButton.shouldBe(visible).shouldBe(enabled).click();
        webdriver().shouldHave(urlContaining("/cases/view/" + caseId));
        return new TestCasePage();
    }

    public TestCasesPage cancel() {
        cancelButton.shouldBe(visible).click();
        webdriver().shouldHave(urlContaining("/suites/view/"));
        return new TestCasesPage();
    }

    public TestCasePage deleteCase() {
        $$("a")
                .findBy(exactText("Delete Test Case"))
                .shouldBe(visible)
                .click();

        $$("[data-testid='deleteCaseDialogActionDefault']")
                .findBy(visible)
                .shouldBe(enabled)
                .click();

        return new TestCasePage();
    }
}
