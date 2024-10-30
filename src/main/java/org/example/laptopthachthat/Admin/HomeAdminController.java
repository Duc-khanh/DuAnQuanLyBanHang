package org.example.laptopthachthat.Admin;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import org.example.laptopthachthat.ConectionJDBC;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

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

    @FXML
    public void initialize() {

        idColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
        imageColumn.setCellValueFactory(new PropertyValueFactory<>("image"));
        nameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        describeColumn.setCellValueFactory(new PropertyValueFactory<>("describe"));
        quantityColumn.setCellValueFactory(new PropertyValueFactory<>("quantity"));
        stockColumn.setCellValueFactory(new PropertyValueFactory<>("stock"));
        priceColumn.setCellValueFactory(new PropertyValueFactory<>("price"));


        centerColumnData(idColumn);
        centerColumnData(stockColumn);
        centerColumnData(nameColumn);
        centerColumnData(describeColumn);
        centerColumnData(quantityColumn);
        centerColumnData(priceColumn);

        imageColumn.setCellFactory(column -> new TableCell<>() {
            private final ImageView imageView = new ImageView();

            @Override
            protected void updateItem(ImageView item, boolean empty) {
                super.updateItem(item, empty);
                if (empty || item == null) {
                    setGraphic(null);
                } else {
                    imageView.setImage(item.getImage());
                    imageView.setFitHeight(145);
                    imageView.setFitWidth(150);
                    setGraphic(imageView);
                    setAlignment(Pos.CENTER);
                }
            }
        });


        loadProducts();

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

    private <T> void centerColumnData(TableColumn<Product, T> column) {
        column.setCellFactory(tc -> {
            TableCell<Product, T> cell = new TableCell<>() {
                @Override
                protected void updateItem(T item, boolean empty) {
                    super.updateItem(item, empty);
                    if (empty || item == null) {
                        setText(null);
                    } else {
                        setText(item.toString());
                        setAlignment(Pos.CENTER); //
                    }
                }
            };
            return cell;
        });
    }
    @FXML
    private void showTableView() {
        productTable.setVisible(true);
    }

}
