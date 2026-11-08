package stevens.ej03.repository;

import stevens.ej03.dao.PacienteDao;
import stevens.ej03.dao.PersistenciaException;
import stevens.ej03.model.Paciente;

import java.util.ArrayList;
import java.util.List;

public class PacienteRepository implements Repository<Paciente> {
    private PacienteDao dao;
    private List<Paciente> pacientes = new ArrayList<>();

    public PacienteRepository(PacienteDao dao) {
        this.dao = dao;
    }

    @Override
    public void cargar() throws PersistenciaException {
        pacientes.clear();
        pacientes.addAll(dao.cargar());
    }

    @Override
    public List<Paciente> listar() {
        return new ArrayList<>(pacientes);
    }

    @Override
    public void agregar(Paciente elementos) throws PersistenciaException {
        List<Paciente> copia = new ArrayList<>(pacientes);
        copia.add(elementos);
        dao.guardar(copia);
        pacientes.add(elementos);
    }

    @Override
    public void eliminar(Paciente elementos) throws PersistenciaException {
        List<Paciente> copia = new ArrayList<>(pacientes);
        copia.remove(elementos);
        dao.guardar(copia);
        pacientes.remove(elementos);
    }
}
