package stevens.ej04.repository;

import stevens.ej04.dao.ProductoDao;
import stevens.ej04.dao.PersistenciaException;
import stevens.ej04.model.Producto;
import stevens.ej04.model.ProductoNoPerecedero;
import stevens.ej04.model.ProductoPerecedero;

import java.util.ArrayList;
import java.util.List;

/**
 * Repositorio de Producto.
 *
 * TODO: mantén la colección en memoria y delega la lectura/escritura en los DAO.
 * Regla de oro: guarda primero en los DAO y solo después actualiza la lista.
 */
public class ProductoRepository implements JsonRepository<Producto> {

    private final ProductoDao daoPerecederos;
    private final ProductoDao daoNoPerecederos;
    private final List<Producto> elementos = new ArrayList<>();

    public ProductoRepository(ProductoDao daoPerecederos, ProductoDao daoNoPerecederos) {
        this.daoPerecederos = daoPerecederos;
        this.daoNoPerecederos = daoNoPerecederos;
    }

    @Override
    public void cargar() throws PersistenciaException {
        List<Producto> productos = new ArrayList<>();
        productos.addAll(daoPerecederos.cargar());
        productos.addAll(daoNoPerecederos.cargar());

        elementos.clear();
        elementos.addAll(productos);
    }

    @Override
    public List<Producto> listar() {
        return new ArrayList<>(elementos);
    }

    @Override
    public void agregar(Producto elemento) throws PersistenciaException {
        List<Producto> copia = new ArrayList<>(elementos);

        copia.add(elemento);
        guardarPorTipo(copia);
        elementos.clear();
        elementos.addAll(copia);
    }

    @Override
    public void eliminar(Producto elemento) throws PersistenciaException {
        List<Producto> copia = new ArrayList<>(elementos);
        copia.remove(elemento);
        guardarPorTipo(copia);
        elementos.clear();
        elementos.addAll(copia);
    }
    
    private void guardarPorTipo(List<Producto> todos) throws PersistenciaException{
        List<Producto> perecedero = new ArrayList<>();
        List<Producto> NoPerecedero = new ArrayList<>();

        for (Producto producto:todos){
            if (producto instanceof ProductoPerecedero){
                perecedero.add(producto);
            }else if (producto instanceof ProductoNoPerecedero){
                NoPerecedero.add(producto);
            }
        }

        daoPerecederos.guardar(perecedero);
        daoNoPerecederos.guardar(NoPerecedero);
    }
}
