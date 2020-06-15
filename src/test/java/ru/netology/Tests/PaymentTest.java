package ru.netology.Tests;

import org.junit.jupiter.api.*;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;
import ru.netology.Models.CardModel;

public class PaymentTest extends BaseTest {
    private CardModel validCard;
    private CardModel invalidCard;

    @Test
    @DisplayName("Успешная покупка утвержденной картой")
    void payWithApprovedCard() {
        validCard = CardModel.generatedApprovedCard("ru");
        formPage.buyByDebit();
        formPage.fillCardData(validCard);
        formPage.pushContinueButton();
        formPage.checkMessageSuccess();
    }

    @Test
    @DisplayName("Успешная покупка в кредит утвержденной картой")
    void payCreditWithApprovedCard() {
        validCard = CardModel.generatedApprovedCard("ru");
        formPage.buyInCredit();
        formPage.fillCardData(validCard);
        formPage.pushContinueButton();
        formPage.checkMessageSuccess();
    }

    @ParameterizedTest
    @CsvFileSource(resources = "/Field.csv", numLinesToSkip = 1)
    void verifyPayField(String numberCard, String month, String year, String name, String cvv,
                        String expected, String message) {
        formPage.buyByDebit();
        formPage.setCardNumber(numberCard);
        formPage.setCardMonth(month);
        formPage.setCardYear(year);
        formPage.setCardOwner(name);
        formPage.setCardCVV(cvv);
        formPage.pushContinueButton();
        formPage.compareExpectedAndActualResult(expected);
    }

    @ParameterizedTest
    @CsvFileSource(resources = "/Field.csv", numLinesToSkip = 1)
    void verifyCreditField(String numberCard, String month, String year, String name, String cvv,
                           String expected, String message) {
        formPage.buyInCredit();
        formPage.setCardNumber(numberCard);
        formPage.setCardMonth(month);
        formPage.setCardYear(year);
        formPage.setCardOwner(name);
        formPage.setCardCVV(cvv);
        formPage.pushContinueButton();
        formPage.compareExpectedAndActualResult(expected);
    }


    @Test
    @DisplayName("Отказ покупки не утвержденной картой")
    void payWithDeclinedCard() {
        invalidCard = CardModel.generatedDeclinedCard("ru");
        formPage.buyInCredit();
        formPage.fillCardData(invalidCard);
        formPage.pushContinueButton();
        formPage.checkMessageError();
    }

    @Test
    @DisplayName("Отказ покупки в кредит не утвержденной картой")
    void payCreditWithDeclinedCard() {
        invalidCard = CardModel.generatedDeclinedCard("ru");
        formPage.buyInCredit();
        formPage.fillCardData(invalidCard);
        formPage.pushContinueButton();
        formPage.checkMessageError();
    }
}
