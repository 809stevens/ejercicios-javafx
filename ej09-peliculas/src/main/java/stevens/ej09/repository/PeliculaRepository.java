package stevens.ej09.repository;



import stevens.ej09.dao.PeliculaDao;
import stevens.ej09.dao.PersistenciaException;
import stevens.ej09.model.Pelicula;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;


public class PeliculaRepository implements Repository<Pelicula>{

    private PeliculaDao dao;
    private List<Pelicula> peliculas = new ArrayList<>();

    public PeliculaRepository(PeliculaDao dao) {
        this.dao = dao;
    }

    @Override
    public void cargar() throws PersistenciaException {
        peliculas.clear();
        peliculas.addAll(dao.cargar());
    }

    @Override
    public List<Pelicula> listar() {
        return new ArrayList<>(peliculas);
    }

    @Override
    public void agregar(Pelicula elemetos) throws PersistenciaException, IOException {
        List<Pelicula> copia = new ArrayList<>(peliculas);
        copia.add(elemetos);
        dao.guardar(copia);
        peliculas.add(elemetos);
    }

    @Override
    public void eliminar(Pelicula elementos) throws PersistenciaException, IOException {
        List<Pelicula> copia = new ArrayList<>(peliculas);

        copia.remove(elementos);
        dao.guardar(copia);
        peliculas.remove(elementos);
    }
}
