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
import org.example.laptopthachthat.ConectionJDBC;
import org.example.laptopthachthat.Admin.Product;
import org.example.laptopthachthat.Main;

import java.io.IOException;
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
    private Label showAdd;

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

        // Load products from the database
        loadProducts();

        // Set the items for the TableView
        productTable.setItems(productList);
    }

    private void loadProducts() {
        String query = "SELECT productID, stock, image, productName, description, quality, price FROM Products";

        try (Connection connection = ConectionJDBC.getConnection();
             PreparedStatement statement = connection.prepareStatement(query);
             ResultSet resultSet = statement.executeQuery()) {

            while (resultSet.next()) {
                int productID = resultSet.getInt("productID");
                boolean stock = resultSet.getBoolean("stock");
                String imageURL = resultSet.getString("image");
                Image image = new Image(imageURL);
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
                System.out.println("Sản phẩm đã được thêm thành công vào cơ sở dữ liệu.");

                // Cập nhật danh sách sản phẩm
                Image image = new Image(imageURL);
                ImageView imageView = new ImageView(image);
                imageView.setFitHeight(145);
                imageView.setFitWidth(150);

                Product product = new Product(0, productName, price, quantity, description, stock, imageView);
                productList.add(product); // Cập nhật danh sách hiển thị
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void showDisplayAdd() {
        Dialog<ButtonType> showAddDialog = new Dialog<>();
        showAddDialog.setTitle("Add Product");

        // Các trường nhập liệu cho sản phẩm
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
            // Lấy dữ liệu từ các trường và thêm sản phẩm
            String productName = nameField.getText();
            String description = descriptionField.getText();
            int quantity = Integer.parseInt(quantityField.getText());
            double price = Double.parseDouble(priceField.getText());
            boolean stock = stockCheckBox.isSelected();
            String imageURL = imageURLField.getText();

            // Thêm sản phẩm mới vào cơ sở dữ liệu và hiển thị trên TableView
            addProduct(productName, price, quantity, description, stock, imageURL);
        }
    }

    public void BackToSignin(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(Main.class.getResource("Login.fxml"));
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        Scene scene = new Scene(root);
        stage.setTitle("Sign up");
        stage.setScene(scene);
        stage.show();
    }
}
