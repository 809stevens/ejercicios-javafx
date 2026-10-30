package stevens.ej02.repository;

import stevens.ej02.dao.LibroDao;
import stevens.ej02.dao.PersistenciaException;
import stevens.ej02.model.Libro;
import java.util.ArrayList;
import java.util.List;

public class LibroRepository implements Repository<Libro> {

    private final LibroDao dao;
    private final List<Libro> libros = new ArrayList<>();

    public LibroRepository(LibroDao dao) {
        this.dao = dao;
    }

    @Override
    public void cargar() throws PersistenciaException {
        libros.clear();
        libros.addAll(dao.cargar());
    }

    @Override
    public List<Libro> listar() {
        return new ArrayList<>(libros);
    }

    @Override
    public void agregar(Libro libro) throws PersistenciaException {
        List<Libro> copia = new ArrayList<>(libros);
        copia.add(libro);
        dao.guardar(copia);
        libros.add(libro);
    }

    @Override
    public void eliminar(Libro libro) throws PersistenciaException {
        List<Libro> copia = new ArrayList<>(libros);
        copia.remove(libro);
        dao.guardar(copia);
        libros.remove(libro);
    }
}