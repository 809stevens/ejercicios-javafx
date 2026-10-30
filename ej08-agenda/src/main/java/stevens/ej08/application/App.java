package stevens.ej08.application;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Rectangle2D;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Screen;
import javafx.stage.Stage;
import stevens.ej08.utils.Paths;

import java.io.IOException;

public class App extends Application {

    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource(Paths.MAIN_VIEW));
        AnchorPane pane = loader.load();
        Scene scene = new Scene(pane);
        stage.setScene(scene);
        stage.setTitle("Agenda de contactos");
        Rectangle2D bounds = Screen.getPrimary().getVisualBounds();


        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}