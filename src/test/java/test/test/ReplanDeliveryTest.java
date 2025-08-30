package test.test;

import com.codeborne.selenide.Condition;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;

import java.time.Duration;

import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.open;
import static test.data.DataGenerator.*;

public class ReplanDeliveryTest {
    String city = generateCity();
    String name = generateName();
    String phone = generatePhone();
    String date = generateDate(3, "dd.MM.yyyy");
    String changeDate = generateDate(6, "dd.MM.yyyy");

    @BeforeEach
    public void setUp() {
        open("http://localhost:9999/");
    }


    @Test
    public void testSendForm() {
        $(By.cssSelector("[data-test-id=\"city\"] input")).setValue(city);
        $(By.cssSelector(".calendar-input__custom-control input")).doubleClick().sendKeys(date);
        $(By.cssSelector("[data-test-id=\"name\"] input")).setValue(name);
        $(By.cssSelector("[data-test-id=\"phone\"] input")).setValue(phone);
        $(By.className("checkbox__box")).click();
        $(".button").click();


        $("[data-test-id=\"success-notification\"] .notification__title").shouldHave(Condition.text("Успешно!"), Duration.ofSeconds(15));
        $("[data-test-id=\"success-notification\"] .notification__content").shouldHave(Condition.text("Встреча успешно запланирована на " + date));

        $(By.cssSelector(".calendar-input__custom-control input")).doubleClick().sendKeys(changeDate);
        $(".button").click();
        $("[data-test-id=\"replan-notification\"] .notification__title").shouldHave(Condition.text("Необходимо подтверждение"), Duration.ofSeconds(15));
        $("[data-test-id=\"replan-notification\"] button").click();

        $("[data-test-id=\"success-notification\"] .notification__title").shouldHave(Condition.text("Успешно!"), Duration.ofSeconds(15));
        $("[data-test-id=\"success-notification\"] .notification__content").shouldHave(Condition.text("Встреча успешно запланирована на " + changeDate));
    }

    @Test
    public void testValidateCity() {

        $(By.cssSelector("[data-test-id=\"city\"] input")).setValue("Нью-Йорк");
        $(By.cssSelector(".calendar-input__custom-control input")).doubleClick().sendKeys(date);
        $(By.cssSelector("[data-test-id=\"name\"] input")).setValue(name);
        $(By.cssSelector("[data-test-id=\"phone\"] input")).setValue(phone);
        $(By.className("checkbox__box")).click();
        $(".button").click();
        $("[data-test-id=\"city\"].input_invalid .input__sub").shouldHave(Condition.text("Доставка в выбранный город недоступна"), Duration.ofSeconds(15));
    }

    @Test
    public void testNoCity() {

        $(By.cssSelector(".calendar-input__custom-control input")).doubleClick().sendKeys(date);
        $(By.cssSelector("[data-test-id=\"name\"] input")).setValue(name);
        $(By.cssSelector("[data-test-id=\"phone\"] input")).setValue(phone);
        $(By.className("checkbox__box")).click();
        $(".button").click();
        $("[data-test-id=\"city\"].input_invalid .input__sub").shouldHave(Condition.text("Поле обязательно для заполнения"), Duration.ofSeconds(15));
    }

    @Test
    public void testValidateDate() {

        $(By.cssSelector("[data-test-id=\"city\"] input")).setValue(city);
        $(By.cssSelector(".calendar-input__custom-control input")).doubleClick().sendKeys(Keys.BACK_SPACE);
        $(By.cssSelector("[data-test-id=\"name\"] input")).setValue(date);
        $(By.cssSelector("[data-test-id=\"phone\"] input")).setValue(phone);
        $(By.className("checkbox__box")).click();
        $(".button").click();
        $("[data-test-id=\"date\"] .input_invalid .input__sub").shouldHave(Condition.text("Неверно введена дата"), Duration.ofSeconds(15));

    }

    @Test
    public void testValidateName() {

        $(By.cssSelector("[data-test-id=\"city\"] input")).setValue(city);
        $(By.cssSelector(".calendar-input__custom-control input")).doubleClick().sendKeys(date);
        $(By.cssSelector("[data-test-id=\"name\"] input")).setValue("NameFameli?(&$$%#%$@!#~:<>>?<,[]{}");
        $(By.cssSelector("[data-test-id=\"phone\"] input")).setValue(phone);
        $(By.className("checkbox__box")).click();
        $(".button").click();
        $("[data-test-id=\"name\"].input_invalid .input__sub").shouldHave(Condition.text("Имя и Фамилия указаные неверно. Допустимы только русские буквы, пробелы и дефисы"), Duration.ofSeconds(15));

    }

    @Test
    public void testNoName() {

        $(By.cssSelector("[data-test-id=\"city\"] input")).setValue(city);
        $(By.cssSelector(".calendar-input__custom-control input")).doubleClick().sendKeys(date);
        $(By.cssSelector("[data-test-id=\"phone\"] input")).setValue(phone);
        $(By.className("checkbox__box")).click();
        $(".button").click();
        $("[data-test-id=\"name\"].input_invalid .input__sub").shouldHave(Condition.text("Поле обязательно для заполнения"), Duration.ofSeconds(15));

    }

    @Test
    public void testValidatePhone() {

        $(By.cssSelector("[data-test-id=\"city\"] input")).setValue(city);
        $(By.cssSelector(".calendar-input__custom-control input")).doubleClick().sendKeys(date);
        $(By.cssSelector("[data-test-id=\"name\"] input")).setValue(name);
        $(By.cssSelector("[data-test-id=\"phone\"] input")).setValue("+9807");
        $(By.className("checkbox__box")).click();
        $(".button").click();
        $("[data-test-id=\"success-notification\"] .notification__title").shouldHave(Condition.text("Успешно!"), Duration.ofSeconds(15));
        $("[data-test-id=\"success-notification\"] .notification__content").shouldHave(Condition.text("Встреча успешно запланирована на " + date));
    }

    @Test
    public void testNoPhone() {

        $(By.cssSelector("[data-test-id=\"city\"] input")).setValue(city);
        $(By.cssSelector(".calendar-input__custom-control input")).doubleClick().sendKeys(date);
        $(By.cssSelector("[data-test-id=\"name\"] input")).setValue(name);
        $(By.className("checkbox__box")).click();
        $(".button").click();
        $("[data-test-id=\"phone\"].input_invalid .input__sub").shouldHave(Condition.text("Поле обязательно для заполнения"), Duration.ofSeconds(15));
    }

    @Test
    public void testValidateCheckBox() {

        $(By.cssSelector("[data-test-id=\"city\"] input")).setValue(city);
        $(By.cssSelector(".calendar-input__custom-control input")).doubleClick().sendKeys(date);
        $(By.cssSelector("[data-test-id=\"name\"] input")).setValue(name);
        $(By.cssSelector("[data-test-id=\"phone\"] input")).setValue(phone);
        $(".button").click();
        $("[data-test-id=\"agreement\"].input_invalid").shouldHave(Condition.text("Я соглашаюсь с условиями обработки и использования моих персональных данных"), Duration.ofSeconds(15));
    }

}




