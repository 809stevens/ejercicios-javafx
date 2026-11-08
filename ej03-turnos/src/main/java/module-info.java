module stevens.ej03 {
    requires javafx.controls;
    requires javafx.fxml;
    requires com.google.gson;
    requires java.desktop;

    exports stevens.ej03.application to javafx.graphics;
    opens stevens.ej03.controllers to javafx.fxml;
    opens stevens.ej03.model to javafx.fxml, javafx.controls, com.google.gson;
}
