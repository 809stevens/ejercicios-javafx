package stevens.ej06.controller;

import com.google.errorprone.annotations.FormatMethod;
import javafx.beans.binding.BooleanBinding;
import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.beans.property.ReadOnlyStringWrapper;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import stevens.ej06.dao.GsonGastoDao;
import stevens.ej06.dao.PersistenciaException;
import stevens.ej06.model.Gasto;
import stevens.ej06.model.GastoFijo;
import stevens.ej06.model.GastoVariable;
import stevens.ej06.repository.Repository;
import stevens.ej06.repository.RepositoryGasto;
import stevens.ej06.util.Paths;

import java.time.LocalDate;

import static stevens.ej06.model.Gasto.pesos;

public class MainController {

    @FXML
    private ComboBox<String> cmbTipo;

    @FXML
    private TableColumn<Gasto, String> colCategoria;

    @FXML
    private TableColumn<Gasto, String> colConcepto;

    @FXML
    private TableColumn<Gasto, String> colDatosEspecifico;

    @FXML
    private TableColumn<Gasto, String> colFecha;

    @FXML
    private TableColumn<Gasto, Integer> colMontoBase;

    @FXML
    private TableColumn<Gasto, Integer> colMontoFinal;

    @FXML
    private TableColumn<Gasto, String> colTipo;

    @FXML
    private Label lblCuotas;

    @FXML
    private Label lblMedio;

    @FXML
    private Label lblPeriodo;

    @FXML
    private Label lblMses;

    @FXML
    private Label lblMensaje;

    @FXML
    private TableView<Gasto> tblGastos;

    @FXML
    private TextField txtCategoria;

    @FXML
    private TextField txtConcepto;

    @FXML
    private Label lblGastoTotal;

    @FXML
    private TextField txtContrato;

    @FXML
    private TextField txtCuota;

    @FXML
    private DatePicker txtFecha;

    @FXML
    private TextField txtGasto;

    @FXML
    private TextField txtMedioPago;

    @FXML
    private TextField txtPeriodo;

    private final ObservableList<Gasto> gastos = FXCollections.observableArrayList();
    private final Repository<Gasto> gastoRepo = new RepositoryGasto(new GsonGastoDao(Paths.FIJOS, GastoFijo.class),
    new GsonGastoDao(Paths.VARIABLES, GastoVariable.class)
    );

    private String datosEspecificos(Gasto gasto){
        if (gasto instanceof GastoFijo fijo){
            return fijo.getPeriodicidad()+" "+fijo.getMesesContrato()+" meses";
        } else if (gasto instanceof GastoVariable variable) {
            return variable.getMedioPago()+" "+variable.getCuotas()+" cuotas";

        }
        return "";
    }



    @FXML
    void btnCancelar(ActionEvent event) {
        limparCampo();

    }

    @FXML
    void btnEliminar(ActionEvent event) {
        Gasto sel = tblGastos.getSelectionModel().getSelectedItem();
        if (sel == null){
            mensaje("debes seleccionar un campo para eliminar");
            return;
        }
        try {
            gastoRepo.eliminar(sel);
            gastos.setAll(gastoRepo.listar());
            mensaje(sel.getConcepto()+" eliminado");
        } catch (PersistenciaException e) {
            mensaje("no se pudo eliminar:"+ e.getMessage());
        }

    }

