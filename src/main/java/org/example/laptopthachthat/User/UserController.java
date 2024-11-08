package org.example.laptopthachthat.User;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;
import org.example.laptopthachthat.Admin.Product;
import org.example.laptopthachthat.ConectionJDBC;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class UserController {
    @FXML
    private TableView<Product> productTable;
    @FXML
    private TableColumn<Product, Integer> idColumn;
    @FXML
    private TableColumn<Product, Boolean> stockColumn;
    @FXML
    private TableColumn<Product, ImageView> imageColumn;
    @FXML
    private TableColumn<Product, String> nameColumn;
    @FXML
    private TableColumn<Product, String> describeColumn;
    @FXML
    private TableColumn<Product, Integer> quantityColumn;
    @FXML
    private TableColumn<Product, Double> priceColumn;

    private ObservableList<Product> productList = FXCollections.observableArrayList();
    @FXML
    public void showProduct() {
        productTable.setVisible(true);
    }

    @FXML
    public void initialize() {
        idColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
        stockColumn.setCellValueFactory(new PropertyValueFactory<>("stock"));
        imageColumn.setCellValueFactory(new PropertyValueFactory<>("image"));
        nameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        describeColumn.setCellValueFactory(new PropertyValueFactory<>("describe"));
        quantityColumn.setCellValueFactory(new PropertyValueFactory<>("quantity"));
        priceColumn.setCellValueFactory(new PropertyValueFactory<>("price"));

        idColumn.getStyleClass().add("centered-column");
        stockColumn.getStyleClass().add("centered-column");
        nameColumn.getStyleClass().add("centered-column");
        describeColumn.getStyleClass().add("centered-column");
        quantityColumn.getStyleClass().add("centered-column");
        priceColumn.getStyleClass().add("centered-column");

        productTable.getStylesheets().add(getClass().getResource("/org/example/laptopthachthat/product.css").toExternalForm());

        loadProducts();
        productTable.setVisible(false);


        productTable.setItems(productList);
    }

    private void loadProducts() {
        productList.clear();
        String query = "SELECT productID, stock, image, productName, description, quality, price FROM Products";

        try (Connection connection = ConectionJDBC.getConnection();
             PreparedStatement statement = connection.prepareStatement(query);
             ResultSet resultSet = statement.executeQuery()) {

            while (resultSet.next()) {
                int productID = resultSet.getInt("productID");
                boolean stock = resultSet.getBoolean("stock");
                String imageURL = resultSet.getString("image");

                Image image;
                try {
                    if (imageURL == null || imageURL.isEmpty() || !isValidURL(imageURL)) {
                        throw new IllegalArgumentException("Invalid image URL for product ID: " + productID);
                    }
                    image = new Image(imageURL, true);
                } catch (Exception e) {
                    System.out.println("Invalid image URL for product ID: " + productID + ". Using default image.");
                    URL defaultImageUrl = getClass().getResource("/path/to/default/image.png");
                    if (defaultImageUrl != null) {
                        image = new Image(defaultImageUrl.toExternalForm());
                    } else {
                        System.err.println("Default image not found at the specified path.");

                        image = new Image("file:/absolute/path/to/local/image.png");
                    }
                }

                String productName = resultSet.getString("productName");
                String description = resultSet.getString("description");
                int quality = resultSet.getInt("quality");
                double price = resultSet.getDouble("price");

                ImageView imageView = new ImageView(image);
                imageView.setFitHeight(145);
                imageView.setFitWidth(150);

                Product product = new Product(productID, productName, price, quality, description, stock, imageView);
                productList.add(product);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private boolean isValidURL(String url) {
        try {
            new URL(url).toURI();
            return true;
        } catch (Exception e) {
            return false;
        }
    }
    @FXML
    private void BackToSignin(ActionEvent event) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Log out");
        alert.setHeaderText("Are you sure you want to log out?");

        alert.showAndWait().ifPresent(response -> {
            if (response == ButtonType.OK) {
                try {
                    Parent root = FXMLLoader.load(getClass().getResource("/org/example/laptopthachthat/Login.fxml"));
                    Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
                    stage.setScene(new Scene(root));
                    stage.show();

                    System.out.println("Signed out successfully.");
                } catch (IOException e) {
                    System.err.println("Error returning to the login page: " + e.getMessage());
                    e.printStackTrace();
                }
            } else {
                System.out.println("Cancel logout.");
            }
        });
    }

}
