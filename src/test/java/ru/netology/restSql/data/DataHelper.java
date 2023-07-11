package ru.netology.restSql.data;

import lombok.Value;

import java.util.Random;

public class DataHelper {
    private DataHelper() { //приватный конструктор
    }

    public static AuthInfo getAuthInfoWithTestData() { //метод валидных фиксированных валидных данных
        return new AuthInfo("vasya", "qwerty123");
    }

    public static CardInfo getFirstCardInfo() { //метод информации о первой карте
        return new CardInfo("92df3f1c-a033-48e6-8390-206f6b1f56c0", "5559 0000 0000 0001");
    }

    public static CardInfo getSecondCardInfo() { // метод информации о второй карте
        return new CardInfo("0f3f5c2a-249e-4c3d-8287-09f7a039391d", "5559 0000 0000 0002");
    }

    public static int generateValidAmount(int balance) { //метод для подсчета суммы перевода случайной
        return new Random().nextInt(balance) + 1;
    }

    @Value //Дата класс для описания пользователя
    public static class AuthInfo {
        String login;
        String password;
    }

    @Value //Дата класс для описания карты
    public static class CardInfo {
        String id;
        String number;
    }

    @Value //Дата класс для описания кода верификации
    public static class VerificationCode {
        String code;
    }

    @Value //Дата класс для описания данных, которые отсылаем в запросе
    public static class VerificationInfo {
        String login;
        String code;
    }
}
