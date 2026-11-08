package stevens.ej03.dao;

import stevens.ej03.model.Turno;

import java.util.List;

public interface TurnoDao {
    List<Turno> cargar() throws PersistenciaException;
    void guardar(List<Turno> turnos) throws PersistenciaException;
}
