package ru.netology.page;

import com.codeborne.selenide.SelenideElement;

import static com.codeborne.selenide.Selectors.byText;
import static com.codeborne.selenide.Selenide.$;

public class Buttons {
    final SelenideElement buttonBuy = $("[class='button button_size_m button_theme_alfa-on-white']");
    final SelenideElement buttonBuyCredit = $(byText("Купить в кредит"));

    public FillForm buyDebitCard() {
        buttonBuy.click();
        return new FillForm();
    }

    public FillForm buyCreditCard() {
        buttonBuyCredit.click();
        return new FillForm();
    }
}