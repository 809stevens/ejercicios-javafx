package stevens.ej07.repository;

import stevens.ej07.dao.ContenidoDao;
import stevens.ej07.dao.PersistenciaException;
import stevens.ej07.model.Cancion;
import stevens.ej07.model.Contenido;
import stevens.ej07.model.Podcast;

import java.util.ArrayList;
import java.util.List;

public class ContenidoRepository implements Repository<Contenido>{
    private ContenidoDao daoCancion;
    private ContenidoDao daoPodcast;
    private List<Contenido> contenidos = new ArrayList<>();

    public ContenidoRepository(ContenidoDao daoCancion, ContenidoDao daoPodcast) {
        this.daoCancion = daoCancion;
        this.daoPodcast = daoPodcast;
    }

    @Override
    public void cargar() throws PersistenciaException {
        contenidos.clear();
        contenidos.addAll(daoCancion.cargar());
        contenidos.addAll(daoPodcast.cargar());
    }

    @Override
    public List<Contenido> listar() {
        return new ArrayList<>(contenidos);
    }

    @Override
    public void agregar(Contenido elementos) throws PersistenciaException {
        List<Contenido> copia = new ArrayList<>(contenidos);
        copia.add(elementos);
        guardarPorTipo(copia);
        contenidos.add(elementos);
    }

    @Override
    public void eliminar(Contenido elementos) throws PersistenciaException {
        List<Contenido> copia = new ArrayList<>(contenidos);
        copia.remove(elementos);
        guardarPorTipo(copia);
        contenidos.remove(elementos);
    }

    private void guardarPorTipo(List<Contenido> todos) throws PersistenciaException{
        List<Contenido> cancions = new ArrayList<>();
        List<Contenido> podcasts = new ArrayList<>();
        for (Contenido contenido:todos ){
            if (contenido instanceof Cancion){
                cancions.add(contenido);

            } else if (contenido instanceof Podcast) {
                podcasts.add(contenido);

            }
        }
        daoCancion.guardar(cancions);
        daoPodcast.guardar(podcasts);
    }
}
