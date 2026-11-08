package stevens.ej01.dao;

import stevens.ej01.model.Duenio;
import java.util.List;

public interface DuenioDao {
    List<Duenio> cargar() throws PersistenciaException;
    void guardar(List<Duenio> duenios) throws PersistenciaException;
}