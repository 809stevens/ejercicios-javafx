package stevens.ej09;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.IOException;

/**
 * Clase de arranque de JavaFX. Ya esta completa.
 */
public class App extends Application {

    private static final String VISTA = "/stevens/ej09/Main.fxml";

    public static App app;
    public Stage stageWindow;

    @Override
    public void start(Stage stage) throws IOException {
        app = this;
        stageWindow = stage;
        stage.setTitle("Ejercicio 9 - Catalogo de peliculas");
        setScene(VISTA);

        stage.show();
    }

    public FXMLLoader setScene(String path) {
        FXMLLoader loader = new FXMLLoader(getClass().getResource(path));
        try {
            AnchorPane pane = loader.load();
            stageWindow.setScene(new Scene(pane));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return loader;
    }
}
