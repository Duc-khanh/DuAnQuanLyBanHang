package org.example.laptopthachthat.User;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import org.example.laptopthachthat.ConectionJDBC;
import org.example.laptopthachthat.Main;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Objects;


public class InformationController {

    @FXML
    private TextField fullNameField;

    @FXML
    private TextField addressField;

    @FXML
    private TextField phoneNumberField;

    // Constructor mặc định, không có tham số
    public InformationController() {
        // Không cần khởi tạo gì trong constructor
    }

    // Kiểm tra tính hợp lệ của tên
    private boolean isValidName(String name) {
        return name != null && !name.trim().isEmpty() && name.matches("[a-zA-Z\\s]+");
    }

    // Kiểm tra tính hợp lệ của địa chỉ
    private boolean isValidAddress(String address) {
        return address != null && !address.trim().isEmpty();
    }
    private boolean isValidPhoneNumber(String phoneNumber) {
        return phoneNumber.matches("^\\d{10}$");
    }


    @FXML
    private void validatePhoneNumber() {
        String phoneNumber = phoneNumberField.getText().trim();
        if (!isValidPhoneNumber(phoneNumber)) {
            phoneNumberField.setStyle("-fx-border-color: #ff0000;");
        } else {
            phoneNumberField.setStyle(null);
        }
    }

    // Hàm xử lý sự kiện lưu thông tin khi nhấn nút "Save"
    @FXML
    private void handleSaveChanges() throws SQLException {
        String username = fullNameField.getText();
        String address = addressField.getText();
        String phoneNumber = phoneNumberField.getText();

        if (!isValidName(username)) {
            showError("Tên không hợp lệ! Vui lòng nhập tên hợp lệ.");
            return;
        }

        if (!isValidAddress(address)) {
            showError("Địa chỉ không được để trống!");
            return;
        }

        if (!isValidPhoneNumber(phoneNumber)) {
            showError("Số điện thoại không hợp lệ! Vui lòng nhập đúng định dạng.");
            return;
        }

        ConectionJDBC conectionJDBC = new ConectionJDBC();
        Connection connection = conectionJDBC.getConnection();

        if (connection != null) {
            String query = "UPDATE User SET username = ?, address = ?, phoneNumber = ? WHERE userID = ?";
            try (PreparedStatement statement = connection.prepareStatement(query)) {
                statement.setString(1, username);
                statement.setString(2, address);
                statement.setString(3, phoneNumber);
                statement.setInt(4, Session.getLoggedInUserId());

                int rowsAffected = statement.executeUpdate();
                if (rowsAffected > 0) {
                    showSuccess("Thông tin đã được lưu thành công.");
                } else {
                    showError("Không tìm thấy người dùng để cập nhật.");
                }
            } catch (SQLException e) {
                e.printStackTrace();
                showError("Có lỗi xảy ra khi lưu dữ liệu.");
            } finally {
                try {
                    connection.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
    }


    private void showAlert(String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setContentText(message);
        alert.show();
    }

    private void showError(String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Lỗi");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }


    private void showSuccess(String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Thành công");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
    public void BackUser(ActionEvent event) throws IOException {

        Parent root = FXMLLoader.load(Objects.requireNonNull(Main.class.getResource("UserDisplay.fxml")));
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        Scene scene = new Scene(root);
        stage.setTitle("Sign up");
        stage.setScene(scene);
        stage.show();
    }
}
