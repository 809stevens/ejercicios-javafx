package stevens.ej03.repository;

import stevens.ej03.dao.DoctorDao;
import stevens.ej03.dao.PersistenciaException;
import stevens.ej03.model.Doctor;

import java.util.ArrayList;
import java.util.List;

public class DoctorRepository implements Repository<Doctor> {
    private DoctorDao dao;
    private List<Doctor> doctors = new ArrayList<>();

    public DoctorRepository(DoctorDao dao) {
        this.dao = dao;
    }

    @Override
    public void cargar() throws PersistenciaException {
        doctors.clear();
        doctors.addAll(dao.cargar());
    }

    @Override
    public List<Doctor> listar() {
        return new ArrayList<>(doctors);
    }

    @Override
    public void agregar(Doctor elementos) throws PersistenciaException {
        List<Doctor> copia = new ArrayList<>(doctors);
        copia.add(elementos);
        dao.guardar(copia);
        doctors.add(elementos);
    }

    @Override
    public void eliminar(Doctor elementos) throws PersistenciaException {
        List<Doctor> copia = new ArrayList<>(doctors);
        copia.remove(elementos);
        dao.guardar(copia);
        doctors.remove(elementos);
    }
}
