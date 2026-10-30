module stevens.ej04 {
    requires javafx.controls;
    requires javafx.fxml;
    requires com.google.gson;

    exports stevens.ej04.application to javafx.graphics;
    opens stevens.ej04.controllers to javafx.fxml;
    opens stevens.ej04.model to javafx.fxml, javafx.controls, com.google.gson;
}
