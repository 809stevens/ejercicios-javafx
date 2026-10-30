package stevens.ej01.dao;

import stevens.ej01.model.Mascota;
import java.util.List;

public interface MascotaDao {
    List<Mascota> cargar() throws PersistenciaException;
    void guardar(List<Mascota> mascotas) throws PersistenciaException;
}