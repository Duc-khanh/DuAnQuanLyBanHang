//package org.example.laptopthachthat.Admin;
//
//import javafx.collections.FXCollections;
//import javafx.collections.ObservableList;
//import javafx.fxml.FXML;
//import javafx.scene.control.TableColumn;
//import javafx.scene.control.TableView;
//import javafx.scene.control.cell.PropertyValueFactory;
//import javafx.scene.image.Image;
//import org.example.laptopthachthat.ConectionJDBC;
//import org.example.laptopthachthat.Admin.Product;
//
//import java.sql.Connection;
//import java.sql.PreparedStatement;
//import java.sql.ResultSet;
//import java.sql.SQLException;
//
//
//public class AdminController {
//
//    @FXML
//    private TableView<Product> tableView;
//
//    @FXML
//    private TableColumn<Product, Integer> idColumn;
//
//    @FXML
//    private TableColumn<Product, Boolean> stocKColumn;
//
//    @FXML
//    private TableColumn<Product, Image> imageColumn;
//
//    @FXML
//    private TableColumn<Product, String> nameColumn;
//
//    @FXML
//    private TableColumn<Product, String> descriptionColumn;
//
//    @FXML
//    private TableColumn<Product, Integer> quantityColumn;
//
//    @FXML
//    private TableColumn<Product, Double> priceColumn;
//
//    private ObservableList<Product> productList = FXCollections.observableArrayList();
//
//    @FXML
//    public void initialize() throws SQLException {
//        idColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
//        stocKColumn.setCellValueFactory(new PropertyValueFactory<>("Stock"));
//        imageColumn.setCellValueFactory(new PropertyValueFactory<>("imagePath"));
//        nameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
//        descriptionColumn.setCellValueFactory(new PropertyValueFactory<>("Describe"));
//        quantityColumn.setCellValueFactory(new PropertyValueFactory<>("quantity"));
//        priceColumn.setCellValueFactory(new PropertyValueFactory<>("price"));
//
//
//        tableView.setItems(getProduct());
//    }
//
//
//    public ObservableList<Product> getProduct() throws SQLException {
//        ObservableList<Product> products = FXCollections.observableArrayList();
//        ConectionJDBC c = new ConectionJDBC();
//        Connection con = c.getConnection();
//        String query = "SELECT * FROM products";
//        try {
//            PreparedStatement ps = con.prepareStatement(query);
//            ResultSet rs = ps.executeQuery();
//            while (rs.next()) {
//                int id = rs.getInt("productID");
//                String name = rs.getString("productName");
//                double price = rs.getDouble("price");
//                int quantity = rs.getInt("quality");
//                String description = rs.getString("description");
//                String status = rs.getString("stock");
//                String image = rs.getString("Image");
////
////                products.add(new Product(id, name, price, quantity, description, status, image));
//            }
//        } catch (SQLException e) {
//            e.printStackTrace();
//        }
//        return products;
//    }
//}
//
//
