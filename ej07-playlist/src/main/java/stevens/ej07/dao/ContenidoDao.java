package stevens.ej07.dao;

import stevens.ej07.model.Contenido;

import java.util.List;

public interface ContenidoDao {
    List<Contenido> cargar() throws PersistenciaException;
    void guardar(List<Contenido> contenidos) throws PersistenciaException;
}
