package com.clothingstore.util;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBConnection {
    private static final String URL = "jdbc:mysql://localhost:3306/clothing_store";
    private static final String USER = "root";
    private static final String PASSWORD = "sqlduong"; // Đổi password nếu MySQL của bạn có mật khẩu

    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(URL, USER, PASSWORD);
    }
}
