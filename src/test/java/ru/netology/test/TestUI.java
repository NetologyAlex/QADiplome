package ru.netology.test;

import com.codeborne.selenide.Configuration;
import com.codeborne.selenide.logevents.SelenideLogger;
import io.qameta.allure.selenide.AllureSelenide;
import lombok.val;
import org.junit.jupiter.api.*;
import ru.netology.data.Data;
import ru.netology.data.DbInteraction;
import ru.netology.page.Buttons;
import ru.netology.page.FillForm;

import static com.codeborne.selenide.Selenide.open;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class TestUI {
    DbInteraction db = new DbInteraction();
    Buttons buttons = new Buttons();
    FillForm fillfull = new FillForm();

    @BeforeEach
    public void setUp2() {
        open("http://localhost:8080", Buttons.class);
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

    @Nested
    class ValidData {
        @Test
        @DisplayName("EnNameByDebitApproveDbRequest")
        void shouldApproveDebitCardBuyFromEnName() {
            buttons.buyDebitCard();
            val cardNumber = Data.getValidCardNumber();
            val month = Data.getValidMonth();
            val year = Data.getValidYear();
            val owner = Data.getValidNameEn();
            val cvc = Data.getValidCVC();
            fillfull.fillOutFields(cardNumber, month, year, owner, cvc);
            fillfull.expectApprovalFromBank();
            val expected = Data.getValidCardStatus();
            val actual = DbInteraction.getStatusBuyDebit();
            assertEquals(expected, actual);
        }

        @Test
        @DisplayName("EnNameByCreditApproveDbRequest")
        void shouldApproveCreditCardBuyFromEnName() {
            buttons.buyCreditCard();
            val cardNumber = Data.getValidCardNumber();
            val month = Data.getValidMonth();
            val year = Data.getValidYear();
            val owner = Data.getValidNameEn();
            val cvc = Data.getValidCVC();
            fillfull.fillOutFields(cardNumber, month, year, owner, cvc);
            fillfull.expectApprovalFromBank();
            val expected = Data.getValidCardStatus();
            val actual = DbInteraction.getStatusBuyCredit();
            assertEquals(expected, actual);
        }
    }

    @Nested
    class EmptyForm {
        @Test
        @DisplayName("ByDebit")
        void shouldShowErrorMessageWhenWeSendEmptyForm() {
            buttons.buyDebitCard();
            fillfull.fillOutFields("", "", "", "", "");
            fillfull.errorMessageInvalidFormat();
        }

        @Test
        @DisplayName("ByCredit")
        void shouldShowErrorMessageWhenWeSendEmptyFormAndPayByCredit() {
            buttons.buyCreditCard();
            fillfull.fillOutFields("", "", "", "", "");
            fillfull.errorMessageInvalidFormat();
        }


    }

    @Nested
    class CvcIsEmpty {
        @Test
        @DisplayName("ByDebit")
        void shouldShowErrorMessageWhenCvcIsEmpty() {
            buttons.buyDebitCard();
            val cardNumber = Data.getValidCardNumber();
            val month = Data.getValidMonth();
            val year = Data.getValidYear();
            val owner = Data.getValidNameEn();
            fillfull.fillOutFields(cardNumber, month, year, owner, "");
            fillfull.errorMessageInvalidFormat();
        }

        @Test
        @DisplayName("ByCredit")
        void shouldShowErrorMessageWhenCvcIsEmptyAndPayByCredit() {
            buttons.buyCreditCard();
            val cardNumber = Data.getValidCardNumber();
            val month = Data.getValidMonth();
            val year = Data.getValidYear();
            val owner = Data.getValidNameEn();
            fillfull.fillOutFields(cardNumber, month, year, owner, "");
            fillfull.errorMessageInvalidFormat();
        }
    }

    @Nested
    class OwnerIsEmpty {


        @Test
        @DisplayName("ByDebit")
        void shouldShowErrorMessageWhenOwnerIsEmpty() {
            buttons.buyDebitCard();
            val cardNumber = Data.getValidCardNumber();
            val month = Data.getValidMonth();
            val year = Data.getValidYear();
            val cvc = Data.getValidCVC();
            fillfull.fillOutFields(cardNumber, month, year, "", cvc);
            fillfull.errorMessageWhenOwnerFieldIsEmpty();
        }

        @Test
        @DisplayName("ByCredit")
        void shouldShowErrorMessageWhenOwnerIsEmptyAndPayByCredit() {
            buttons.buyCreditCard();
            val cardNumber = Data.getValidCardNumber();
            val month = Data.getValidMonth();
            val year = Data.getValidYear();
            val cvc = Data.getValidCVC();
            fillfull.fillOutFields(cardNumber, month, year, "", cvc);
            fillfull.errorMessageWhenOwnerFieldIsEmpty();
        }
    }

    @Nested
    class YearIsEmpty {

        @Test
        @DisplayName("ByDebit")
        void shouldShowErrorMessageWhenYearIsEmpty() {
            buttons.buyDebitCard();
            val cardNumber = Data.getValidCardNumber();
            val month = Data.getValidMonth();
            val owner = Data.getValidNameEn();
            val cvc = Data.getValidCVC();
            fillfull.fillOutFields(cardNumber, month, "", owner, cvc);
            fillfull.errorMessageInvalidFormat();
        }

        @Test
        @DisplayName("ByCredit")
        void shouldShowErrorMessageWhenYearIsEmptyAndPayByCredit() {
            buttons.buyCreditCard();
            val cardNumber = Data.getValidCardNumber();
            val month = Data.getValidMonth();
            val owner = Data.getValidNameEn();
            val cvc = Data.getValidCVC();
            fillfull.fillOutFields(cardNumber, month, "", owner, cvc);
            fillfull.errorMessageInvalidFormat();
        }
    }

    @Nested
    class MonthIsEmpty {

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

    @Nested
    class CardNumberIsInvalid {
        @Test
        @DisplayName("ByDebitDbRequest")
        void shouldDeclineBuyWhenCardIsInvalid() {
            buttons.buyDebitCard();
            val cardNumber = Data.getInvalidCardNumber();
            val month = Data.getValidMonth();
            val year = Data.getValidYear();
            val owner = Data.getValidNameEn();
            val cvc = Data.getValidCVC();
            fillfull.fillOutFields(cardNumber, month, year, owner, cvc);
            fillfull.expectRejectionFromBank();
            val expected = Data.getInvalidCardStatus();
            val actual = DbInteraction.getStatusBuyDebit();
            assertEquals(expected, actual);
        }

        @Test
        @DisplayName("ByCreditDbRequest")
        void shouldDeclineBuyWhenCardIsInvalidAndPayByCredit() { // карта успешно одобрена банком
            buttons.buyCreditCard();
            val cardNumber = Data.getInvalidCardNumber();
            val month = Data.getValidMonth();
            val year = Data.getValidYear();
            val owner = Data.getValidNameEn();
            val cvc = Data.getValidCVC();
            fillfull.fillOutFields(cardNumber, month, year, owner, cvc);
            fillfull.expectRejectionFromBank();
            val expected = Data.getInvalidCardStatus();
            val actual = DbInteraction.getStatusBuyCredit();
            assertEquals(expected, actual);
        }
    }

    @Nested
    class CvcIs2Digit {

        @Test
        @DisplayName("ByDebit")
        void shouldShowErrorMessageWhenCvcIsInvalid() {
            buttons.buyDebitCard();
            val cardNumber = Data.getValidCardNumber();
            val month = Data.getValidMonth();
            val year = Data.getValidYear();
            val owner = Data.getValidNameEn();
            val cvc = Data.getInvalidCVC2Digit();
            fillfull.fillOutFields(cardNumber, month, year, owner, cvc);
            fillfull.errorMessageInvalidFormat();
        }

        @Test
        @DisplayName("ByCredit")
        void shouldShowErrorMessageWhenCvcIsInvalidAndPayByCredit() {
            buttons.buyCreditCard();
            val cardNumber = Data.getValidCardNumber();
            val month = Data.getValidMonth();
            val year = Data.getValidYear();
            val owner = Data.getValidNameEn();
            val cvc = Data.getInvalidCVC2Digit();
            fillfull.fillOutFields(cardNumber, month, year, owner, cvc);
            fillfull.errorMessageInvalidFormat();
        }
    }

    @Nested
    class MonthIsInvalid {

        @Test
        @DisplayName("ByDebit")
        void shouldShowErrorMessageWhenMonthIsInvalid() {
            buttons.buyDebitCard();
            val cardNumber = Data.getValidCardNumber();
            val month = Data.getInValidMonth();
            val year = Data.getValidYear();
            val owner = Data.getValidNameEn();
            val cvc = Data.getValidCVC();
            fillfull.fillOutFields(cardNumber, month, year, owner, cvc);
            fillfull.errorMessageInvalidDuration();
        }

        @Test
        @DisplayName("ByCredit")
        void shouldShowErrorMessageWhenMonthIsInvalidAndPayByCredit() {
            buttons.buyCreditCard();
            val cardNumber = Data.getValidCardNumber();
            val month = Data.getInValidMonth();
            val year = Data.getValidYear();
            val owner = Data.getValidNameEn();
            val cvc = Data.getValidCVC();
            fillfull.fillOutFields(cardNumber, month, year, owner, cvc);
            fillfull.errorMessageInvalidDuration();
        }
    }

    @Nested
    class YearIsInvalid {
        @Test
        @DisplayName("ByDebit")
        void shouldShowErrorMessageWhenYearIsInvalid() {
            buttons.buyDebitCard();
            val cardNumber = Data.getValidCardNumber();
            val month = Data.getValidMonth();
            val year = Data.getInvalidYear();
            val owner = Data.getValidNameEn();
            val cvc = Data.getValidCVC();
            fillfull.fillOutFields(cardNumber, month, year, owner, cvc);
            fillfull.errorMessageInvalidYear();
        }

        @Test
        @DisplayName("ByCredit")
        void shouldShowErrorMessageWhenYearIsInvalidAndPayByCredit() {
            buttons.buyCreditCard();
            val cardNumber = Data.getValidCardNumber();
            val month = Data.getValidMonth();
            val year = Data.getInvalidYear();
            val owner = Data.getValidNameEn();
            val cvc = Data.getValidCVC();
            fillfull.fillOutFields(cardNumber, month, year, owner, cvc);
            fillfull.errorMessageInvalidYear();
        }
    }

    @Nested
    class RandomOwnerName {
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

    @Nested
    class RandomCardNumber {
        @Test
        @DisplayName("ByDebitDbRequest")
        void shouldDeclineWhenDebitCardBuyFromRandomCardNumber() {
            buttons.buyDebitCard();
            val cardNumber = Data.getRandomCardNumber();
            val month = Data.getValidMonth();
            val year = Data.getValidYear();
            val owner = Data.getValidNameEn();
            val cvc = Data.getValidCVC();
            fillfull.fillOutFields(cardNumber, month, year, owner, cvc);
            fillfull.expectApprovalFromBank();
            val expected = "DECLINED";
            val actual = DbInteraction.getStatusBuyDebit();
            assertEquals(expected, actual);
        }

        @Test
        @DisplayName("ByCreditDbRequest")
        void shouldDeclineWhenCreditCardBuyFromRandomCardNumber() {
            buttons.buyCreditCard();
            val cardNumber = Data.getRandomCardNumber();
            val month = Data.getValidMonth();
            val year = Data.getValidYear();
            val owner = Data.getValidNameEn();
            val cvc = Data.getValidCVC();
            fillfull.fillOutFields(cardNumber, month, year, owner, cvc);
            fillfull.expectApprovalFromBank();
            val expected = "DECLINED";
            val actual = DbInteraction.getStatusBuyCredit();
            assertEquals(expected, actual);
        }
    }

    @Nested
    class CvcNumbersIsNulls {
        @Test
        @DisplayName("ByDebitDbRequest")
        void shouldDeclineDebitCardBuyWhenCvcNumberIsNulls() {
            buttons.buyDebitCard();
            val cardNumber = Data.getValidCardNumber();
            val month = Data.getValidMonth();
            val year = Data.getValidYear();
            val owner = Data.getValidNameEn();
            val cvc = Data.getCVCEqualsNulls();
            fillfull.fillOutFields(cardNumber, month, year, owner, cvc);
            fillfull.expectRejectionFromBank();
            val expected = "DECLINED";
            val actual = DbInteraction.getStatusBuyDebit();
            assertEquals(expected, actual);
        }

        @Test
        @DisplayName("ByCreditDbRequest")
        void shouldDeclineCreditCardBuyWhenCvcNumberIsNulls() {
            buttons.buyCreditCard();
            val cardNumber = Data.getValidCardNumber();
            val month = Data.getValidMonth();
            val year = Data.getValidYear();
            val owner = Data.getValidNameEn();
            val cvc = Data.getCVCEqualsNulls();
            fillfull.fillOutFields(cardNumber, month, year, owner, cvc);
            fillfull.expectRejectionFromBank();
            val expected = "DECLINED";
            val actual = DbInteraction.getStatusBuyCredit();
            assertEquals(expected, actual);
        }
    }
}