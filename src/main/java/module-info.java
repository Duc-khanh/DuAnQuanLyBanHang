module org.example.laptopthachthat {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;


    opens org.example.laptopthachthat to javafx.fxml;
    exports org.example.laptopthachthat;
}