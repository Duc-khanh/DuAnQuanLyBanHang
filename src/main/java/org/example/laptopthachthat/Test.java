package org.example.laptopthachthat;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class Test {

    Connection connection;
    Statement statement;

    public Test() throws SQLException {
        // Khởi tạo kết nối và câu lệnh SQL
        connection = DBConnection.getConnection();
        statement = connection.createStatement();

        // Thực thi truy vấn
        String query = "SELECT * FROM user";
        try {
            ResultSet rs = statement.executeQuery(query);

            while (rs.next()) {  // Di chuyển con trỏ xuống bản ghi kế tiếp
                int userID = rs.getInt(1);
                String role  = rs.getString(2);
                String state  = rs.getString(3);
                String username = rs.getString(4);
                String password = rs.getString(5);
                String address = rs.getString(6);
                String phoneNumber = rs.getString(7);
                System.out.println(userID + " - " + role +" - "+ state + username + " - " + password + " - " + address + phoneNumber);
            }

            // Đóng ResultSet sau khi hoàn tất xử lý
            rs.close();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            // Đóng các tài nguyên
            if (statement != null) statement.close();
            if (connection != null) connection.close();
        }
    }

    public static void main(String[] args) {
        try {
            new Test();  // Gọi constructor để thực hiện truy vấn
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
