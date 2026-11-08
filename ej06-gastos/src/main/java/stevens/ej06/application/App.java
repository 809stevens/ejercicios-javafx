package stevens.ej06.application;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import stevens.ej06.util.Paths;

import java.net.URL;

/**
 * Aplicación JavaFX. Carga la vista principal.
 * La clase de arranque es Launcher; no necesitas modificarla.
 */
public class App extends Application {

    @Override
    public void start(Stage stage) throws Exception {
        URL vista = App.class.getResource(Paths.MAIN_VIEW);
        if (vista == null) {
            throw new IllegalStateException("No se encontró la vista: " + Paths.MAIN_VIEW);
        }
        Parent root = FXMLLoader.load(vista);
        stage.setTitle("Ejercicio 6 - Gastos personales");
        stage.setScene(new Scene(root));
        stage.show();
    }
}
