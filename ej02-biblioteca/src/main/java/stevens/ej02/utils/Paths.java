package stevens.ej02.utils;

import java.nio.file.Path;

public final class Paths {

    public static final Path LIBROS = Path.of("libros.json").toAbsolutePath();

    // Vistas FXML
    public static final String MAIN_VIEW = "/stevens/ej02/Main.fxml";
    public static final String LIBRO_VIEW = "/stevens/ej02/libro.fxml";
    public static final String DETALLES_VIEW = "/stevens/ej02/DetallesView.fxml";

    private Paths() {
    }
}