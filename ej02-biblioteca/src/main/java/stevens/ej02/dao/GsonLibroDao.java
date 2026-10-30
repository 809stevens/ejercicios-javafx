package stevens.ej02.dao;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonParseException;
import com.google.gson.TypeAdapter;
import com.google.gson.reflect.TypeToken;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonWriter;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import stevens.ej02.model.Libro;

import java.io.IOException;
import java.io.Reader;
import java.io.Writer;
import java.lang.reflect.Type;
import java.nio.charset.StandardCharsets;
import java.nio.file.AtomicMoveNotSupportedException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;
import java.util.List;

public class GsonLibroDao implements LibroDao {

    /**
     * Gson no sabe serializar las propiedades de JavaFX: intenta acceder por reflexión a sus
     * campos internos (bean, helper...) y falla porque el módulo javafx.base no está abierto.
     * Con este adaptador la propiedad se guarda como un simple true/false.
     */
    private static final TypeAdapter<BooleanProperty> ADAPTADOR_BOOLEAN_PROPERTY = new TypeAdapter<>() {
        @Override
        public void write(JsonWriter salida, BooleanProperty valor) throws IOException {
            salida.value(valor != null && valor.get());
        }

        @Override
        public BooleanProperty read(JsonReader entrada) throws IOException {
            return new SimpleBooleanProperty(entrada.nextBoolean());
        }
    };

    private final Path archivo;
    private final Type tipoLista;
    private final Gson gson = new GsonBuilder()
            .setPrettyPrinting()
            .registerTypeHierarchyAdapter(BooleanProperty.class, ADAPTADOR_BOOLEAN_PROPERTY)
            .create();

    public GsonLibroDao(Path archivo, Class<Libro> clase) {
        this.archivo = archivo;
        this.tipoLista = TypeToken.getParameterized(List.class, clase).getType();
    }

    @Override
    public List<Libro> cargar() throws PersistenciaException {
        if (!Files.exists(archivo)) {
            return new ArrayList<>();
        }
        try (Reader lector = Files.newBufferedReader(archivo, StandardCharsets.UTF_8)) {
            List<Libro> libros = gson.fromJson(lector, tipoLista);
            if (libros == null) {
                return new ArrayList<>();
            }
            return new ArrayList<>(libros);
        } catch (JsonParseException e) {
            throw new PersistenciaException(
                    "El archivo " + archivo.getFileName() + " está dañado o incompleto. "
                            + "Corrígelo o bórralo para empezar de cero. Detalle: " + e.getMessage(), e);
        } catch (IOException e) {
            throw new PersistenciaException("No se pudo leer " + archivo.getFileName() + ": " + e.getMessage(), e);
        }
    }

    @Override
    public void guardar(List<Libro> libros) throws PersistenciaException {
        Path destino = archivo.toAbsolutePath();
        try {
            Path carpeta = destino.getParent();
            if (carpeta != null) {
                Files.createDirectories(carpeta);
            }
            // Se escribe primero en un archivo temporal y luego se reemplaza, para que una
            // interrupción a mitad de escritura no deje libros.json truncado.
            Path temporal = destino.resolveSibling(destino.getFileName() + ".tmp");
            try (Writer escritor = Files.newBufferedWriter(temporal, StandardCharsets.UTF_8)) {
                gson.toJson(libros, tipoLista, escritor);
            }
            try {
                Files.move(temporal, destino,
                        StandardCopyOption.REPLACE_EXISTING, StandardCopyOption.ATOMIC_MOVE);
            } catch (AtomicMoveNotSupportedException e) {
                Files.move(temporal, destino, StandardCopyOption.REPLACE_EXISTING);
            }
        } catch (IOException | JsonParseException e) {
            throw new PersistenciaException("No se pudo guardar " + archivo.getFileName() + ": " + e.getMessage(), e);
        }
    }
}