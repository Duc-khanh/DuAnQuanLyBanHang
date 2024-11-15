package org.example.laptopthachthat.Admin;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import org.example.laptopthachthat.ConectionJDBC;
import org.example.laptopthachthat.Main;
import org.example.laptopthachthat.User.User;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class UserDisplay {
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
            String query = "SELECT * FROM User WHERE role = 'User'";
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

    public void BackToUser(ActionEvent event) throws IOException {

        Parent root = FXMLLoader.load(Main.class.getResource("Admin/HomeAdmin.fxml"));
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        Scene scene = new Scene(root);
        stage.setTitle("Sign up");
        stage.setScene(scene);
        stage.show();
    }

}

