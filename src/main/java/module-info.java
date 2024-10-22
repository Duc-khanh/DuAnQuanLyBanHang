
module org.example.laptopthachthat {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;
    requires java.desktop;


    opens org.example.laptopthachthat to javafx.fxml;
    exports org.example.laptopthachthat;
}