package stevens.ej09.application;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import stevens.ej09.utils.Paths;

import java.io.IOException;

/**
 * Clase de arranque de JavaFX. Ya esta completa.
 */
public class App extends Application {

    public static App app;
    public Stage stageWindow;

    @Override
    public void start(Stage stage) throws IOException {
        app = this;
        stageWindow = stage;
        stage.setTitle("Ejercicio 9 - Catalogo de peliculas");
        setScene(Paths.MAIN_VIEW);

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
