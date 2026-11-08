package stevens.ej05.dao;

import stevens.ej05.model.Participante;

import java.util.List;

/**
 * Contrato de acceso a datos para Participante.
 * La implementación con Gson la escribes tú en GsonParticipanteDao.
 */
public interface ParticipanteDao {

    List<Participante> cargar() throws PersistenciaException;

    void guardar(List<Participante> elementos) throws PersistenciaException;
}
