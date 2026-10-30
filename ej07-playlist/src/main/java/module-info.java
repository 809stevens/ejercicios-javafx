module stevens.ej07 {
    requires javafx.controls;
    requires javafx.fxml;
    requires com.google.gson;

    exports stevens.ej07.application to javafx.graphics;
    opens stevens.ej07.controllers to javafx.fxml;
    opens stevens.ej07.model to javafx.fxml, javafx.controls, com.google.gson;
}
