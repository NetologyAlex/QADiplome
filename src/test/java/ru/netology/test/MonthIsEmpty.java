package ru.netology.test;

import com.codeborne.selenide.Configuration;
import com.codeborne.selenide.logevents.SelenideLogger;
import io.qameta.allure.selenide.AllureSelenide;
import lombok.val;
import org.junit.jupiter.api.*;
import ru.netology.data.Data;
import ru.netology.data.DbInteraction;
import ru.netology.page.MainPage;
import ru.netology.page.PaymentPage;

import static com.codeborne.selenide.Selenide.open;

public class MonthIsEmpty {
    DbInteraction db = new DbInteraction();
    MainPage buttons = new MainPage();
    PaymentPage fillfull = new PaymentPage();

    @BeforeEach
    public void setUp2() {
        open("http://localhost:8080", MainPage.class);
//        Configuration.holdBrowserOpen = true;
        db.deleteDataFromDb();
    }

    @BeforeAll
    static void setUpAll() {
        Configuration.headless = true;
        SelenideLogger.addListener("allure", new AllureSelenide());
    }

    @AfterAll
    static void tearDownAll() {
        SelenideLogger.removeListener("allure");
    }

    @Test
    @DisplayName("ByDebit")
    void shouldShowErrorMessageWhenMonthIsEmpty() {
        buttons.buyDebitCard();
        val cardNumber = Data.getValidCardNumber();
        val year = Data.getValidYear();
        val owner = Data.getValidNameEn();
        val cvc = Data.getValidCVC();
        fillfull.fillOutFields(cardNumber, "", year, owner, cvc);
        fillfull.errorMessageInvalidFormat();
    }

    @Test
    @DisplayName("ByCredit")
    void shouldShowErrorMessageWhenMonthIsEmptyAndPayByCredit() {
        buttons.buyCreditCard();
        val cardNumber = Data.getValidCardNumber();
        val year = Data.getValidYear();
        val owner = Data.getValidNameEn();
        val cvc = Data.getValidCVC();
        fillfull.fillOutFields(cardNumber, "", year, owner, cvc);
        fillfull.errorMessageInvalidFormat();
    }
}
