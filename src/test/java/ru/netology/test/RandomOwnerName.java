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
import static org.junit.jupiter.api.Assertions.assertEquals;

public class RandomOwnerName {
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
    @DisplayName("ByDebitRuNameDbRequest")
    void shouldDeclineWhenDebitCardBuyFromRandomRuName() {
        buttons.buyDebitCard();
        val cardNumber = Data.getValidCardNumber();
        val month = Data.getValidMonth();
        val year = Data.getValidYear();
        val owner = Data.getRandomNameRu();
        val cvc = Data.getValidCVC();
        fillfull.fillOutFields(cardNumber, month, year, owner, cvc);
        fillfull.expectApprovalFromBank();
        val expected = "DECLINED";
        val actual = DbInteraction.getStatusBuyDebit();
        assertEquals(expected, actual);
    }

    @Test
    @DisplayName("ByCreditRuNameDbRequest")
    void shouldDeclineWhenCreditCardBuyFromRandomRuName() {
        buttons.buyCreditCard();
        val cardNumber = Data.getValidCardNumber();
        val month = Data.getValidMonth();
        val year = Data.getValidYear();
        val owner = Data.getRandomNameRu();
        val cvc = Data.getValidCVC();
        fillfull.fillOutFields(cardNumber, month, year, owner, cvc);
        fillfull.expectApprovalFromBank();
        val expected = "DECLINED";
        val actual = DbInteraction.getStatusBuyDebit();
        assertEquals(expected, actual);
    }

    @Test
    @DisplayName("ByDebitEnNameDbRequest")
    void shouldApproveDebitCardBuyFromRandomEnName() {
        buttons.buyDebitCard();
        val cardNumber = Data.getValidCardNumber();
        val month = Data.getValidMonth();
        val year = Data.getValidYear();
        val owner = Data.getRandomNameEn();
        val cvc = Data.getValidCVC();
        fillfull.fillOutFields(cardNumber, month, year, owner, cvc);
        fillfull.expectApprovalFromBank();
        val expected = Data.getValidCardStatus();
        val actual = DbInteraction.getStatusBuyDebit();
        assertEquals(expected, actual);
    }

    @Test
    @DisplayName("ByCreditEnNameDbRequest")
    void shouldApproveCreditCardBuyFromRandomEnName() {
        buttons.buyCreditCard();
        val cardNumber = Data.getValidCardNumber();
        val month = Data.getValidMonth();
        val year = Data.getValidYear();
        val owner = Data.getRandomNameEn();
        val cvc = Data.getValidCVC();
        fillfull.fillOutFields(cardNumber, month, year, owner, cvc);
        fillfull.expectApprovalFromBank();
        val expected = Data.getValidCardStatus();
        val actual = DbInteraction.getStatusBuyCredit();
        assertEquals(expected, actual);
    }
}