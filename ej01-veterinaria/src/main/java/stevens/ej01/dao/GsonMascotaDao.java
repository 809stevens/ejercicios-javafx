package stevens.ej01.dao;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonParseException;
import com.google.gson.reflect.TypeToken;
import stevens.ej01.model.Mascota;

import java.io.IOException;
import java.io.Reader;
import java.io.Writer;
import java.lang.reflect.Type;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

public class GsonMascotaDao implements MascotaDao {

    private final Path archivo;
    private final Type tipoLista;
    private final Gson gson = new GsonBuilder().setPrettyPrinting().create();

    public GsonMascotaDao(Path archivo, Class<Mascota> clase) {
        this.archivo = archivo;
        this.tipoLista = TypeToken.getParameterized(List.class, clase).getType();
    }

    @Override
    public List<Mascota> cargar() throws PersistenciaException {
        if (!Files.exists(archivo)) {
            return new ArrayList<>();
        }
        try (Reader lector = Files.newBufferedReader(archivo, StandardCharsets.UTF_8)) {
            List<Mascota> mascotas = gson.fromJson(lector, tipoLista);
            if (mascotas == null) {
                return new ArrayList<>();
            }
            return new ArrayList<>(mascotas);
        } catch (IOException | JsonParseException e) {
            throw new PersistenciaException("No se pudo leer " + archivo.getFileName() + ": " + e.getMessage(), e);
        }
    }

    @Override
    public void guardar(List<Mascota> mascotas) throws PersistenciaException {
        try {
            Path carpeta = archivo.toAbsolutePath().getParent();
            if (carpeta != null) {
                Files.createDirectories(carpeta);
            }
            try (Writer escritor = Files.newBufferedWriter(archivo, StandardCharsets.UTF_8)) {
                gson.toJson(mascotas, tipoLista, escritor);
            }
        } catch (IOException | JsonParseException e) {
            throw new PersistenciaException("No se pudo guardar " + archivo.getFileName() + ": " + e.getMessage(), e);
        }
    }
}