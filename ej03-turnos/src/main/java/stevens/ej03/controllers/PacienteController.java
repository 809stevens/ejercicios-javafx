package stevens.ej03.controllers;

import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.beans.property.ReadOnlyStringWrapper;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import stevens.ej03.application.App;
import stevens.ej03.dao.GsonPacienteDao;
import stevens.ej03.dao.PersistenciaException;
import stevens.ej03.model.Paciente;
import stevens.ej03.repository.PacienteRepository;
import stevens.ej03.repository.Repository;
import stevens.ej03.utils.Paths;

public class PacienteController {

    @FXML
    private TableColumn<Paciente, String> colNombre;

    @FXML
    private TableColumn<Paciente, String> colTelefono;

    @FXML
    private Label lblMensaje;

    @FXML
    private TableView<Paciente> tblPacientes;

    @FXML
    private TextField txtNombre;

    @FXML
    private TextField txtTelefono;

    private final ObservableList<Paciente> pacientes = FXCollections.observableArrayList();
    private Repository<Paciente> pacienteRepo = new PacienteRepository(new GsonPacienteDao(Paths.PACIENTES,Paciente.class));

    @FXML
    void btnCancelar(ActionEvent event) {
        limpiarCampos();


    }

    @FXML
    void btnEliminar(ActionEvent event) {
        Paciente sel = tblPacientes.getSelectionModel().getSelectedItem();
        if (sel == null){
            mensaje("debes selecionar un paciente para eliminar",false);
        }
        Alert confirmacion = new Alert(Alert.AlertType.CONFIRMATION,
                "Eliminar\n" + sel.getNombre() + "\n?", ButtonType.YES, ButtonType.NO);
        if (confirmacion.showAndWait().orElse(ButtonType.NO) == ButtonType.YES){
            try {
                pacienteRepo.eliminar(sel);
                pacientes.setAll(pacienteRepo.listar());
                mensaje(sel.getNombre()+" Eliminado",true);
            }catch (PersistenciaException e){
                mensaje("Error al eliminar: "+e.getMessage(),false);
            }
        }
    }

    @FXML
    void btnGuardar(ActionEvent event) {
        if (!validarFormulario()){
            return;
        }
        Paciente paciente = new Paciente();
        paciente.setNombre(txtNombre.getText().trim());
        paciente.setTelefono(txtTelefono.getText().trim());
        try {
            pacienteRepo.agregar(paciente);
            pacientes.setAll(pacienteRepo.listar());
            mensaje(paciente.getNombre()+ " Registrado",true);
            limpiarCampos();
        } catch (PersistenciaException e) {
            mensaje("Error al guardar: "+e.getMessage(),false);
        }



    }

    @FXML
    void btnVolver(ActionEvent event) {
        App.app.setScene(Paths.MAIN_VIEW);

    }

    @FXML
    private void initialize(){
        colNombre.setCellValueFactory(c -> new ReadOnlyObjectWrapper<>(c.getValue().getNombre()));
        colTelefono.setCellValueFactory(c -> new ReadOnlyObjectWrapper<>(c.getValue().getTelefono()));
        tblPacientes.setItems(pacientes);

        try {
            pacienteRepo.cargar();
            pacientes.setAll(pacienteRepo.listar());
            mensaje("datos cargado correctamente",true);
        }catch (PersistenciaException e){
            mensaje("Error al cargar: "+e.getMessage(),false);
        }

    }


    private void limpiarCampos(){
        txtNombre.clear();
        txtTelefono.clear();
    }

    private boolean validarFormulario(){
        if (txtNombre.getText().isBlank() || txtTelefono.getText().isBlank()){
            mensaje("Debes completa todos lso campos",false);
            return false;
        }
        return true;
    }

    private void mensaje(String mensaje, boolean exito){
        if (lblMensaje != null){
            lblMensaje.setText(mensaje);
            lblMensaje.getStyleClass().add(exito ? "resultado-ok" : "resultado-error");
        }
    }

}
