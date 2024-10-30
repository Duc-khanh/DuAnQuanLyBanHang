//package org.example.laptopthachthat;
//
//import java.sql.Connection;
//import java.sql.PreparedStatement;
//import java.sql.ResultSet;
//import java.sql.SQLException;
//import java.util.ArrayList;
//import java.util.List;
//
//
//import javafx.collections.FXCollections;
//import javafx.collections.ObservableList;
//import java.sql.Connection;
//import java.sql.PreparedStatement;
//import java.sql.ResultSet;
//import java.sql.SQLException;
//public class ProductAdd {
//
//        public static ObservableList<Product> getProductList() {
//            ObservableList<Product> products = FXCollections.observableArrayList();
//
//            String query = "SELECT * FROM Products";
//
//            try (Connection conn = ConectionJDBC.getConnection();
//                 PreparedStatement stmt = conn.prepareStatement(query);
//                 ResultSet rs = stmt.executeQuery()) {
//
//                while (rs.next()) {
//                    int id = rs.getInt("productID");
//                    int stock = rs.getInt("stock");
//                    String image = rs.getString("Image");
//                    String name = rs.getString("productName");
//                    String description = rs.getString("description");
//                    int quality = rs.getInt("quality");
//                    double price = rs.getDouble("price");
//
//                    products.add(new Product(id, stock, image, name, description, quality, price));
//                }
//            } catch (SQLException e) {
//                e.printStackTrace();
//            }
//            return products;
//        }
//
//
//    public List<Product> getProduct() {
//    }
//}
