package stevens.ej09.dao;


import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonParseException;
import com.google.gson.reflect.TypeToken;
import stevens.ej09.model.Pelicula;

import java.io.File;
import java.io.IOException;
import java.io.Reader;
import java.io.Writer;
import java.lang.reflect.Type;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

public class GsonPeliculaDao implements PeliculaDao{

    private final Path archivo;
    private final Type tipoLista;
    private final Gson gson = new GsonBuilder().setPrettyPrinting().create();


    public GsonPeliculaDao(Path archivo, Class<Pelicula> clase) {
        this.archivo = archivo;
        this.tipoLista = TypeToken.getParameterized(List.class, clase).getType();
    }

    @Override
    public List<Pelicula> cargar() throws PersistenciaException {
        if (!Files.exists(archivo)){
            return new ArrayList<>();
        }

        try (Reader lector = Files.newBufferedReader(archivo, StandardCharsets.UTF_8)){
            List<Pelicula> peliculas = gson.fromJson(lector, tipoLista);
            if (peliculas == null){
                return new ArrayList<>();
            }
            return new ArrayList<>(peliculas);

        }catch (IOException | JsonParseException e){
            throw new PersistenciaException("no se pudo leer: "+ archivo.getFileName()+" "+e.getMessage(),e);
        }
    }

    @Override
    public void guardar(List<Pelicula> peliculas) throws PersistenciaException, IOException {
        Path distino = archivo.getParent();
        if (distino != null){
            Files.createDirectories(distino);
        }
        try(Writer escritor = Files.newBufferedWriter(archivo, StandardCharsets.UTF_8)) {
            gson.toJson(peliculas,tipoLista,escritor);

        }catch (IOException | JsonParseException e){
            throw new PersistenciaException("No se pudo gaurdar: "+archivo.getFileName() + e.getMessage(),e);

        }
    }
}
