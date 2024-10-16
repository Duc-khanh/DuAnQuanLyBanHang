module com.example.duancanstrore {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;


    opens com.example.duancanstrore to javafx.fxml;
    exports com.example.duancanstrore;
}