package stevens.ej05.dao;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonParseException;
import com.google.gson.reflect.TypeToken;
import stevens.ej05.model.Participante;

import java.io.IOException;
import java.io.Reader;
import java.io.Writer;
import java.lang.reflect.Type;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

/**
 * Implementación con Gson de ParticipanteDao.
 *
 * TODO: completa cargar() y guardar(). Los imports ya están puestos para que
 * solo tengas que escribir el cuerpo.
 */
public class GsonParticipanteDao implements ParticipanteDao {

    private final Path archivo;
    private final Type tipoLista;
    private final Gson gson = new GsonBuilder().setPrettyPrinting().create();

    public GsonParticipanteDao(Path archivo, Class<? extends Participante> tipo) {
        this.archivo = archivo;
        this.tipoLista = TypeToken.getParameterized(List.class, tipo).getType();
    }

    @Override
    public List<Participante> cargar() throws PersistenciaException {
        if (!Files.exists(archivo)){
            return new ArrayList<>();
        }
        try(Reader lector = Files.newBufferedReader(archivo,StandardCharsets.UTF_8)){
            List<Participante> participantes = gson.fromJson(lector,tipoLista);
            if (participantes == null){
                return new ArrayList<>();
            }
            return new ArrayList<>(participantes);
        } catch (IOException ||)
    }

    @Override
    public void guardar(List<Participante> elementos) throws PersistenciaException {
        // TODO: crear la carpeta si falta con Files.createDirectories(...).
        // TODO: abrir con Files.newBufferedWriter(archivo, StandardCharsets.UTF_8).
        // TODO: gson.toJson(elementos, tipoLista, escritor);
        // TODO: capturar IOException y JsonParseException y lanzar
        //       new PersistenciaException("No se pudo guardar " + archivo.getFileName(), e).
        throw new UnsupportedOperationException("TODO: implementar guardar()");
    }
}
