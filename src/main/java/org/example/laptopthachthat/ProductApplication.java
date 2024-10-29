package org.example.laptopthachthat;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class ProductApplication extends Application {
    @Override
    public void start(Stage primaryStage) throws Exception {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("User.fxml"));
        primaryStage.setScene(new Scene(loader.load()));
        primaryStage.setTitle("Danh sách sản phẩm");
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}

