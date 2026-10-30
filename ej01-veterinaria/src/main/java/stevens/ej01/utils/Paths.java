package stevens.ej01.utils;

import java.nio.file.Path;

public final class Paths {

    public static final Path MASCOTAS = Path.of("mascotas.json").toAbsolutePath();
    public static final Path DUENIOS = Path.of("duenios.json").toAbsolutePath();

    // Vistas FXML
    public static final String MASCOTA_VIEW = "/stevens/ej01/Main.fxml";
    public static final String DUENIO_VIEW = "/stevens/ej01/Duenio.fxml";

    private Paths() {
    }
}