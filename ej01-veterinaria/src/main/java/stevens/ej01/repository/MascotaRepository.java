package stevens.ej01.repository;

import stevens.ej01.dao.MascotaDao;
import stevens.ej01.dao.PersistenciaException;
import stevens.ej01.model.Mascota;
import java.util.ArrayList;
import java.util.List;

public class MascotaRepository implements Repository<Mascota> {

    private final MascotaDao dao;
    private final List<Mascota> mascotas = new ArrayList<>();

    public MascotaRepository(MascotaDao dao) {
        this.dao = dao;
    }

    @Override
    public void cargar() throws PersistenciaException {
        mascotas.clear();
        mascotas.addAll(dao.cargar());
    }

    @Override
    public List<Mascota> listar() {
        return new ArrayList<>(mascotas);
    }

    @Override
    public void agregar(Mascota mascota) throws PersistenciaException {
        List<Mascota> copia = new ArrayList<>(mascotas);
        copia.add(mascota);
        dao.guardar(copia);
        mascotas.add(mascota);
    }

    @Override
    public void eliminar(Mascota mascota) throws PersistenciaException {
        List<Mascota> copia = new ArrayList<>(mascotas);
        copia.remove(mascota);
        dao.guardar(copia);
        mascotas.remove(mascota);
    }
}