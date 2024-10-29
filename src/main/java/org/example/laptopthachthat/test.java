package org.example.laptopthachthat;

import java.sql.SQLException;

public class test {
    public static void main(String[] args) throws SQLException {
        ConectionJDBC con = new ConectionJDBC();
        ConectionJDBC.getConnection();
    }
}
