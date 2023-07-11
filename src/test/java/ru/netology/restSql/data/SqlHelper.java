package ru.netology.restSql.data;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.SneakyThrows;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanHandler;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class SqlHelper {
    private static final QueryRunner runner = new QueryRunner();

    private SqlHelper() { //приватный конструктор
    }

    private static Connection getConn() throws SQLException { //метод для подключения к БД
        return DriverManager.getConnection("jdbc:mysql://localhost:3306/app", "app", "pass");
    }

    @SneakyThrows //метод для получения последнего кода верификации
    public static DataHelper.VerificationCode getVerificationCode() {
        var codeSQL = "SELECT code FROM auth_codes ORDER BY created DESC LIMIT 1";
        var conn = getConn();
        var result = runner.query(conn, codeSQL, new BeanHandler<>(SQLAuthCode.class));
        return new DataHelper.VerificationCode(result.getCode());
    }

    @Data
    @NoArgsConstructor
    public static class SQLAuthCode {
        private String id;
        private String user_id;
        private String code;
        private String created;
    }
}
