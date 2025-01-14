package org.example.laptopthachthat.Admin;

import javafx.fxml.FXML;
import javafx.scene.control.CheckBox;
import javafx.scene.control.TextField;
import org.example.laptopthachthat.ConectionJDBC;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class AddProductController {
    @FXML
    private TextField nameField;
    @FXML
    private TextField priceField;
    @FXML
    private TextField quantityField;
    @FXML
    private TextField descriptionField;
    @FXML
    private TextField stockField;
    @FXML
    private TextField imageField;

    @FXML
    private void addProductToDatabase() {
        String query = "INSERT INTO Products ( productName, price, quality, description, stock, image) VALUES ( ?, ?, ?, ?, ?, ?)";

        try (Connection connection = ConectionJDBC.getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {

            statement.setString(1, nameField.getText());
            statement.setDouble(2, Double.parseDouble(priceField.getText()));
            statement.setInt(3, Integer.parseInt(quantityField.getText()));
            statement.setString(4, descriptionField.getText());
            statement.setInt(5, stockField.getText().equals("Còn hang") ? 1 : 0);
            statement.setString(6, imageField.getText());

            statement.executeUpdate();
            System.out.println("Thêm sản phẩm thành công.");
        } catch (SQLException e) {
            System.err.println("Lỗi khi thêm sản phẩm: " + e.getMessage());
        }
    }
}
