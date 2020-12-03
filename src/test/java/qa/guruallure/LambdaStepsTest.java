package qa.guruallure;

import io.qameta.allure.Allure;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;

import static com.codeborne.selenide.Condition.text;
import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selectors.byText;
import static com.codeborne.selenide.Selenide.*;
import static io.qameta.allure.Allure.step;

public class LambdaStepsTest {

    private final static String REPOSITORY = "znyaks/123";
    private final static String USER = "znyaks";
    private final static String PASSWORD = "";
    private final static String ISSUE_NUMBER = "1";

    @Test
    public void createForIssue() {
        Allure.feature("Issues");
        Allure.story("Моя любимая история");
        Allure.parameter("Repository", REPOSITORY);

        step("Открываю главную страницу", () -> {
            String url = "https://github.com";
            open(url);
            Allure.link("Тестинг", url);
        });
        step("Авторизуюсь", () -> {
            $(".header-search-input").click();
            $("[href=\"/login\"]").click();
            $("#login_field").val(USER);
            $("#password").val(PASSWORD).pressEnter();
        });
        step("Перехожу в репозиторий", (step) -> {
            step.parameter("name", REPOSITORY);
            $(".header-search-input").sendKeys(REPOSITORY);
            $(".header-search-input").submit();
            $(By.linkText(REPOSITORY)).click();
        });
        step("Перехожу в Issues", () -> {
            $("[data-tab-item='i1issues-tab']").click();
        });
        step("Создаю новый Issue", () -> {
            $$(byText("New issue")).find(visible).click();
            $("#issue_title").val(ISSUE_NUMBER);
            $("#issue_body").val("ляля-фа");
            $$(byText("Submit new issue")).find(visible).click();
        });
        step("Проверяю наличие созданного Issue", (step) -> {
            step.parameter("textt", ISSUE_NUMBER);
            $(".js-issue-ttle").shouldHave(text(ISSUE_NUMBER));
        });
        System.out.println();
    }
}