    @FXML
    void btnGuadar(ActionEvent event) {
        String tipo = cmbTipo.getValue();
        if (tipo == null){
            mensaje("eliga un tipo de gasto");
        }

        if (txtFecha.getValue() == null) {
            mensaje("debes elegir una fecha");
            return;
        }

        String concepto = txtConcepto.getText().trim();
        String categoria = txtCategoria.getText().trim();
        String fecha = txtFecha.getValue().format(Gasto.FORMATO_FECHA);

        String gastoTxt = txtGasto.getText().trim();
        int montoBase = gastoTxt.isEmpty() ? 0 : Integer.parseInt(gastoTxt);
        String contratoTxt = txtContrato.getText().trim();
        int mesesContrato = contratoTxt.isEmpty() ? 0 : Integer.parseInt(contratoTxt);
        String periodoTxt = txtPeriodo.getText().trim();
        String cuotasTxt = txtCuota.getText().trim();
        int cuotas = cuotasTxt.isEmpty() ? 0 : Integer.parseInt(cuotasTxt);
        String medioPago = txtMedioPago.getText().trim();
        Gasto gasto = null;
        try {
            if ("Fijo".equals(tipo)) {
                gasto = new GastoFijo(concepto, categoria, montoBase, fecha, periodoTxt, mesesContrato);

            } else if ("Variable".equals(tipo)) {
                gasto = new GastoVariable(concepto, categoria, montoBase, fecha, medioPago, cuotas);
            }
            if (gasto != null) {
                gastoRepo.agregar(gasto);
                gastos.setAll(gastoRepo.listar());
                mensaje(gasto.getCategoria() + " guardado");
                limparCampo();
            } else {
                mensaje("Tipo de gasto no reconocido");
            }

        }catch (NumberFormatException e) {
            mensaje("no puedes ingresar letras en los campos numérico");
        }catch (IllegalArgumentException e){
            mensaje(e.getMessage());
        }catch (PersistenciaException e){
            mensaje("no se pudo guardar "+e.getMessage());
        }

    }

    @FXML
    private void initialize(){
        cmbTipo.getItems().addAll("Fijo","Variable");
        cmbTipo.setValue("Fijo");
        BooleanBinding esFijo =cmbTipo.valueProperty().isEqualTo("Fijo");

        txtMedioPago.visibleProperty().bind(esFijo.not());
        lblMedio.visibleProperty().bind(esFijo.not());
        txtCuota.visibleProperty().bind(esFijo.not());
        lblCuotas.visibleProperty().bind(esFijo.not());
        txtContrato.visibleProperty().bind(esFijo);
        lblPeriodo.visibleProperty().bind(esFijo);
        txtPeriodo.visibleProperty().bind(esFijo);
        lblMses.visibleProperty().bind(esFijo);

        colTipo.setCellValueFactory(c -> new ReadOnlyStringWrapper(c.getValue().getTipo()));
        colCategoria.setCellValueFactory(c -> new ReadOnlyStringWrapper(c.getValue().getCategoria()));
        colConcepto.setCellValueFactory(c -> new ReadOnlyStringWrapper(c.getValue().getConcepto()));
        colFecha.setCellValueFactory(c -> new  ReadOnlyStringWrapper(c.getValue().getFecha()));
        colMontoBase.setCellValueFactory(c -> new ReadOnlyObjectWrapper<>(c.getValue().getMontoBase()));
        colDatosEspecifico.setCellValueFactory(c -> new ReadOnlyObjectWrapper<>(datosEspecificos(c.getValue())));
        colMontoFinal.setCellValueFactory(c -> new ReadOnlyObjectWrapper<>(c.getValue().calcularMontoFinal()));
        tblGastos.setItems(gastos);
        try {
            gastoRepo.cargar();
            gastos.setAll(gastoRepo.listar());
            mensaje("Datos cargados correctamente");
        } catch (PersistenciaException e) {
            mensaje("error al cargar los datos");
        }

    }

    private void limparCampo(){
        txtConcepto.clear();
        txtCategoria.clear();
        txtPeriodo.clear();
        txtGasto.clear();
        txtMedioPago.clear();
        txtCuota.clear();
        txtContrato.clear();
    }

    private void mensaje(String texto){
        if (lblMensaje != null){
            lblMensaje.setText(texto);
        }
    }

    private void suma(){
        int total=0;
         for (Gasto T: gastos){
             total +=T.calcularMontoFinal();
         }

        lblGastoTotal.setText("Total: " + pesos(total));
    }

}
