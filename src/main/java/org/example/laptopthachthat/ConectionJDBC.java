package org.example.laptopthachthat;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConectionJDBC {
    private String hostName = "localhost:3306";
    private String dbName = "LapTopThachThat";
    private String username = "root";
    private String password = "linhnhi234";
    private String url = "jdbc:mysql://" + hostName + "/" + dbName;
    public Connection getConnection() throws SQLException {
        Connection conn = null;
        try{
            conn = DriverManager.getConnection(url, username, password);
            System.out.println("Kết no thanh cong");
        }catch (SQLException e){
            e.printStackTrace();
        }
        return conn;
    }
}
