package stevens.ej06.repository;

import stevens.ej06.dao.GastoDao;
import stevens.ej06.dao.PersistenciaException;
import stevens.ej06.model.Gasto;
import stevens.ej06.model.GastoFijo;
import stevens.ej06.model.GastoVariable;

import java.util.ArrayList;
import java.util.List;


public class RepositoryGasto implements Repository<Gasto>{

    private GastoDao daoFijo;
    private GastoDao daoVariable;
    private List<Gasto> gastos = new ArrayList<>();

    public RepositoryGasto(GastoDao daoFijo, GastoDao daoVariable) {
        this.daoFijo = daoFijo;
        this.daoVariable = daoVariable;
    }

    @Override
    public void cargar() throws PersistenciaException {
        List<Gasto> cargadas = new ArrayList<>(gastos);
        cargadas.addAll(daoFijo.cargar());
        cargadas.addAll(daoVariable.cargar());
        gastos.clear();
        gastos.addAll(cargadas);
    }

    @Override
    public List<Gasto> listar() {
        return new ArrayList<>(gastos);
    }

    @Override
    public void agregar(Gasto elementos) throws PersistenciaException {
        List<Gasto> copia = new ArrayList<>(gastos);
        copia.add(elementos);
        guardarPorTipo(copia);
        gastos.add(elementos);

    }

    @Override
    public void eliminar(Gasto elementos) throws PersistenciaException {
        List<Gasto> copia = new ArrayList<>(gastos);
        copia.remove(elementos);
        guardarPorTipo(copia);
        gastos.remove(elementos);
    }

    private void guardarPorTipo(List<Gasto> todos) throws PersistenciaException {
        List<Gasto> fijo = new ArrayList<>();
        List<Gasto> variable = new ArrayList<>();
        for (Gasto gasto:todos){
            if (gasto instanceof GastoFijo){
                fijo.add(gasto);
            } else if (gasto instanceof GastoVariable) {
                variable.add(gasto);

            }
        }
        daoFijo.guardar(fijo);
        daoVariable.guardar(variable);
    }
}
