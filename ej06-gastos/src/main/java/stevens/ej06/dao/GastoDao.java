package stevens.ej06.dao;

import stevens.ej06.model.Gasto;

import java.util.List;

public interface GastoDao {
    List<Gasto> cargar() throws PersistenciaException;
    void guardar(List<Gasto> gastos) throws PersistenciaException;
}
