package qa.guruallure;

import io.qameta.allure.*;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;

import static com.codeborne.selenide.Condition.text;
import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selectors.byText;
import static com.codeborne.selenide.Selenide.*;

public class AnnotationStepsTest {

    private final static String REPOSITORY = "znyaks/123";
    private final static String USER = "znyaks";
    private final static String PASSWORD = "git rm -rf --cached";
    private final static String ISSUE_NUMBER = "1";

    @Test
    @DisplayName("Наш любимый тест с аннотациями")
    @Feature("Issues")
    @Story("User should see issues in existing repository")
    //@Link(url = "https://github.com", name = "Тестинг") //можно выделять link здесь, а можно внутри кода
    @Owner("znyaks")
    @Severity (SeverityLevel.CRITICAL)
    public void createForIssue() {
        BaseSteps steps = new BaseSteps();

        steps.openMainPage();
        steps.authorization();
        steps.goToRepository(REPOSITORY);
        steps.goToIssues();
        steps.createToIssue(ISSUE_NUMBER);
        steps.shouldSeeIssueWithNumber(ISSUE_NUMBER);
}

    public static class BaseSteps {
        @Step("Открываю главную страницу")
        public void openMainPage() {
            open("https://github.com");
        }

        @Step("Авторизуюсь")
        public void authorization() {
            $(".header-search-input").click();
            $("[href=\"/login\"]").click();
            $("#login_field").val(USER);
            $("#password").val(PASSWORD).pressEnter();
        }

        @Step("Перехожу в {name}")
            public void goToRepository(final String name) {
            $(".header-search-input").sendKeys(name);
            $(".header-search-input").submit();
            $(By.linkText(name)).click();
        }

        @Step("Перехожу в Issues")
                public void goToIssues() {
        $("[data-tab-item='i1issues-tab']").click();
        }

        @Step("Создаю новый Issue c текстом {textt}")
                public void createToIssue(final String textt) {
            $$(byText("New issue")).find(visible).click();
            $("#issue_title").val(textt);
            $("#issue_body").val("ляля-фа");
            $$(byText("Submit new issue")).find(visible).click();
        }

        @Step ("Проверяю наличие созданного Issue {textt}")
                public void shouldSeeIssueWithNumber(final String textt) {
            $(".js-issue-title").shouldHave(text(textt));
        }
    }
}