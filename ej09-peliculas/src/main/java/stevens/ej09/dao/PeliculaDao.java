package stevens.ej09.dao;

import stevens.ej09.model.Pelicula;

import java.io.IOException;
import java.util.List;

public interface PeliculaDao {
    List<Pelicula> cargar() throws PersistenciaException;

    void guardar(List<Pelicula> peliculas) throws PersistenciaException, IOException;


}
