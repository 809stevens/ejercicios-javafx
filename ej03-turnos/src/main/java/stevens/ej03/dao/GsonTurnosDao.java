package stevens.ej03.dao;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonParseException;
import com.google.gson.reflect.TypeToken;
import stevens.ej03.model.Turno;

import java.io.IOException;
import java.io.Reader;
import java.io.Writer;
import java.lang.reflect.Type;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

public class GsonTurnosDao implements TurnoDao {
    private final Path archivo;
    private final Type tipoLista;
    private Gson gson = new GsonBuilder().setPrettyPrinting().create();

    public GsonTurnosDao(Path archivo, Class<Turno> clase) {
        this.archivo = archivo;
        this.tipoLista = TypeToken.getParameterized(List.class,clase).getType();
    }


    @Override
    public List<Turno> cargar() throws PersistenciaException {
        if (!Files.exists(archivo)){
            return new ArrayList<>();
        }
        try (Reader lector = Files.newBufferedReader(archivo, StandardCharsets.UTF_8)) {
            List<Turno> turnos = gson.fromJson(lector,tipoLista);
            if (turnos ==null){
                return new ArrayList<>();
            }
            return new ArrayList<>(turnos);
        }catch (IOException | JsonParseException e){
            throw new PersistenciaException("No se pudo leer "+archivo.getFileName()+": "+e.getMessage(),e);
        }
    }

    @Override
    public void guardar(List<Turno> turnos) throws PersistenciaException {
        try {
            Path carpeta = archivo.toAbsolutePath().getParent();
            if (carpeta != null){
                Files.createDirectories(carpeta);
            }
            try (Writer escritor = Files.newBufferedWriter(archivo,StandardCharsets.UTF_8)) {
                gson.toJson(turnos,tipoLista,escritor);
            }
        }catch (IOException | JsonParseException e){
            throw new PersistenciaException("No se pudo guardar "+archivo.getFileName()+": "+e.getMessage(),e);
        }
    }
}
