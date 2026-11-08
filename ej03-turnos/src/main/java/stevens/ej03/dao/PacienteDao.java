package stevens.ej03.dao;

import stevens.ej03.model.Paciente;

import java.util.List;

public interface PacienteDao {
    List<Paciente> cargar() throws PersistenciaException;
    void guardar(List<Paciente> pacientes) throws PersistenciaException;
}
