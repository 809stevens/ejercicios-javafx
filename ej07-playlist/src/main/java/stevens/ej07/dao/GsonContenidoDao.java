package stevens.ej07.dao;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonParseException;
import com.google.gson.reflect.TypeToken;
import stevens.ej07.model.Contenido;

import java.io.IOException;
import java.io.Reader;
import java.io.Writer;
import java.lang.reflect.Type;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

public class GsonContenidoDao implements ContenidoDao {
    private Path archivo;
    private Type tipoLista;
    private Gson gson = new GsonBuilder().setPrettyPrinting().create();

    public GsonContenidoDao(Path archivo, Class<? extends Contenido> clase) {
        this.archivo = archivo;
        this.tipoLista = TypeToken.getParameterized(List.class,clase).getType();
    }

    @Override
    public List<Contenido> cargar() throws PersistenciaException {
        if (!Files.exists(archivo)){
            return new ArrayList<>();
        }
        try(Reader lector = Files.newBufferedReader(archivo, StandardCharsets.UTF_8)) {
            List<Contenido> contenidos = gson.fromJson(lector,tipoLista);
            if (contenidos == null){
                return new ArrayList<>();
            }
            return new ArrayList<>(contenidos);
        }catch (IOException | JsonParseException e){
            throw new PersistenciaException("no se pudo leer "+archivo.getFileName()+": "+e.getMessage(),e);
        }
    }

    @Override
    public void guardar(List<Contenido> contenidos) throws PersistenciaException {
        try {
            Path carpeta = archivo.toAbsolutePath().getParent();
            if (carpeta != null){
                Files.createDirectories(carpeta);
            }
            try (Writer escritor = Files.newBufferedWriter(archivo,StandardCharsets.UTF_8)) {
                gson.toJson(contenidos,tipoLista,escritor);
            }
        }catch (IOException | JsonParseException e){
            throw new PersistenciaException("no se pudo guadar "+archivo.getFileName()+": "+e.getMessage(),e);
        }
    }
}
