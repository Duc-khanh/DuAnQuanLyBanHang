module org.example.laptopthachthat {

    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;
    requires java.desktop;

    opens org.example.laptopthachthat to javafx.fxml;
    exports org.example.laptopthachthat;





    exports org.example.laptopthachthat.Admin;
    opens org.example.laptopthachthat.Admin to javafx.fxml;

    exports org.example.laptopthachthat.User;
    opens org.example.laptopthachthat.User to javafx.fxml;
}
