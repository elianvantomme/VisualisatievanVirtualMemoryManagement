module com.example.practicum2 {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.example.practicum2 to javafx.fxml;
    exports com.example.practicum2;
}