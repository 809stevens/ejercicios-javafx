package stevens.ej07;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.net.URL;

/**
 * Aplicación JavaFX. Carga la vista principal.
 * La clase de arranque es Launcher.
 */
public class App extends Application {

    private static final String VISTA = "/stevens/ej07/Main-view.fxml";

    @Override
    public void start(Stage stage) throws Exception {
        URL vista = App.class.getResource(VISTA);
        if (vista == null) {
            throw new IllegalStateException("No se encontró la vista: " + VISTA);
        }
        Parent root = FXMLLoader.load(vista);
        stage.setTitle("Ejercicio 7 - Playlist de música");
        stage.setScene(new Scene(root));
        stage.show();
    }
}
