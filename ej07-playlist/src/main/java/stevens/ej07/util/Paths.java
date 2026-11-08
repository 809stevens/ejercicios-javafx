package stevens.ej07.util;

import java.nio.file.Path;

public final class Paths {
    public static final Path CANCION = Path.of("cancion.json").toAbsolutePath();
    public static final Path PODCAST = Path.of("podcast.json").toAbsolutePath();

    private Paths(){

    }

}
