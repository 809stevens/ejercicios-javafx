module stevens.ej08 {
    requires javafx.controls;
    requires javafx.fxml;
    requires com.google.gson;
    requires java.desktop;

    exports stevens.ej08.application to javafx.graphics;
    opens stevens.ej08.controllers to javafx.fxml;
    opens stevens.ej08.model to javafx.fxml, javafx.controls, com.google.gson;
    exports stevens.ej08.dao;
    exports stevens.ej08.repository;
}