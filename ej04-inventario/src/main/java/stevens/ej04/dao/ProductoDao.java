package stevens.ej04.dao;

import stevens.ej04.model.Producto;

import java.util.List;

public interface ProductoDao {

    List<Producto> cargar() throws PersistenciaException;

    void guardar(List<Producto> productos) throws PersistenciaException;
}
