package org.example.laptopthachthat;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.fxml.FXMLLoader;
import org.example.laptopthachthat.ConectionJDBC;
import org.example.laptopthachthat.Main;

public class SignUpController {

    @FXML
    private TextField username;
    @FXML
    private PasswordField passwordTextFile;
    @FXML
    private PasswordField rePassword;
    @FXML
    private TextField phoneNumber;
    @FXML
    private TextField address;

    private static final String PHONE_REGEX = "^0\\d{9}$";
    private static final String ADDRESS_REGEX = "^(?=.*[A-Za-zÀ-ỹ])[A-Za-zÀ-ỹ0-9\\s]+(?:\\s[A-Za-zÀ-ỹ\\s]+)*$";

    public void signUp(ActionEvent actionEvent) throws SQLException {
        String username = this.username.getText().trim();
        String password = this.passwordTextFile.getText().trim();
        String rePassword = this.rePassword.getText().trim();
        String address = this.address.getText().trim();
        String phoneNumber = this.phoneNumber.getText().trim();

        // Validate inputs
        if (username.isEmpty() || password.isEmpty() || rePassword.isEmpty() || address.isEmpty() || phoneNumber.isEmpty()) {
            showAlert("All fields must be filled.");
        } else if (username.length() < 6) {
            showAlert("Username must be at least 6 characters long.");
        } else if (password.length() < 8) {
            showAlert("Password must be at least 8 characters long.");
        } else if (!password.equals(rePassword)) {
            showAlert("Passwords do not match.");
        } else if (!isValidPhoneNumber(phoneNumber)) {
            showAlert("Invalid phone number. It should be 10 digits and start with 0.");
        } else if (!isValidAddress(address)) {
            showAlert("Invalid address format.");
        } else if (checkAccount(username)) {
            showAlert("Username is already taken.");
        } else {
            saveUser(username, password, address, phoneNumber);
            showAlert("Sign up successful!");

            try {
                Main.changeScene("Login.fxml");
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    private void saveUser(String username, String password, String address, String phoneNumber) throws SQLException {
        Connection conn = ConectionJDBC.getConnection();
        String query = "INSERT INTO User(role, state, username, password, address, phoneNumber) VALUES(?,?,?, ?, ?, ?)";
        try (PreparedStatement ps = conn.prepareStatement(query)) {
            ps.setString(1, "Customer");
            ps.setString(2, "Active");
            ps.setString(3, username);
            ps.setString(4, password);
            ps.setString(5, address);
            ps.setString(6, phoneNumber);
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private boolean checkAccount(String username) throws SQLException {
        Connection conn = ConectionJDBC.getConnection();
        String query = "SELECT username FROM User WHERE username = ?";
        try (PreparedStatement ps = conn.prepareStatement(query)) {
            ps.setString(1, username);
            ResultSet rs = ps.executeQuery();
            return rs.next();
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    private boolean isValidPhoneNumber(String phoneNumber) {
        return phoneNumber.matches(PHONE_REGEX);
    }

    @FXML
    private void validatePhoneNumber() {
        String phone = phoneNumber.getText().trim();
        if (!isValidPhoneNumber(phone)) {
            phoneNumber.setStyle("-fx-border-color: #ff0000;");
        } else {
            phoneNumber.setStyle(null);
        }
    }
    private boolean isValidAddress(String address) {
        return address.matches(ADDRESS_REGEX);
    }

    private void showAlert(String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }


    public void showLogin(ActionEvent actionEvent) {
        try {
            Main.changeScene("Login.fxml");
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}
