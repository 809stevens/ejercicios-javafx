package stevens.ej08.dao;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonIOException;
import com.google.gson.reflect.TypeToken;
import stevens.ej08.model.Contacto;

import javax.imageio.IIOException;
import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.io.Writer;
import java.lang.reflect.Type;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

public class GsonContactoDao implements ContactoDao {

    private final Path archivo;
    private final Type tipoLista;
    private Gson gson = new GsonBuilder().setPrettyPrinting().create();

    public GsonContactoDao(Path archivo, Class<Contacto> clase) {
        this.archivo = archivo;
        this.tipoLista = TypeToken.getParameterized(List.class,clase).getType();
    }

    @Override
    public List<Contacto> cargar() throws PersistenciaException {
        if (!Files.exists(archivo)){
            return new ArrayList<>();
        }
        try (Reader lector = Files.newBufferedReader(archivo,StandardCharsets.UTF_8)) {
            List<Contacto> contactos = gson.fromJson(lector,tipoLista);
            if (contactos == null){
                return  new ArrayList<>();
            }
            return new ArrayList<>(contactos);
        }catch (IOException | JsonIOException e){
            throw new PersistenciaException("no se pudo leer "+archivo.getFileName()+ ": "+ e.getMessage(),e);
        }
    }

    @Override
    public void guardar(List<Contacto> contactos) throws PersistenciaException {
        try {
            Path carpeta = archivo.toAbsolutePath().getParent();
            if (carpeta != null){
                Files.createDirectories(carpeta);
            }
            try (Writer escritor = Files.newBufferedWriter(archivo,StandardCharsets.UTF_8)){
                gson.toJson(contactos, tipoLista,escritor);
            }
        }catch (IOException | JsonIOException e){
            throw new PersistenciaException("no se pudo guardar "+archivo.getFileName()+ ": "+e.getMessage(),e);
        }
    }
}