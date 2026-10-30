module stevens.ej06 {
    requires javafx.controls;
    requires javafx.fxml;
    requires com.google.gson;

    exports stevens.ej06.application to javafx.graphics;
    opens stevens.ej06.controllers to javafx.fxml;
    opens stevens.ej06.model to javafx.fxml, javafx.controls, com.google.gson;
}
