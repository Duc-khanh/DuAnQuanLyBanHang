package org.example.laptopthachthat.User;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;
import org.example.laptopthachthat.Admin.Product;
import org.example.laptopthachthat.ConectionJDBC;
import org.example.laptopthachthat.LoggedInUser;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ProductDetailsController {

    @FXML
    private ImageView productImage;
    @FXML
    private Label productName;
    @FXML
    private Label productPrice;
    @FXML
    private Label productDescription;
    @FXML
    private Label productStock;
    @FXML
    private Label productQuantity;
    @FXML
    private TextField quantity;
    @FXML
    private TableView<Product> cartTableView;

    private ObservableList<Product> cartItems = FXCollections.observableArrayList();


    public void updateCartDisplay(List<Product> newCartItems) {
        cartItems.clear();
        cartItems.addAll(newCartItems);
        cartTableView.setItems(cartItems);
    }

    private Product product;
    private UserController userController;

    public void setProduct(Product product) {
        this.product = product;
        productImage.setImage(product.getImage().getImage());
        productName.setText(product.getName());
        productPrice.setText("Giá: " + product.getPrice() + " VND");
        productDescription.setText("Mô tả: " + product.getDescribe());
        productStock.setText("Còn hàng: " + (product.isStock() ? "Còn" : "Hết"));
        productQuantity.setText("Số lượng: " + product.getQuantity());

    }

    public void setUserController(UserController userController) {
        this.userController = userController;
    }


    @FXML
    private void HandleBuyButton() {
        if (product.isStock()) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Xác nhận mua hàng");
            alert.setHeaderText(null);
            alert.setContentText("Bạn đã mua sản phẩm: " + product.getName());

            alert.showAndWait();
            addCart();
        } else {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Thông báo");
            alert.setHeaderText(null);
            alert.setContentText("Sản phẩm hiện đã hết hàng!");
            alert.showAndWait();
        }


        Stage stage = (Stage) productImage.getScene().getWindow();
        stage.close();
    }

    private void addCart() {
        String Insert = "Insert into cart ( productID ,userID , quantity , price) values(? , ? ,?,?)";
        try {
            Connection connection = ConectionJDBC.getConnection();
            PreparedStatement statement = connection.prepareStatement(Insert);
            statement.setInt(1, product.getId());
            statement.setInt(2, LoggedInUser.getInstance().getUserID());
            statement.setInt(3, Integer.parseInt(quantity.getText()));
            statement.setDouble(4, product.getPrice());
            statement.executeUpdate();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
