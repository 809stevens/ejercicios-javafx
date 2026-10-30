package stevens.ej06.application;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.IOException;

public class app extends Application {

    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/Main.fxml"));
        AnchorPane pane = loader.load();
        Scene scene = new Scene(pane);
        stage.setScene(scene);
        stage.setTitle("Ejercicio 6 - Gastos personales");
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}
