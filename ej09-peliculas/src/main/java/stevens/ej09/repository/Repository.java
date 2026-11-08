package stevens.ej09.repository;


import stevens.ej09.dao.PersistenciaException;
import stevens.ej09.model.Pelicula;

import java.io.IOException;
import java.util.List;


public interface Repository<T> {

    void cargar() throws PersistenciaException;
    List<T> listar();
    void agregar(T elemetos) throws PersistenciaException, IOException;


    void eliminar(T elementos) throws PersistenciaException, IOException;


}