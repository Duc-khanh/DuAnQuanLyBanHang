package com.example.duancanstrore;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBConnection {
    private static final String URL ="jdbc:mysql://localhost:3306/LaptopThachThat ";
    private static final String USER1 = "root";
    private static final String PASSWORD1 = "linhnhi234";
    private static final String USER2 = "root";
    private static final String PASSWORD2 = "root@123";
    public static Connection getConnection(int user) throws SQLException {
        switch (user) {
            case 1:
                return DriverManager.getConnection(URL, USER1, PASSWORD1);
            case 2:
                return DriverManager.getConnection(URL, USER2, PASSWORD2);
            default:
                System.out.println("Không tìm thấy tài khoản Database");
                return null;
        }
    }
}