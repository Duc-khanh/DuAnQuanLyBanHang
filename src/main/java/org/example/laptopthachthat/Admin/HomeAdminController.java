package org.example.laptopthachthat.Admin;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import org.example.laptopthachthat.ConectionJDBC;
import org.example.laptopthachthat.User.Product;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class HomeAdminController {
//    @FXML
//    private TableView<Product> productTable;
//    @FXML
//    private TableColumn<Product, Integer> idColumn;
//    @FXML
//    private TableColumn<Product, Boolean> stockColumn;
//    @FXML
//    private TableColumn<Product, Image> imageColumn;
//    @FXML
//    private TableColumn<Product, String> nameColumn;
//    @FXML
//    private TableColumn<Product, String> describeColumn;
//    @FXML
//    private TableColumn<Product, Integer> quantityColumn;
//    @FXML
//    private TableColumn<Product, Double> priceColumn;
//
//    private ObservableList<Product> productList = FXCollections.observableArrayList();
//
//    @FXML
//    public void initialize() {
//        // Set up the columns to map correctly with the Product class properties
//        idColumn.setCellValueFactory(new PropertyValueFactory<>("productID"));
//        stockColumn.setCellValueFactory(new PropertyValueFactory<>("stock"));
//        imageColumn.setCellValueFactory(new PropertyValueFactory<>("image"));
//        nameColumn.setCellValueFactory(new PropertyValueFactory<>("productName"));
//        describeColumn.setCellValueFactory(new PropertyValueFactory<>("description"));
//        quantityColumn.setCellValueFactory(new PropertyValueFactory<>("quality"));
//        priceColumn.setCellValueFactory(new PropertyValueFactory<>("price"));
//
//        // Load products from the database
//        loadProducts();
//
//        // Set the items for the TableView
//        productTable.setItems(productList);
//    }
//
//    private void loadProducts() {
//        String query = "SELECT productID, stock, image, productName, description, quality, price FROM Products";
//
//        try (Connection connection = ConectionJDBC.getConnection(); // Sử dụng kết nối từ lớp khác
//             PreparedStatement statement = connection.prepareStatement(query);
//             ResultSet resultSet = statement.executeQuery()) {
//
//            while (resultSet.next()) {
//                int productID = resultSet.getInt("productID");
//                boolean stock = resultSet.getBoolean("stock");
//                Image image = new Image(resultSet.getString("image")); // Điều chỉnh nếu cần
//                String productName = resultSet.getString("productName");
//                String description = resultSet.getString("description");
//                int quality = resultSet.getInt("quality");
//                double price = resultSet.getDouble("price");
//
//                Product product = new Product(productID, stock, image, productName, description, quality, price);
//                productList.add(product);
//            }
//        } catch (SQLException e) {
//            e.printStackTrace();
//        }
//    }
}
