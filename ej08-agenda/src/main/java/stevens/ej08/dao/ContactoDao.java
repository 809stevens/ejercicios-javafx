package stevens.ej08.dao;

import stevens.ej08.model.Contacto;
import java.util.List;

public interface ContactoDao {
    List<Contacto> cargar() throws PersistenciaException;
    void guardar(List<Contacto> contactos) throws PersistenciaException;
}
