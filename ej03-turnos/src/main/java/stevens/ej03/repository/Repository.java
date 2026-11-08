package stevens.ej03.repository;

import stevens.ej03.dao.PersistenciaException;

import java.util.List;

public interface Repository<T> {
    void cargar() throws PersistenciaException;
    List<T> listar();
    void agregar(T elementos) throws PersistenciaException;
    void eliminar(T elementos) throws PersistenciaException;
}
