package stevens.ej03.dao;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonParseException;
import com.google.gson.reflect.TypeToken;
import stevens.ej03.model.Doctor;

import java.io.IOException;
import java.io.Reader;
import java.io.Writer;
import java.lang.reflect.Type;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

public class GsonDoctorDao implements DoctorDao{
    private final Path arichivo;
    private final Type tipoLista;
    private Gson gson = new GsonBuilder().setPrettyPrinting().create();

    public GsonDoctorDao(Path arichivo, Class<Doctor> clase) {
        this.arichivo = arichivo;
        this.tipoLista = TypeToken.getParameterized(List.class,clase).getType();
    }

    @Override
    public List<Doctor> cargar() throws PersistenciaException {
        if (!Files.exists(arichivo)){
            return new ArrayList<>();
        }
        try (Reader lector = Files.newBufferedReader(arichivo, StandardCharsets.UTF_8)) {
            List<Doctor> doctors = gson.fromJson(lector,tipoLista);
            if (doctors == null){
                return new ArrayList<>();
            }
            return new ArrayList<>(doctors);
        }catch (IOException | JsonParseException e){
            throw new PersistenciaException("No se pudo leer "+arichivo.getFileName() + ": "+e.getMessage(),e);
        }
    }

    @Override
    public void guardar(List<Doctor> doctors) throws PersistenciaException {
        try {
            Path carpeta = arichivo.toAbsolutePath().getParent();
            if (carpeta != null){
                Files.createDirectories(carpeta);
            }
            try (Writer escritor = Files.newBufferedWriter(arichivo,StandardCharsets.UTF_8)) {
                gson.toJson(doctors,tipoLista,escritor);
            }
        }catch (IOException | JsonParseException e){
            throw new PersistenciaException("No se pudo guardar "+arichivo.getFileName()+": "+e.getMessage(),e);
        }
    }
}
