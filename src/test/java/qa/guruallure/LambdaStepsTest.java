package qa.guruallure;

import io.qameta.allure.Allure;
import org.junit.jupiter.api.Test;

import static com.codeborne.selenide.Condition.text;
import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selectors.byLinkText;
import static com.codeborne.selenide.Selectors.byText;
import static com.codeborne.selenide.Selenide.*;
import static io.qameta.allure.Allure.step;

public class LambdaStepsTest {

    private final static String REPOSITORY = "znyaks/Product";
    private final static String USER = "znyaks";
    private final static String PASSWORD = "";
    private final static String ISSUE_NAME = "1";
    String url = "https://github.com";

    @Test
    public void createForIssue() {
        Allure.feature("Issues");
        Allure.story("Моя любимая история");
        Allure.parameter("Repository", REPOSITORY);

        step("Открытие главной страницы", () -> {
            open(url);
            Allure.link("Тестовая ссылка", url);
        });
        step("Авторизация", () -> {
            $(".header-search-input").click();
            $("[href='/login']").click();
            $("#login_field").val(USER);
            $("#password").val(PASSWORD).pressEnter();
        });
        step("Переход в репозиторий", (step) -> {
            step.parameter("name", REPOSITORY);
            $(".header-search-input").sendKeys(REPOSITORY);
            $(".header-search-input").submit();
            $(byLinkText(REPOSITORY)).click();
        });
        step("Переход в Issues", () -> {
            $("[data-tab-item='i1issues-tab']").click();
        });
        step("Создание нового Issue", () -> {
            $$(byText("New issue")).find(visible).click();
            $("#issue_title").val(ISSUE_NAME);
            $("#issue_body").val("Банковская карта");
            $$(byText("Submit new issue")).find(visible).click();
        });
        step("Проверка наличия созданного Issue", (step) -> {
            step.parameter("text", ISSUE_NAME);
            $(".js-issue-ttle").shouldHave(text(ISSUE_NAME));
        });
    }
}