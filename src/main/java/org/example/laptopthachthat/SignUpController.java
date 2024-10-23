package org.example.laptopthachthat;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;

public class SignUpController {
    @FXML
    private TextField UsernameTextFile;
    @FXML
    private TextField PasswordTextFile;
    @FXML
    private TextField ConfirmPasswordTextFile;
    @FXML
    private TextField AddressTextFile;
    @FXML
    private TextField PhoneNumberTextFile;
    @FXML
    private Label error;

    private void showAlert(Alert.AlertType alertType, String title, String message) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setHeaderText(title);
        alert.setContentText(message);
        alert.showAndWait();
    }
    public void BackToSignin(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(LoginApplication.class.getResource("Login.fxml"));
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        Scene scene = new Scene(root);
        stage.setTitle("Sign up");
        stage.setScene(scene);
        stage.show();
    }


    @FXML
    private void registerUser() {
        String username = UsernameTextFile.getText();
        String password = PasswordTextFile.getText();
        String confirmPassword = ConfirmPasswordTextFile.getText();
        String address = AddressTextFile.getText();
        String phoneNumber = PhoneNumberTextFile.getText();

        if (username.isEmpty()) {
            showAlert(Alert.AlertType.ERROR, "Form Error!", "Username is required");
            return;
        }

        if (!ValidateUsername(username)) {
            showAlert(Alert.AlertType.ERROR, "Form Error!", "Username must have a length of 3 to 15 characters and contain only letters, numbers, underscores _, or hyphens -.");
            return;
        }

        if (password.isEmpty()) {
            showAlert(Alert.AlertType.ERROR, "Form Error!", "Password is required");
            return;
        }

        if (!ValidatePassword(password)) {  // Corrected the logic here
            showAlert(Alert.AlertType.ERROR, "Form Error!", "Password must be at least 8 characters long and contain at least one uppercase letter, one lowercase letter, and one number.");
            return;
        }

        if (confirmPassword.isEmpty()) {
            showAlert(Alert.AlertType.ERROR, "Form Error!", "Confirm Password is required");
            return;
        }

        if (!password.equals(confirmPassword)) {
            showAlert(Alert.AlertType.ERROR, "Form Error!", "Passwords do not match.");
            return;
        }

        if (phoneNumber.isEmpty()) {
            showAlert(Alert.AlertType.ERROR, "Form Error!", "Phone number is required");
            return;
        }

        if (!ValidatePhoneNumber(phoneNumber)) {
            showAlert(Alert.AlertType.ERROR, "Form Error!", "Phone number must be 10 or 11 digits.");
            return;
        }

        if (address.isEmpty()) {
            showAlert(Alert.AlertType.ERROR, "Form Error!", "Address is required");
            return;
        }

    }

    private boolean ValidateUsername(String username) {
        return username.matches("^[a-zA-Z0-9_-]{3,15}$");
    }

    private boolean ValidatePhoneNumber(String phoneNumber) {
        return phoneNumber.matches("^0(9[0-9]{8}|1[0-9]{9}|7[0-9]{8})$");
    }

    private boolean ValidatePassword(String password) {
        return password.matches("^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)[a-zA-Z\\d]{8,}$");
    }
}
