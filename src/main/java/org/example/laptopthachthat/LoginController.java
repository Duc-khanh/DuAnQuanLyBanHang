package org.example.laptopthachthat;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;

import javafx.scene.control.*;
import javafx.stage.Stage;
import org.example.laptopthachthat.ConectionJDBC;
import org.example.laptopthachthat.Main;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.EventObject;

public class LoginController {


    @FXML
    private TextField username;
    @FXML
    private PasswordField password;
    @FXML
    private Button signInButton;
    @FXML
    private Button signUpButton;

    @FXML
    public void initialize() {

    }

    @FXML
    public void signIn(ActionEvent actionEvent) throws SQLException, IOException {

        String username = this.username.getText();
        String password = this.password.getText();


        System.out.println("Attempting to login with username: " + username + " and password: " + password);

        if (username.isEmpty() || password.isEmpty()) {
            showAlert("Vui lòng nhập tên người dùng và mặt khẩu để đăng nhập");
            return;
        }
        String role = checkUser(username, password);
        if (role != null) {
            System.out.println("Đang nhap thanh cong" );

            switch (role) {
                case "Admin":
                    showAlert("Đăng nhập thành công .Xin chào Admin   " + username);
                    Main.changeScene("Admin/HomeAdmin.fxml");
                    break;
                case "User":
                    showAlert("Đăng nhập thành công .Xin chào User");

                    Main.changeScene("User/UserDisplay.fxml");
                    break;
                default:
                    showAlert("Vai trò không được công nhận. ");
            }
        } else {
            System.out.println("Đăng Nhập Không Thành Công");
            showAlert("Tên người dùng hoặc mật khẩu không hợp lệ.");
            this.username.requestFocus();
        }

    }

    private String checkUser(String username, String password) throws SQLException {
        Connection conn = ConectionJDBC.getConnection();
        if (conn == null) {
            showAlert("Kết Nối Dữ Liệu Không Thành Công");
            return null;
        }

        String query = "SELECT * FROM user WHERE LOWER(username)=LOWER(?) AND password=?";
        try (PreparedStatement ps = conn.prepareStatement(query)) {
            ps.setString(1, username);
            ps.setString(2, password);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                int userID = rs.getInt("userID");
                String name = rs.getString("username");
                String state = rs.getString("state");
                if (!"Active".equalsIgnoreCase(state)) {
                    showAlert("Your account is blocked. Please contact support.");
                    return null;
                }
                LoggedInUser.login(userID,name);
                return rs.getString("role");
            } else {
                return null;
            }
        } catch (SQLException e) {
            e.printStackTrace();
            showAlert("Database error occurred!");
            return null;
        }
    }



    public void showSignUp(ActionEvent event) throws IOException {

        Parent root = FXMLLoader.load(Main.class.getResource("Register.fxml"));
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        Scene scene = new Scene(root);
        stage.setTitle("Sign up");
        stage.setScene(scene);
        stage.show();
    }

    private void showAlert(String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }



}
