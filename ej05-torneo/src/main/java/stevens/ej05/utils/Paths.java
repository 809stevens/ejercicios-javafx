package stevens.ej05.utils;

import java.nio.file.Path;

public final class Paths {

    public static final Path JUGADORES = Path.of("jugadores.json").toAbsolutePath();
    public static final Path ENTRENADORES = Path.of("entrenadores.json").toAbsolutePath();
    public static final String MAIN_VIEW = "/stevens/ej05/Main-view.fxml";

    private Paths() {
    }
}
