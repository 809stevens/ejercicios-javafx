package stevens.ej03.repository;

import stevens.ej03.dao.PersistenciaException;
import stevens.ej03.dao.TurnoDao;
import stevens.ej03.model.Turno;

import java.util.ArrayList;
import java.util.List;

public class TurnoRepository implements Repository<Turno>{

    private TurnoDao dao;
    private List<Turno> turnos = new ArrayList<>();

    public TurnoRepository(TurnoDao dao) {
        this.dao = dao;
    }

    @Override
    public void cargar() throws PersistenciaException {
        turnos.clear();
        turnos.addAll(dao.cargar());
    }

    @Override
    public List<Turno> listar() {
        return new ArrayList<>(turnos);
    }

    @Override
    public void agregar(Turno elementos) throws PersistenciaException {
        List<Turno> copia = new ArrayList<>(turnos);
        copia.add(elementos);
        dao.guardar(copia);
        turnos.add(elementos);
    }

    @Override
    public void eliminar(Turno elementos) throws PersistenciaException {
        List<Turno> copia = new ArrayList<>(turnos);
        copia.remove(elementos);
        dao.guardar(copia);
        turnos.remove(elementos);
    }
}
