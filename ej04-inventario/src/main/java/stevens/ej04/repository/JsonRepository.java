package stevens.ej04.repository;

import stevens.ej04.dao.PersistenciaException;

import java.util.List;

/**
 * Operaciones que el repositorio ofrece al controlador.
 * El repositorio mantiene la colección en memoria y delega los archivos al DAO.
 */
public interface JsonRepository<T> {
    void cargar() throws PersistenciaException;
    List<T> listar();
    void agregar(T elemento) throws PersistenciaException;
    void eliminar(T elemento) throws PersistenciaException;
}
