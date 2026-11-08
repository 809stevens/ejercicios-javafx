package stevens.ej08.repository;

import stevens.ej08.dao.ContactoDao;
import stevens.ej08.dao.PersistenciaException;
import stevens.ej08.model.Contacto;
import java.util.ArrayList;
import java.util.List;

public class ContactoRepository implements Repository<Contacto> {

    private final ContactoDao dao;
    private final List<Contacto> contactos = new ArrayList<>();

    public ContactoRepository(ContactoDao dao) {
        this.dao = dao;
    }

    @Override
    public void cargar() throws PersistenciaException {
        contactos.clear();
        contactos.addAll(dao.cargar());
    }

    @Override
    public List<Contacto> listar() {
        return  new ArrayList<>(contactos);
    }

    @Override
    public void agregar(Contacto contacto) throws PersistenciaException {
        List<Contacto> copia = new ArrayList<>(contactos);
        copia.add(contacto);
        dao.guardar(copia);
        contactos.add(contacto);
    }

    @Override
    public void eliminar(Contacto contacto) throws PersistenciaException {
        List<Contacto> copia = new ArrayList<>(contactos);
        copia.remove(contacto);
        dao.guardar(copia);
        contactos.remove(contacto);
    }
}