package stevens.ej05.repository;

import stevens.ej05.dao.ParticipanteDao;
import stevens.ej05.dao.PersistenciaException;
import stevens.ej05.model.Participante;

import java.util.ArrayList;
import java.util.List;

/**
 * Repositorio de Participante.
 *
 * TODO: mantén la colección en memoria y delega la lectura/escritura en los DAO.
 * Regla de oro: guarda primero en los DAO y solo después actualiza la lista.
 */
public class ParticipanteRepository implements JsonRepository<Participante> {

    private final ParticipanteDao daoJugadores;
    private final ParticipanteDao daoEntrenadores;
    private final List<Participante> elementos = new ArrayList<>();

    public ParticipanteRepository(ParticipanteDao daoJugadores, ParticipanteDao daoEntrenadores) {
        this.daoJugadores = daoJugadores;
        this.daoEntrenadores = daoEntrenadores;
    }

    @Override
    public void cargar() throws PersistenciaException {
        // TODO: cargar los dos archivos, juntar los resultados y reemplazar la lista.
        throw new UnsupportedOperationException("TODO: implementar cargar()");
    }

    @Override
    public List<Participante> listar() {
        // TODO: devolver new ArrayList<>(elementos).
        throw new UnsupportedOperationException("TODO: implementar listar()");
    }

    @Override
    public void agregar(Participante elemento) throws PersistenciaException {
        // TODO: trabajar sobre una copia, guardar en los DAO y después actualizar la lista.
        throw new UnsupportedOperationException("TODO: implementar agregar()");
    }

    @Override
    public void eliminar(Participante elemento) throws PersistenciaException {
        // TODO: trabajar sobre una copia, guardar en los DAO y después actualizar la lista.
        throw new UnsupportedOperationException("TODO: implementar eliminar()");
    }

    private void guardarPorTipo(List<Participante> todos) throws PersistenciaException {
        // TODO: separar con instanceof Jugador y Entrenador, y llamar a cada DAO.
        throw new UnsupportedOperationException("TODO: implementar guardarPorTipo()");
    }
}
