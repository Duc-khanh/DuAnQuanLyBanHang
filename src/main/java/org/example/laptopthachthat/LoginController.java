//package org.example.laptopthachthat;
//
//
//
//import java.sql.Connection;
//import java.sql.PreparedStatement;
//import java.sql.ResultSet;
//import java.sql.SQLException;
//
//import static java.sql.DriverManager.getConnection;
//
//public class LoginController {
//
//    private ConectionJDBC conectionJDBC = new ConectionJDBC();
//    public boolean Login(String username, String password) throws SQLException {
//      String query = "SELECT * FROM users WHERE username = '" + username + "' AND password = '" + password + "'";
//      try (Connection conn = getConnection();
//           PreparedStatement pstmt = conn.prepareStatement(query)){
//          pstmt.setString(1,username);
//          pstmt.setString(2,password);
//          ResultSet rs = pstmt.executeQuery();
//          return rs.next();
//
//      }catch (Exception e){
//          e.printStackTrace();
//      }
//      return false;
//
//    }
//
//
//
//}
