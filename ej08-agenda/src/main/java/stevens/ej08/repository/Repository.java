package stevens.ej08.repository;

import stevens.ej08.dao.PersistenciaException;

import java.util.List;

public interface Repository<T> {

    void cargar() throws PersistenciaException;

    List<T> listar();

    void agregar(T elemento) throws PersistenciaException;

    void eliminar(T elemento) throws PersistenciaException;
}