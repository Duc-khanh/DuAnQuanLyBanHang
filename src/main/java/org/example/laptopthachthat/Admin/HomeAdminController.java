package org.example.laptopthachthat.Admin;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import org.example.laptopthachthat.ConectionJDBC;
import org.example.laptopthachthat.Main;
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

    private ObservableList<Product> productList = FXCollections.observableArrayList();
//    @FXML
//    public void showProduct() {
//        productTable.setVisible(true);
//    }

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

        productTable.getStylesheets().add(getClass().getResource("/org/example/laptopthachthat/Admin/product.css").toExternalForm());

        loadProducts();
//        productTable.setVisible(false);


        productTable.setItems(productList);
    }

    private void loadProducts() {
        productList.clear();
        String query = "SELECT productID, stock, image, productName, description, quantity, price FROM Products";

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
    private <T> void centerColumnData(TableColumn<Product, T> column) {
        column.setCellFactory(tc -> new TableCell<>() {
            @Override
            protected void updateItem(T item, boolean empty) {
                super.updateItem(item, empty);
                if (empty || item == null) {
                    setText(null);
                } else {
                    setText(item.toString());
                    setAlignment(Pos.CENTER);
                }
            }
        });
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

    @FXML
    public void addProduct(String productName, double price, int quantity, String description, boolean stock, String imageURL) {
        String query = "INSERT INTO Products (productName, description, quantity, price, stock, image) VALUES (?, ?, ?, ?, ?, ?)";

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

    @FXML
    public void deleteProduct(ActionEvent actionEvent) {
        TextInputDialog inputDialog = new TextInputDialog();
        inputDialog.setTitle("Change product status");
        inputDialog.setHeaderText("Enter the product ID for which you want to change the status:");
        inputDialog.setContentText("Product ID:");

        Optional<String> result = inputDialog.showAndWait();
        if (result.isPresent()) {
            String input = result.get().trim();

            try {
                int productId = Integer.parseInt(input);

                Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                alert.setTitle("Change product status");
                alert.setHeaderText("Select a new status for the product with ID: " + productId);

                ButtonType availableButton = new ButtonType("Còn hàng");
                ButtonType outOfStockButton = new ButtonType("Hết hàng");
                ButtonType cancelButton = new ButtonType("Hủy", ButtonBar.ButtonData.CANCEL_CLOSE);

                alert.getButtonTypes().setAll(availableButton, outOfStockButton, cancelButton);

                Optional<ButtonType> statusResult = alert.showAndWait();
                if (statusResult.isPresent() && statusResult.get() != cancelButton) {
                    boolean newStockStatus = (statusResult.get() == availableButton);
                    updateProductStatus(productId, newStockStatus);
                }
            } catch (NumberFormatException e) {
                showError("Invalid ID", "Please enter a valid product ID (integer).");
            }
        }
    }


    private void updateProductStatus(int productId, boolean stock) {
        String query = "UPDATE Products SET stock = ? WHERE productID = ?";

        try (Connection connection = ConectionJDBC.getConnection(); PreparedStatement statement = connection.prepareStatement(query)) {

            statement.setBoolean(1, stock);
            statement.setInt(2, productId);

            int rowsUpdated = statement.executeUpdate();
            if (rowsUpdated > 0) {
                System.out.println("Product status has been updated.");
                loadProducts();
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void updateProduct() {
        TextField productIdField = new TextField();

        Dialog<ButtonType> updateDialog = new Dialog<>();
        updateDialog.setTitle("Update");

        TextField nameField = new TextField();
        TextField descriptionField = new TextField();
        TextField quantityField = new TextField();
        TextField priceField = new TextField();
        TextField imageURLField = new TextField();
        CheckBox stockCheckBox = new CheckBox();

        GridPane grid = new GridPane();
        grid.setHgap(10);
        grid.setVgap(10);
        grid.add(new Label("ID sản phẩm"), 0, 0);
        grid.add(productIdField, 1, 0);
        grid.add(new Label("Tên sản phẩm"), 0, 1);
        grid.add(nameField, 1, 1);
        grid.add(new Label("Mô tả"), 0, 2);
        grid.add(descriptionField, 1, 2);
        grid.add(new Label("Số lượng"), 0, 3);
        grid.add(quantityField, 1, 3);
        grid.add(new Label("Giá"), 0, 4);
        grid.add(priceField, 1, 4);
        grid.add(new Label("Đường dẫn hình ảnh"), 0, 5);
        grid.add(imageURLField, 1, 5);
        grid.add(new Label("Còn hàng"), 0, 6);
        grid.add(stockCheckBox, 1, 6);

        updateDialog.getDialogPane().setContent(grid);
        updateDialog.getDialogPane().getButtonTypes().addAll(ButtonType.OK, ButtonType.CANCEL);

        Optional<ButtonType> result = updateDialog.showAndWait();
        if (result.isPresent() && result.get() == ButtonType.OK) {
            String productIdText = productIdField.getText();
            String productName = nameField.getText();
            String description = descriptionField.getText();
            String quantityText = quantityField.getText();
            String priceText = priceField.getText();
            String imageURL = imageURLField.getText();
            boolean stock = stockCheckBox.isSelected();

            if (productIdText.isEmpty() || productName.isEmpty() || description.isEmpty() || quantityText.isEmpty() || priceText.isEmpty() || imageURL.isEmpty()) {
                showError("Information is incomplete", "Please fill in all information before updating the product.");
                return;
            }

            try {
                int productId = Integer.parseInt(productIdText);
                int quantity = Integer.parseInt(quantityText);
                double price = Double.parseDouble(priceText);
                updateProduct(productId, productName, price, quantity, description, stock, imageURL);
            } catch (NumberFormatException e) {
                showError("Invalid data", "Please enter a valid number for ID, quantity and price.");
            }
        }
    }

    private void updateProduct(int productId, String productName, double price, int quantity, String description, boolean stock, String imageURL) {
        String query = "UPDATE Products SET productName = ?, description = ?, quantity = ?, price = ?, stock = ?, image = ? WHERE productID = ?";

        try (Connection connection = ConectionJDBC.getConnection(); PreparedStatement statement = connection.prepareStatement(query)) {

            statement.setString(1, productName);
            statement.setString(2, description);
            statement.setInt(3, quantity);
            statement.setDouble(4, price);
            statement.setBoolean(5, stock);
            statement.setString(6, imageURL);
            statement.setInt(7, productId);

            int rowsUpdated = statement.executeUpdate();
            if (rowsUpdated > 0) {
                System.out.println("The product has been updated.");
                loadProducts();
            }

        } catch (SQLException e) {
            e.printStackTrace();
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

    public void Account(ActionEvent event) throws IOException {

        Parent root = FXMLLoader.load(Main.class.getResource("/org/example/laptopthachthat/User/User.fxml"));
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        Scene scene = new Scene(root);
        stage.setTitle("User Profile");
        stage.setScene(scene);
        stage.show();
    }
}
