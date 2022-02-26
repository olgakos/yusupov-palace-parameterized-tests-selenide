package olgakos.github;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.Selenide;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static com.codeborne.selenide.Condition.exist;
import static com.codeborne.selenide.Condition.text;
import static com.codeborne.selenide.Configuration.baseUrl;
import static com.codeborne.selenide.Configuration.browserSize;
import static com.codeborne.selenide.Selectors.byText;
import static com.codeborne.selenide.Selectors.withText;
import static com.codeborne.selenide.Selenide.*;

@DisplayName("Класс с базовыми тестами сайта Юсуповский дворец")
public class yusupovPalaceSimpleTests {

    @BeforeEach
    void preconditionBrowser() {
        baseUrl = "https://www.yusupov-palace.ru/ru";
        browserSize = "1920x1080";
    }

    @AfterEach
    void closeBrowser() {
        Selenide.closeWebDriver();
    }

    @Test
    @DisplayName("News's search test")
    void searchNewsTest() {
        //открыть страницу Экскурсионные программы
        Selenide.open("/programs");

        //алерт-окно про "QR-код"
        //ЕСЛИ есть алерт с текстом "необходимо предъявить QR-код":
        if ($x("//h3[contains(.,'необходимо предъявить QR-код')]").is(exist)) {
            //ТО кликни закрыть "х"
            $(".closeAnnounce").click();
        }
        //ИНАЧЕ, пропускаем и сразу:
        //проверяем заголовок страницы
        $(".logo-title").shouldHave(text("Юсуповский дворец"));
        //найти поле "поиск" + ввести запрос
        $(".search").setValue("Феликс Юсупов").pressEnter();
        //откроется страница с заголовком Результаты поиска
        $(".title").shouldHave(text("Результаты поиска"));
        //на странице пристутсвует текст:
        $("body").shouldHave(text("Имение Кореиз на Южном берегу Крыма"));
        $("body").shouldHave(text("Мать и сын Юсуповы: «Я тебя люблю с восхищением»"));

        //не сработает прави, НЕ покажет ошибку:
        //$(byText("Имение Коре1з")).isDisplayed();
    }

    @Test
    @DisplayName("Form's error test")
    void servicesFormTest() {
        //открыть страницу
        //Selenide.open("/services/presentation");
        Selenide.open("https://www.yusupov-palace.ru/ru/services/presentation");

        //ЕСЛИ есть алерт с текстом "необходимо предъявить QR-код":
        if ($x("//h3[contains(.,'необходимо предъявить QR-код')]").is(exist)) {
            //ТО кликни закрыть "х"
            $(".closeAnnounce").click();
        }
        //ИНАЧЕ, пропускаем и
        //проверяем заголовок cтраницы:

        $("#servicesFormDiv").shouldHave(text("Форма заявки"));
        $("#fio").setValue("Example Name").pressEnter();
        $("#phone").setValue("Example Tel TEXT!").pressEnter();
        $("#type").setValue("цель мероприятия").pressEnter();
        $("#guests").setValue("два").pressEnter(); // int=2
        $("[type=submit]").click(); // кнопка отправить

        //todo:
        //Ожидаемый результат: предупреждающее ОКНО с текстом "Заполните это поле / Пожалуйста, введите число"
        //Фактический результат: НЕ РАБОТАЕТ ничего из вараинтов:
        //$x("//text[contains(.,'Заполните это поле')]").should(Condition.exist); //1. не раб.
        //$(withText("Заполните это поле")).should(Condition.exist); //2. не раб.
        //$(withText("Пожалуйста, введите число")).should(Condition.exist); //3. не раб.
        //$(withText("Заполните это поле.")).should(Condition.exist); //4. не раб.
        //$(byText("Пож1луйста, введите число")).isDisplayed(); //тест зеленый, а должен быть красный
        //$x("//body[contains(.,'Пож1луйста, введите число')]").is(exist); //тест зеленый, а должен быть красный
        //$x("//text[contains(.,'Пож1луйста, введите число')]").isDisplayed(); //тест зеленый, а должен быть красный
    }
}