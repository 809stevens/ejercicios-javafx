package stevens.ej03.controllers;

import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import stevens.ej03.application.App;
import stevens.ej03.dao.GsonDoctorDao;
import stevens.ej03.dao.PersistenciaException;
import stevens.ej03.model.Doctor;
import stevens.ej03.repository.DoctorRepository;
import stevens.ej03.repository.Repository;
import stevens.ej03.utils.Paths;

public class DoctorController {

    @FXML
    private TableColumn<Doctor, String> colEspecilidad;

    @FXML
    private TableColumn<Doctor, String> colNombre;

    @FXML
    private Label lblMensaje;

    @FXML
    private TableView<Doctor> tblDoctor;

    @FXML
    private TextField txtEspecialidad;

    @FXML
    private TextField txtNombre;

    private final ObservableList<Doctor> doctors = FXCollections.observableArrayList();
    private Repository<Doctor> doctorRepo = new DoctorRepository(new GsonDoctorDao(Paths.DOCTOR,Doctor.class));

    @FXML
    void btnCancelar(ActionEvent event) {
        limpiarCampos();

    }

    @FXML
    void btnEliminar(ActionEvent event) {
        Doctor sel = tblDoctor.getSelectionModel().getSelectedItem();
        if (sel == null){
            mensaje("debes seleccionar un doctor para eliminar",false);
        }
        Alert confirmacion = new Alert(Alert.AlertType.CONFIRMATION,
                "Eliminar\n" + sel.getNombre() + "\n?", ButtonType.YES, ButtonType.NO);
        if (confirmacion.showAndWait().orElse(ButtonType.NO) == ButtonType.YES){
            try {
                doctorRepo.eliminar(sel);
                doctors.setAll(doctorRepo.listar());
                mensaje(sel.getNombre()+" eliminado",true);
            } catch (PersistenciaException e) {
                mensaje(sel.getNombre()+" no se pudo eliminar: "+e.getMessage(),false);
            }
        }
    }

    @FXML
    void btnGuardar(ActionEvent event) {
        if (!validarFormulario()){
            return;
        }
        Doctor doctor = new Doctor();
        doctor.setNombre(txtNombre.getText().trim());
        doctor.setEspecialidad(txtEspecialidad.getText().trim());
        try {
            doctorRepo.agregar(doctor);
            doctors.setAll(doctorRepo.listar());
            mensaje(doctor.getNombre()+" Registrado",true);
            limpiarCampos();
        } catch (PersistenciaException e) {
            mensaje("Error al guadar: "+e.getMessage(),false);
        }
    }

    @FXML
    private void initialize(){
        colNombre.setCellValueFactory(c -> new ReadOnlyObjectWrapper<>(c.getValue().getNombre()));
        colEspecilidad.setCellValueFactory(c -> new ReadOnlyObjectWrapper<>(c.getValue().getEspecialidad()));
        tblDoctor.setItems(doctors);

        try {
            doctorRepo.cargar();
            doctors.setAll(doctorRepo.listar());
            mensaje("datos cargado correctamente",true);
        } catch (PersistenciaException e) {
            mensaje("no se pudo cargar los datos: "+e.getMessage(),false);
        }
    }


    @FXML
    void btnVolver(ActionEvent event) {
        App.app.setScene(Paths.MAIN_VIEW);

    }

    private void limpiarCampos(){
        txtNombre.clear();
        txtEspecialidad.clear();
    }


    private boolean validarFormulario(){
        if (txtNombre.getText().isBlank() || txtEspecialidad.getText().isBlank()){
            mensaje("debes completar todos los campos",false);
            return false;
        }
        return true;
    }

    private void mensaje(String mensaje, boolean exito){
        if (lblMensaje != null){
            lblMensaje.setText(mensaje);
            lblMensaje.getStyleClass().add(exito ? "resultado-ok":"resultado-error");
        }
    }

}
