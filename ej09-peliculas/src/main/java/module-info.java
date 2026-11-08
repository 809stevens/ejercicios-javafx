module stevens.ej09 {
    requires javafx.controls;
    requires javafx.fxml;
    requires com.google.gson;
    requires javafx.base;

    exports stevens.ej09.application to javafx.graphics;
    opens stevens.ej09.controllers to javafx.fxml;
    opens stevens.ej09.model to javafx.fxml, javafx.controls, javafx.base, com.google.gson;
    exports stevens.ej09.dao;
    exports stevens.ej09.repository;
}
