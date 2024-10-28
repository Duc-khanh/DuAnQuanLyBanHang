package org.example.laptopthachthat;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import org.example.laptopthachthat.Product;
import org.example.laptopthachthat.ProductAdd;

import java.sql.SQLException;
import java.util.List;

public class ProductController {
    @FXML
    private TableView<Product> tableView;
    @FXML
    private TableColumn<Product, Integer> idColumn;
    @FXML
    private TableColumn<Product, Boolean> stocKColumn;
    @FXML
    private TableColumn<Product, String> nameColumn;
    @FXML
    private TableColumn<Product, String> descriptionColumn;
    @FXML
    private TableColumn<Product, Integer> quantityColumn;
    @FXML
    private TableColumn<Product, Double> priceColumn;

    private ObservableList<Product> productList = FXCollections.observableArrayList();

    @FXML
    public void initialize() {
        idColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
        stocKColumn.setCellValueFactory(new PropertyValueFactory<>("stocK"));
        nameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        descriptionColumn.setCellValueFactory(new PropertyValueFactory<>("description"));
        quantityColumn.setCellValueFactory(new PropertyValueFactory<>("quantity"));
        priceColumn.setCellValueFactory(new PropertyValueFactory<>("price"));

        try {
            loadProducts();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        tableView.setItems(productList);
    }

    private void loadProducts() throws SQLException {
        ProductAdd productDAO = new ProductAdd();
        List<Product> products = productDAO.getProduct();
        productList.addAll(products);
    }
}
