package stevens.ej02.application;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import stevens.ej02.model.GestorLibro;
import stevens.ej02.utils.Paths;

import java.io.IOException;

public class App extends Application {
    public static App app;
    public Stage stageWindow;

    public static void main(String[] args) {
        launch();
    }

    @Override
    public void start(Stage primaryStage) throws IOException {
        app = this;
        stageWindow = primaryStage;
        setScene(Paths.MAIN_VIEW);

        primaryStage.show();
    }



    public FXMLLoader setScene(String path){
        FXMLLoader loader = new FXMLLoader(getClass().getResource(path));

        try {
            AnchorPane pane = loader.load();
            Scene scene = new Scene(pane);
            stageWindow.setScene(scene);
            stageWindow.show();
        }catch (IOException e){
            e.printStackTrace();
        }
        return loader;
    }
}
