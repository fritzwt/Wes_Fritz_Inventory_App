module com.example.c482new {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.example.c482new to javafx.fxml;
    exports com.example.c482new;
}