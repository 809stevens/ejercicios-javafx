package stevens.ej08.utils;

import java.nio.file.Path;

public final class Paths {
    public static final String MAIN_VIEW = "/stevens/ej08/Main.fxml";
    public static final Path CONTACTOS = Path.of("contactos.json").toAbsolutePath();

    private Paths() {
    }
}
