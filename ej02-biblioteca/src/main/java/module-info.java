module stevens.ej02 {
    requires javafx.controls;
    requires javafx.fxml;
    requires com.google.gson;
    requires javafx.base;

    exports stevens.ej02.application to javafx.graphics;
    opens stevens.ej02.controllers to javafx.fxml;
    opens stevens.ej02.model to javafx.fxml, javafx.controls, javafx.base, com.google.gson;
    exports stevens.ej02.dao;
    exports stevens.ej02.repository;
}