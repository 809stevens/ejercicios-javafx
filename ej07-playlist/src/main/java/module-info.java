module stevens.ej07 {
    requires javafx.fxml;
    requires javafx.graphics;
    requires javafx.controls;
    requires com.google.gson;

    opens stevens.ej07.model to com.google.gson;
    opens stevens.ej07.controller to javafx.fxml;

    exports stevens.ej07;


}