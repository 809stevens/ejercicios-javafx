package stevens.ej02.dao;

import stevens.ej02.model.Libro;
import java.util.List;

public interface LibroDao {
    List<Libro> cargar() throws PersistenciaException;
    void guardar(List<Libro> libros) throws PersistenciaException;
}