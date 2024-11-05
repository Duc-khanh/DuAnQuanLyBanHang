package org.example.laptopthachthat.Admin;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import org.example.laptopthachthat.Admin.User;
import org.example.laptopthachthat.ConectionJDBC;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class UserController {
    @FXML
    private TableView<User> userTable;
    @FXML
    private TableColumn<User, Integer> userIDColumn;
    @FXML
    private TableColumn<User, String> roleColumn;
    @FXML
    private TableColumn<User, String> usernameColumn;
    @FXML
    private TableColumn<User, Integer> passwordColumn;
    @FXML
    private TableColumn<User, String> phoneNumberColumn;
    @FXML
    private TableColumn<User, String> stockColumn;
    @FXML
    private TableColumn<User, String> addressColumn;

    private ObservableList<User> userList = FXCollections.observableArrayList();


    public void initialize() {

        userIDColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
        roleColumn.setCellValueFactory(new PropertyValueFactory<>("role"));
        usernameColumn.setCellValueFactory(new PropertyValueFactory<>("username"));
        passwordColumn.setCellValueFactory(new PropertyValueFactory<>("password"));
        phoneNumberColumn.setCellValueFactory(new PropertyValueFactory<>("phoneNumber"));
        stockColumn.setCellValueFactory(new PropertyValueFactory<>("state"));
        addressColumn.setCellValueFactory(new PropertyValueFactory<>("address"));

        loadUserData();
    }


    private void loadUserData() {
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;

        try {
            connection = ConectionJDBC.getConnection();
            String query = "SELECT * FROM User WHERE role = 'Customer'";
            statement = connection.prepareStatement(query);
            resultSet = statement.executeQuery();


            while (resultSet.next()) {
                int userID = resultSet.getInt("userID");
                String role = resultSet.getString("role");
                String username = resultSet.getString("username");
                int password = resultSet.getInt("password");
                String address = resultSet.getString("address");
                String phoneNumber = resultSet.getString("phoneNumber");
                String state = resultSet.getString("state");

                User user = new User(userID, role, username, password, address, phoneNumber, state);
                userList.add(user);
            }


            userTable.setItems(userList);

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {

            try {
                if (resultSet != null) resultSet.close();
                if (statement != null) statement.close();
                if (connection != null) connection.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
}
