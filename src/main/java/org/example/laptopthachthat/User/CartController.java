package org.example.laptopthachthat.User;

import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import org.example.laptopthachthat.Admin.Product;
import org.example.laptopthachthat.ConectionJDBC;
import org.example.laptopthachthat.LoggedInUser;
import org.example.laptopthachthat.Main;

import java.io.IOException;
import java.sql.*;
import java.time.LocalDateTime;

public class CartController {
    @FXML
    private TableView<Cart> cartTable;
    @FXML
    private TableColumn<Cart, Integer> idColumn;
    @FXML
    private TableColumn<Cart, String> nameColumn;
    @FXML
    private TableColumn<Cart, Integer> quantityColumn;
    @FXML
    private TableColumn<Cart, Double> priceColumn;
    @FXML
    private TableColumn<Cart, Double> totalColumn;
    @FXML
    private Label totalAmount;


    public void initialize() throws SQLException {
        getDataCart();
        calculateTotalAmount();

    }

    public void getDataCart() throws SQLException {
        String query = "SELECT p.productName, p.price, c.quantity, c.cartID " +
                "FROM products p JOIN cart c ON p.productId = c.productId WHERE c.userID = ? ";
        Connection connection = ConectionJDBC.getConnection();
        PreparedStatement preparedStatement = connection.prepareStatement(query);
        preparedStatement.setInt(1, LoggedInUser.getInstance().getUserID());
        try (ResultSet resultSet = preparedStatement.executeQuery()) {
            while (resultSet.next()) {
                String productName = resultSet.getString("productName");
                Double price = resultSet.getDouble("price");
                int quantity = resultSet.getInt("quantity");
                int cartID = resultSet.getInt("cartID");

                Cart cart = new Cart(productName, quantity, price, cartID);
                cartTable.getItems().add(cart);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        setTableColumn();
    }

    private void setTableColumn() {
        idColumn.setCellValueFactory(cellData -> new SimpleIntegerProperty(cartTable.getItems().indexOf(cellData.getValue()) + 1).asObject());
        nameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        quantityColumn.setCellValueFactory(new PropertyValueFactory<>("quantity"));
        priceColumn.setCellValueFactory(new PropertyValueFactory<>("price"));
        totalColumn.setCellValueFactory(cellData -> {
            Cart cart = cellData.getValue();
            Double amount = cart.getPrice() * cart.getQuantity();
            return new ReadOnlyObjectWrapper<>(amount);
        });
    }

    private void calculateTotalAmount() {
        double total = 0.0;
        for (Cart cart : cartTable.getItems()) {
            total += cart.getPrice() * cart.getQuantity();
        }
        totalAmount.setText(String.format("%.2f", total));
    }


    public void handleCheckout(ActionEvent actionEvent) throws IOException, SQLException {
      if(showAlert("Bạn có chắc chắn muốn đặt đơn ?")){
          int orderID = addOrder(LoggedInUser.getInstance().getUserID(), LocalDateTime.now());
          for (Cart cartItem : cartTable.getItems()) {
              int quantity = cartItem.getQuantity();
              int cartID = cartItem.getCartID();
              int productID = getProductIDFromCart(cartID);
              addCart_Order(cartID, orderID, quantity);
              updateQuantityProduct(productID, quantity);

          }
          System.out.println("Thêm vào Cart_Order,Order,Cập nhật số lượng sản phẩm");
          showAlert("Đặt đơn hàng thành công");
          Main.changeScene("User/UserDisplay.fxml");
      }
    }
    private int getProductIDFromCart(int cartID) throws SQLException {
        String query = "SELECT productID FROM cart WHERE cartID = ?";
        Connection connection = ConectionJDBC.getConnection();
        PreparedStatement preparedStatement = connection.prepareStatement(query);
        preparedStatement.setInt(1, cartID);
        try (ResultSet resultSet = preparedStatement.executeQuery()){
            if (resultSet.next()) {
                return resultSet.getInt("productID");
            }else {
                throw new SQLException("Không tìm thấy productID của cartID");
            }
        }
        finally {
            preparedStatement.close();
        }
    }

    public void addCart_Order(int cartID , int orderID, int quantity) throws SQLException {
        String query ="INSERT INTO Cart_Order(cartID, orderID,quantity) values(?,?,?)";
        Connection connection = ConectionJDBC.getConnection();
        PreparedStatement preparedStatement = connection.prepareStatement(query);
        preparedStatement.setInt(1,cartID);
        preparedStatement.setInt(2,orderID);
        preparedStatement.setInt(3,quantity);
        preparedStatement.executeUpdate();
        preparedStatement.close();
    }
    public int addOrder(int userID, LocalDateTime orderDate ) throws SQLException {;
        try {
            String query = "INSERT INTO `Order`(userID, orderDate) values(?,?)";
            Connection connection =ConectionJDBC.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
            preparedStatement.setInt(1, userID);
            preparedStatement.setTimestamp(2, Timestamp.valueOf(orderDate));
            preparedStatement.executeUpdate();

            try (ResultSet generatedKeys = preparedStatement.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    return generatedKeys.getInt(1);
                } else {
                    throw new SQLException("Tạo đơn hàng thất bại. Không thể lấy khóa chính.");
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw new SQLException("Lỗi trong quá trình tạo đơn hàng: " + e.getMessage());
        }

    }
    public void updateQuantityProduct(int productID, int quantitySold) throws SQLException {
        String query = "Update products set `quantity` = quantity - ? where productID = ?";
        Connection connection = ConectionJDBC.getConnection();
        PreparedStatement preparedStatement = connection.prepareStatement(query);
        preparedStatement.setInt(1, quantitySold);
        preparedStatement.setInt(2, productID);
        int row =preparedStatement.executeUpdate();
        if (row > 0) {
            System.out.println("Cập nhật số lượng " + productID);
        }else {
            System.out.println("Không tìm thấy sản phẩm " + productID);
        }
    }
    public void handleTransitions(ActionEvent actionEvent) throws IOException {
        Main.changeScene("User/UserDisplay.fxml");
    }
    public boolean showAlert(String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Đặt hàng thành công");
        alert.setContentText(message);
        return alert.showAndWait().get() == ButtonType.OK;
    }
}

