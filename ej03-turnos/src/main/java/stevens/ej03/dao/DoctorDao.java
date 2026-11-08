package stevens.ej03.dao;


import stevens.ej03.model.Doctor;

import java.util.List;

public interface DoctorDao {
    List<Doctor> cargar() throws PersistenciaException;
    void guardar(List<Doctor> doctors) throws PersistenciaException;
}
