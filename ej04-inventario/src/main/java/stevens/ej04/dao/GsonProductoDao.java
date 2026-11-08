package stevens.ej04.dao;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonParseException;
import com.google.gson.reflect.TypeToken;
import stevens.ej04.model.Producto;

import java.io.IOException;
import java.io.Reader;
import java.io.Writer;
import java.lang.reflect.Type;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

public class GsonProductoDao implements ProductoDao {

    private final Path archivo;
    private final Type tipoLista;
    private final Gson gson = new GsonBuilder().setPrettyPrinting().create();

    public GsonProductoDao(Path archivo, Class<? extends Producto> tipo) {
        this.archivo = archivo;
        this.tipoLista = TypeToken.getParameterized(List.class, tipo).getType();
    }

    @Override
    public List<Producto> cargar() throws PersistenciaException {
        if (!Files.exists(archivo)){
            return new ArrayList<>();
        }
        try(Reader lector = Files.newBufferedReader(archivo,StandardCharsets.UTF_8)) {
            List<Producto> productos = gson.fromJson(lector,tipoLista);
            if (productos == null){
                return new ArrayList<>();
            }
            return new ArrayList<>(productos);
        }catch (IOException | JsonParseException e){
            throw new PersistenciaException("Error al leer "+archivo.getFileName()+": "+e.getMessage(),e);
        }
    }

    @Override
    public void guardar(List<Producto> productos) throws PersistenciaException {
        try {
            Path carpeta = archivo.toAbsolutePath().getParent();
            if (carpeta != null){
                Files.createDirectories(carpeta);
            }
            try (Writer escritor = Files.newBufferedWriter(archivo, StandardCharsets.UTF_8)) {
                gson.toJson(productos,tipoLista,escritor);
            }
        }catch (IOException | JsonParseException e){
            throw new PersistenciaException("no se pudo guardar "+archivo.getFileName()+e.getMessage(),e);
        }
    }
}
