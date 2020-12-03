package qa.guruallure;

import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;

import static com.codeborne.selenide.Condition.text;
import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selectors.byText;
import static com.codeborne.selenide.Selenide.*;

public class GithubissueTestSelenide {

    private final static String REPOSITORY = "znyaks/123";
    private final static String USER = "znyaks";
    private final static String PASSWORD = "";
    private final static String ISSUE_NUMBER = "1";

    @Test
    public void createForIssue() {
        //Открываю главную страницу
        open("https://github.com");
        //Авторизуюсь
        $(".header-search-input").click();
        $("[href=\"/login\"]").click();
        $("#login_field").val(USER);
        $("#password").val(PASSWORD).pressEnter();
        //Перехожу в репозиторий
        $(".header-search-input").sendKeys(REPOSITORY);
        $(".header-search-input").submit();
        $(By.linkText(REPOSITORY)).click();
        //Перехожу в Issues
        $("[data-tab-item='i1issues-tab']").click();
        //Создаю новый Issue
        $$(byText("New issue")).find(visible).click();
        $("#issue_title").val(ISSUE_NUMBER);
        $("#issue_body").val("ляля-фа");
        $$(byText("Submit new issue")).find(visible).click();
        //Проверяю наличие созданного Issue
        $(".js-issue-title").shouldHave(text(ISSUE_NUMBER));
    }
}
