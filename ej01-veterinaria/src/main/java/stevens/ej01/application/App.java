package stevens.ej01.application;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import stevens.ej01.controllers.MainController;
import stevens.ej01.model.GestorVeterinaria;
import stevens.ej01.utils.Paths;
import java.io.IOException;

public class App extends Application {
    public static App app;
    public static GestorVeterinaria gestor = new GestorVeterinaria();
    public Stage stageWindow;

    public static void main(String[] args) {
        launch();
    }

    @Override
    public void start(Stage primaryStage) throws IOException {
        app = this;
        stageWindow = primaryStage;

        primaryStage.setMinWidth(600);
        primaryStage.setMinHeight(400);
        primaryStage.setMaxWidth(1200);
        primaryStage.setMaxHeight(900);
        setScene(Paths.MASCOTA_VIEW);

        primaryStage.show();
    }



    public void setScene(String path){
        FXMLLoader loader = new FXMLLoader(getClass().getResource(path));

        try {
            AnchorPane pane = loader.load();
            Scene scene = new Scene(pane);
            stageWindow.setScene(scene);
            stageWindow.show();
        }catch (IOException e){
            e.printStackTrace();
        }
    }
}
