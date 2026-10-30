module stevens.ej05 {
    requires javafx.controls;
    requires javafx.fxml;
    requires com.google.gson;

    exports stevens.ej05.application to javafx.graphics;
    opens stevens.ej05.controllers to javafx.fxml;
    opens stevens.ej05.model to javafx.fxml, javafx.controls, com.google.gson;
}
