package stevens.ej06.util;

import java.nio.file.Path;

public final class Paths {

    public static final Path FIJOS = Path.of("gastosFijos.json").toAbsolutePath();
    public static final Path VARIABLES = Path.of("gastosVariables.json").toAbsolutePath();


    public static final String MAIN_VIEW = "/stevens/ej06/Main-view.fxml";

    private Paths(){

    }
}
