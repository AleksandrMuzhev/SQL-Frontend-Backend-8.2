package ru.netology.restSql.test;

import org.junit.jupiter.api.Test;
import ru.netology.restSql.data.ApiHelper;
import ru.netology.restSql.data.DataHelper;
import ru.netology.restSql.data.SqlHelper;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class ApiSqlTest {

    @Test
    public void validTransferFromFirstToSecond() {
        var authInfo = DataHelper.getAuthInfoWithTestData(); //передача пользователя
        ApiHelper.makeQueryToLogin(authInfo, 200); //получение кода ответа
        var verificationCode = SqlHelper.getVerificationCode(); //получение кода верификации
        var verificationInfo = new DataHelper.VerificationInfo(authInfo.getLogin(), verificationCode.getCode());
        var tokenInfo = ApiHelper.sendQueryToVerify(verificationInfo, 200);
        var cardsBalances = ApiHelper.sendQueryToGetCardsBalances(tokenInfo.getToken(), 200); //получение балансов карт
        var firstCardBalance = cardsBalances.get(DataHelper.getFirstCardInfo().getId()); //баланс первой карты
        var secondCardBalance = cardsBalances.get(DataHelper.getSecondCardInfo().getId()); //баланс второй карты
        var amount = DataHelper.generateValidAmount(firstCardBalance);
        var transferInfo = new ApiHelper.APITransferInfo(DataHelper.getFirstCardInfo().getNumber(),
                DataHelper.getSecondCardInfo().getNumber(), amount);
        ApiHelper.generateQueryToTransfer(tokenInfo.getToken(), transferInfo, 200);
        cardsBalances = ApiHelper.sendQueryToGetCardsBalances(tokenInfo.getToken(), 200);
        var actualFirstCardBalance = cardsBalances.get(DataHelper.getFirstCardInfo().getId());
        var actualSecondCardBalance = cardsBalances.get(DataHelper.getSecondCardInfo().getId());
        assertAll(() -> assertEquals(firstCardBalance - amount, actualFirstCardBalance),
                () -> assertEquals(secondCardBalance + amount, actualSecondCardBalance));
    }

    @Test
    public void validTransferFromSecondToFirst() {
        var authInfo = DataHelper.getAuthInfoWithTestData(); //передача пользователя
        ApiHelper.makeQueryToLogin(authInfo, 200); //получение кода ответа
        var verificationCode = SqlHelper.getVerificationCode(); //получение кода верификации
        var verificationInfo = new DataHelper.VerificationInfo(authInfo.getLogin(), verificationCode.getCode());
        var tokenInfo = ApiHelper.sendQueryToVerify(verificationInfo, 200);
        var cardsBalances = ApiHelper.sendQueryToGetCardsBalances(tokenInfo.getToken(), 200); //получение балансов карт
        var firstCardBalance = cardsBalances.get(DataHelper.getFirstCardInfo().getId()); //баланс первой карты
        var secondCardBalance = cardsBalances.get(DataHelper.getSecondCardInfo().getId()); //баланс второй карты
        var amount = DataHelper.generateValidAmount(secondCardBalance);
        var transferInfo = new ApiHelper.APITransferInfo(DataHelper.getSecondCardInfo().getNumber(),
                DataHelper.getFirstCardInfo().getNumber(), amount);
        ApiHelper.generateQueryToTransfer(tokenInfo.getToken(), transferInfo, 200);
        cardsBalances = ApiHelper.sendQueryToGetCardsBalances(tokenInfo.getToken(), 200);
        var actualFirstCardBalance = cardsBalances.get(DataHelper.getFirstCardInfo().getId());
        var actualSecondCardBalance = cardsBalances.get(DataHelper.getSecondCardInfo().getId());
        assertAll(() -> assertEquals(firstCardBalance + amount, actualFirstCardBalance),
                () -> assertEquals(secondCardBalance - amount, actualSecondCardBalance));
    }
}
