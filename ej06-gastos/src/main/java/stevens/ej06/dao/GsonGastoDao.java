package stevens.ej06.dao;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonParseException;
import com.google.gson.reflect.TypeToken;
import stevens.ej06.model.Gasto;

import java.io.IOException;
import java.io.Reader;
import java.io.Writer;
import java.lang.reflect.Type;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

public class GsonGastoDao implements GastoDao{

    private Path archivo;
    private Type tipoLista;
    private Gson gson = new GsonBuilder().setPrettyPrinting().create();

    public GsonGastoDao(Path archivo, Class<? extends Gasto> clase) {
        this.archivo = archivo;
        tipoLista = TypeToken.getParameterized(List.class,clase).getType();
    }

    @Override
    public List<Gasto> cargar() throws PersistenciaException {
        if (!Files.exists(archivo)){
            return new ArrayList<>();
        }
        try(Reader lector = Files.newBufferedReader(archivo, StandardCharsets.UTF_8)) {
            List<Gasto> gastos = gson.fromJson(lector,tipoLista);
            if (gastos == null){
                return new ArrayList<>();
            }
            return new ArrayList<>(gastos);
        }catch (IOException | JsonParseException e){
            throw new PersistenciaException("no se pudo guadar "+archivo.getFileName() +": "+e.getMessage(),e);
        }
    }

    @Override
    public void guardar(List<Gasto> gastos) throws PersistenciaException {
        try {
            Path carpeta = archivo.toAbsolutePath().getParent();
            if (carpeta != null){
                Files.createDirectories(carpeta);
            }
            try(Writer escritor = Files.newBufferedWriter(archivo,StandardCharsets.UTF_8)) {
                gson.toJson(gastos,tipoLista,escritor);
            }
        }catch (IOException | JsonParseException e){
            throw new PersistenciaException("no se pudo guadar "+archivo.getFileName()+": "+e.getMessage(),e);
        }
    }
}
