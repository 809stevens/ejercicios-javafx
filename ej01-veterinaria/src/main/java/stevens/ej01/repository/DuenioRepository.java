package stevens.ej01.repository;

import stevens.ej01.dao.DuenioDao;
import stevens.ej01.dao.PersistenciaException;
import stevens.ej01.model.Duenio;
import java.util.ArrayList;
import java.util.List;

public class DuenioRepository implements Repository<Duenio> {

    private final DuenioDao dao;
    private final List<Duenio> duenios = new ArrayList<>();

    public DuenioRepository(DuenioDao dao) {
        this.dao = dao;
    }

    @Override
    public void cargar() throws PersistenciaException {
        duenios.clear();
        duenios.addAll(dao.cargar());
    }

    @Override
    public List<Duenio> listar() {
        return new ArrayList<>(duenios);
    }

    @Override
    public void agregar(Duenio duenio) throws PersistenciaException {
        List<Duenio> copia = new ArrayList<>(duenios);
        copia.add(duenio);
        dao.guardar(copia);
        duenios.add(duenio);
    }

    @Override
    public void eliminar(Duenio duenio) throws PersistenciaException {
        List<Duenio> copia = new ArrayList<>(duenios);
        copia.remove(duenio);
        dao.guardar(copia);
        duenios.remove(duenio);
    }
}