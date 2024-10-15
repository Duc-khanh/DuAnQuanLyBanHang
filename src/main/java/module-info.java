module org.example.laptopthachthat {
    requires javafx.controls;
    requires javafx.fxml;


    opens org.example.laptopthachthat to javafx.fxml;
    exports org.example.laptopthachthat;
}