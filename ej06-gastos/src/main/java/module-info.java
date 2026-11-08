module stevens.ej06 {
    requires javafx.controls;
    requires javafx.fxml;
    requires com.google.gson;
    requires com.google.errorprone.annotations;

    exports stevens.ej06.application to javafx.graphics;
    exports stevens.ej06.dao;
    exports stevens.ej06.repository;
    opens stevens.ej06.controller to javafx.fxml;
    opens stevens.ej06.model to javafx.fxml, javafx.controls, com.google.gson;
}
