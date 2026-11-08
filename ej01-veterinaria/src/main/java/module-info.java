module stevens.ej01 {
    requires javafx.controls;
    requires javafx.fxml;
    requires com.google.gson;
    requires javafx.base;

    exports stevens.ej01.application to javafx.graphics;
    opens stevens.ej01.controllers to javafx.fxml;
    opens stevens.ej01.model to javafx.fxml, javafx.controls, javafx.base, com.google.gson;
    exports stevens.ej01.dao;
    exports stevens.ej01.repository;
}