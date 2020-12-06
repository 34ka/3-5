package qa.guruallure;

import com.codeborne.selenide.logevents.SelenideLogger;
import io.qameta.allure.selenide.AllureSelenide;
import org.junit.jupiter.api.Test;

import static com.codeborne.selenide.Condition.text;
import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selectors.byLinkText;
import static com.codeborne.selenide.Selectors.byText;
import static com.codeborne.selenide.Selenide.*;
import static qa.guruallure.NamedBy.css;

public class GithubissueTestSelenideListener {

    private final static String REPOSITORY = "znyaks/Product";
    private final static String USER = "znyaks";
    private final static String PASSWORD = "";
    private final static String ISSUE_NAME = "1";

    @Test
    public void testGitHub() {
        SelenideLogger.addListener("Allure", new AllureSelenide());
        //Листенер удобен, когда нет времени заниматься разметкой 100, 500 тестов
        //Не всегда нужно заниматься разметкой

        open("https://github.com");
        $(css(".header-search-input").as("Поисковая строка")).click();
        $("[href='/login']").click();
        $("#login_field").val(USER);
        $("#password").val(PASSWORD).pressEnter();
        $(".header-search-input").sendKeys(REPOSITORY);
        $(".header-search-input").submit();
        $(byLinkText(REPOSITORY)).click();
        $("[data-tab-item='i1issues-tab']").click();
        $$(byText("New issue")).find(visible).click();
        $("#issue_title").val(ISSUE_NAME);
        $("#issue_body").val("Банковская карта");
        $$(byText("Submit new issue")).find(visible).click();
        $(".js-issue-title").shouldHave(text(ISSUE_NAME));
    }
}
