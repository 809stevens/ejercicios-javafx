package stevens.ej04.utils;

import java.nio.file.Path;

public final class Paths {

    public static final Path PERECEDEROS = Path.of("perecederos.json").toAbsolutePath();
    public static final Path NO_PERECEDEROS = Path.of("no_perecederos.json").toAbsolutePath();
    public static final String MAIN_VIEW = "/stevens/ej04/Main-view.fxml";

    private Paths() {
    }
}
