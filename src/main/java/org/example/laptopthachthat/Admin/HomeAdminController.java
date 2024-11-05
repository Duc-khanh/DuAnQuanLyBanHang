package org.example.laptopthachthat.Admin;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import org.example.laptopthachthat.Admin.Product;
import org.example.laptopthachthat.ConectionJDBC;
import org.example.laptopthachthat.Main;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Optional;

public class HomeAdminController {
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
    @FXML
    private Button AddButton;
    @FXML
    private Button DeleteButton;
    @FXML
    private Button UpdateButton;

    private ObservableList<Product> productList = FXCollections.observableArrayList();

    @FXML
    public void initialize() {
        // Set up the columns to map correctly with the Product class properties
        idColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
        stockColumn.setCellValueFactory(new PropertyValueFactory<>("stock"));
        imageColumn.setCellValueFactory(new PropertyValueFactory<>("image"));
        nameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        describeColumn.setCellValueFactory(new PropertyValueFactory<>("describe"));
        quantityColumn.setCellValueFactory(new PropertyValueFactory<>("quantity"));
        priceColumn.setCellValueFactory(new PropertyValueFactory<>("price"));

        loadProducts();



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

    // Phương thức kiểm tra tính hợp lệ của URL
    private boolean isValidURL(String url) {
        try {
            new URL(url).toURI();
            return true;
        } catch (Exception e) {
            return false;
        }
    }
//sadasdad

    @FXML
    public void addProduct(String productName, double price, int quantity, String description, boolean stock, String imageURL) {
        String query = "INSERT INTO Products (productName, description, quality, price, stock, image) VALUES (?, ?, ?, ?, ?, ?)";

        try (Connection connection = ConectionJDBC.getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {

            statement.setString(1, productName);
            statement.setString(2, description);
            statement.setInt(3, quantity);
            statement.setDouble(4, price);
            statement.setBoolean(5, stock);
            statement.setString(6, imageURL);

            int rowsInserted = statement.executeUpdate();
            if (rowsInserted > 0) {
                System.out.println("The product has been successfully added to the database.");

                loadProducts();
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    public void showDisplayAdd() {
        Dialog<ButtonType> showAddDialog = new Dialog<>();
        showAddDialog.setTitle("Add Product");

        TextField nameField = new TextField();
        TextField descriptionField = new TextField();
        TextField quantityField = new TextField();
        TextField priceField = new TextField();
        TextField imageURLField = new TextField();
        CheckBox stockCheckBox = new CheckBox();

        GridPane grid = new GridPane();
        grid.setHgap(10);
        grid.setVgap(10);
        grid.add(new Label("Name"), 0, 0);
        grid.add(nameField, 1, 0);
        grid.add(new Label("Description"), 0, 1);
        grid.add(descriptionField, 1, 1);
        grid.add(new Label("Quantity"), 0, 2);
        grid.add(quantityField, 1, 2);
        grid.add(new Label("Price"), 0, 3);
        grid.add(priceField, 1, 3);
        grid.add(new Label("Image URL"), 0, 4);
        grid.add(imageURLField, 1, 4);
        grid.add(new Label("In Stock"), 0, 5);
        grid.add(stockCheckBox, 1, 5);

        showAddDialog.getDialogPane().setContent(grid);
        showAddDialog.getDialogPane().getButtonTypes().addAll(ButtonType.OK, ButtonType.CANCEL);

        Optional<ButtonType> result = showAddDialog.showAndWait();
        if (result.isPresent() && result.get() == ButtonType.OK) {
            String productName = nameField.getText();
            String description = descriptionField.getText();
            String quantityText = quantityField.getText();
            String priceText = priceField.getText();
            String imageURL = imageURLField.getText();
            boolean stock = stockCheckBox.isSelected();


            if (productName.isEmpty() || description.isEmpty() || quantityText.isEmpty() || priceText.isEmpty() || imageURL.isEmpty()) {
                showError("Incomplete Information", "Please fill in all fields before adding the product.");
                return;
            }

            try {
                int quantity = Integer.parseInt(quantityText);
                double price = Double.parseDouble(priceText);

                addProduct(productName, price, quantity, description, stock, imageURL);
            } catch (NumberFormatException e) {
                showError("Invalid Data", "Please enter valid numbers for quantity and price.");
            }
        }
    }


    private void showError(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    private void DeleteProduct(int productID, String productName, double price, int quantity, String description, boolean stock, ImageView imageView) {

        String query = "DELETE FROM Products WHERE productID = ?";

    }

    public void BackToSignin(ActionEvent event) throws IOException {

        Parent root = FXMLLoader.load(Main.class.getResource("Login.fxml"));
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        Scene scene = new Scene(root);
        stage.setTitle("Sign up");
        stage.setScene(scene);
        stage.show();
    }

    public void DeleteProduct(ActionEvent actionEvent) {
    }

    public void UpdateProduct(ActionEvent actionEvent) {
    }
}
