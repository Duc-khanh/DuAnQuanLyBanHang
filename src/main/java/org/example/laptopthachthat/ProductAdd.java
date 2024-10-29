package org.example.laptopthachthat;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ProductAdd {
    public List<Product> getProduct() throws SQLException {
        List<Product> products = new ArrayList<>();
        ConectionJDBC c = new ConectionJDBC();
        Connection con = c.getConnection();
        String query = "SELECT * FROM products";
        PreparedStatement ps = con.prepareStatement(query);
        ResultSet rs = ps.executeQuery();
        while (rs.next()) {
            Product product = new Product();
            product.setId(rs.getInt("id"));
            product.setStock(rs.getString("Stock"));
            product.setName(rs.getString("name"));
            product.setDescribe(rs.getString("Describe"));
            product.setQuantity(rs.getInt("quantity"));
            product.setPrice(rs.getDouble("price"));
            products.add(product);
        }
        rs.close();
        ps.close();
        con.close();

        return products;


    }
}
