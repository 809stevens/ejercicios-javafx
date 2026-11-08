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

    private final Path archivo;
    private final Type tipoLista;
    private final Gson gson = new GsonBuilder().setPrettyPrinting().create();

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
        } catch (IOException |JsonParseException e) {
            throw new PersistenciaException("no se pudo leer "+archivo.getFileName()+": "+e.getMessage(),e);
        }
    }

    @Override
    public void guardar(List<Libro> libros) throws PersistenciaException {
        Path carpeta = archivo.toAbsolutePath().getParent();
        try {
            if (carpeta != null) {
                Files.createDirectories(carpeta);
            }
            try (Writer escritor = Files.newBufferedWriter(archivo, StandardCharsets.UTF_8)) {
                gson.toJson(libros, tipoLista, escritor);
            }
        } catch (IOException | JsonParseException e) {
            throw new PersistenciaException("No se pudo guardar " + archivo.getFileName() + ": " + e.getMessage(), e);
        }
    }
}