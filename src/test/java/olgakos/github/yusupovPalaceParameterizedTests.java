package olgakos.github;
import com.codeborne.selenide.Selenide;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import static com.codeborne.selenide.Condition.*;
import static com.codeborne.selenide.Configuration.baseUrl;
import static com.codeborne.selenide.Configuration.browserSize;
import static com.codeborne.selenide.Selectors.byText;
import static com.codeborne.selenide.Selenide.*;

@DisplayName("Класс c параметризованными тестами для сайта Юсуповский дворец")
public class yusupovPalaceParameterizedTests {
    @BeforeEach
    void preconditionBrowser() {
        baseUrl = "https://www.yusupov-palace.ru/ru";
        browserSize = "1920x1080";
    }
    @AfterEach
    void closeBrowser() {
        Selenide.closeWebDriver();
    }

    @CsvSource(value = {
            "Феликс Юсупов| Имение Кореиз на Южном берегу Крыма",
            "Дмитрий Павлович| из великих князей Дома Романовых"
    }, delimiter = '|')
    @ParameterizedTest(name = "Проверка отображения поисковых результатов для запроса \"{0}\"")
    void searchTwoNewsTest (String testData, String expectedText){
        //открыть страницу Экскурсионные программы
        Selenide.open("/programs");
        alertWindowMethod(); //алерт-окно про "QR-код"
        //проверяем заголовок cтраницы
        $(".logo-title").shouldHave(text("Юсуповский дворец"));
        //найти поле "поиск" + ввести запрос
        $(".search").setValue(testData).pressEnter();
        //откроется страница с заголовком "Результаты поиска"
        $(".title").shouldHave(text("Результаты поиска"));
        //на странице пристутсвует текст:
        //$("body").shouldHave(text(expectedText));
        $$("body").find(text(expectedText)).shouldBe(visible);
    }

    void alertWindowMethod (){
        //ЕСЛИ есть алерт с текстом "необходимо предъявить QR-код":
        if ($x("//h3[contains(.,'необходимо предъявить QR-код')]").is(exist)) {
            //ТО кликни закрыть "х"
            $(".closeAnnounce").click();
            //ИНАЧЕ, пропускаем и
            // сразу проверка заголовка:
        }
    }
}
