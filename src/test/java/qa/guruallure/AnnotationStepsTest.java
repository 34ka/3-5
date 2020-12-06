package qa.guruallure;

import io.qameta.allure.*;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static com.codeborne.selenide.Condition.text;
import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selectors.byLinkText;
import static com.codeborne.selenide.Selectors.byText;
import static com.codeborne.selenide.Selenide.*;

public class AnnotationStepsTest {

    private final static String REPOSITORY = "znyaks/Product";
    private final static String USER = "znyaks";
    private final static String PASSWORD = "";
    private final static String ISSUE_NAME = "1";

    @Test
    @Feature("Issues")
    @Story("User should see issues in existing repository")
    //@Link(url = "https://github.com", name = "Тестовая ссылка") //можно выделять link здесь, а можно внутри кода
    @Owner("znyaks")
    @Severity (SeverityLevel.CRITICAL)
    @DisplayName("Наш любимый тест с аннотациями")
    public void createForIssue() {
        BaseSteps steps = new BaseSteps();

        steps.openMainPage();
        steps.authorization();
        steps.goToRepository(REPOSITORY);
        steps.goToIssues();
        steps.createToIssue(ISSUE_NAME);
        steps.shouldSeeIssueWithNumber(ISSUE_NAME);
}

    public static class BaseSteps {
        @Step("Открытие главной страницы")
        public void openMainPage() {
            open("https://github.com");
        }

        @Step("Авторизация")
        public void authorization() {
            $(".header-search-input").click();
            $("[href='/login']").click();
            $("#login_field").val(USER);
            $("#password").val(PASSWORD).pressEnter();
        }

        @Step("Переход в {name}")
        public void goToRepository(String name) {
            $(".header-search-input").sendKeys(name);
            $(".header-search-input").submit();
            $(byLinkText(name)).click();
        }

        @Step("Переход в Issues")
        public void goToIssues() {
            $("[data-tab-item='i1issues-tab']").click();
        }

        @Step("Создание нового Issue c текстом {text}")
        public void createToIssue(String text) {
            $$(byText("New issue")).find(visible).click();
            $("#issue_title").val(text);
            $("#issue_body").val("Банковская карта");
            $$(byText("Submit new issue")).find(visible).click();
        }

        @Step ("Проверка наличия созданного Issue {text}")
        public void shouldSeeIssueWithNumber(String text) {
            $(".js-issue-title").shouldHave(text(text));
        }
    }
}