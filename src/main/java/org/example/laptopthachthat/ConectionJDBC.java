package org.example.laptopthachthat;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConectionJDBC {
    private static String hostName = "localhost:3306";
    private static String dbName = "LapTopThachThat";
    private static String username = "root";

    private static String password = "root@123";
    private static String url = "jdbc:mysql://" + hostName + "/" + dbName;

    public static Connection getConnection() throws SQLException {
        Connection conn = null;
        try{
            conn = DriverManager.getConnection(url, username, password);
            System.out.println("Kết nối thành công");
        }catch (SQLException e){
            e.printStackTrace();
        }
        return conn;
    }

    public static void main(String[] args) {


    }
}
