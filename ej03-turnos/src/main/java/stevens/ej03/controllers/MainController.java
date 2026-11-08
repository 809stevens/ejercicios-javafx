package stevens.ej03.controllers;

import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import stevens.ej03.application.App;
import stevens.ej03.dao.GsonDoctorDao;
import stevens.ej03.dao.GsonPacienteDao;
import stevens.ej03.dao.GsonTurnosDao;
import stevens.ej03.dao.PersistenciaException;
import stevens.ej03.model.Doctor;
import stevens.ej03.model.Paciente;
import stevens.ej03.model.Turno;
import stevens.ej03.repository.DoctorRepository;
import stevens.ej03.repository.PacienteRepository;
import stevens.ej03.repository.Repository;
import stevens.ej03.repository.TurnoRepository;
import stevens.ej03.utils.Paths;

import java.time.LocalDate;

public class MainController {

    @FXML
    private ComboBox<Doctor> cmbDoctor;

    @FXML
    private ComboBox<Paciente> cmbPaciente;

    @FXML
    private TableColumn<Turno, Doctor> colDoctor;

    @FXML
    private TableColumn<Turno, String> colFecha;

    @FXML
    private TableColumn<Turno, String> colHora;

    @FXML
    private TableColumn<Turno, Paciente> colPaciente;

    @FXML
    private Label lblMensaje;

    @FXML
    private DatePicker dpkFecha;

    @FXML
    private TableView<Turno> tblTurnos;

    @FXML
    private TextField txtHora;

    private final ObservableList<Turno> turnos = FXCollections.observableArrayList();
    private final ObservableList<Doctor> doctors = FXCollections.observableArrayList();
    private final ObservableList<Paciente> pacientes  = FXCollections.observableArrayList();

    private Repository<Turno> turnoRepo = new TurnoRepository(new GsonTurnosDao(Paths.TURNOS, Turno.class));
    private Repository<Doctor> doctorRepo = new DoctorRepository(new GsonDoctorDao(Paths.DOCTOR,Doctor.class));
    private Repository<Paciente> pacienteRepo = new PacienteRepository(new GsonPacienteDao(Paths.PACIENTES,Paciente.class));


    @FXML
    void btnCancelar(ActionEvent event) {
        limpiarCampos();
    }

    @FXML
    void btnEliminar(ActionEvent event) {
        Turno sel = tblTurnos.getSelectionModel().getSelectedItem();
        if (sel == null){
            mensaje("debes elegir un turno para eliminar",false);
            return;
        }
        Alert confirmacion = new Alert(Alert.AlertType.CONFIRMATION,
                "Eliminar\n" + sel.getFecha() +"\n?", ButtonType.YES, ButtonType.NO);
        if (confirmacion.showAndWait().orElse(ButtonType.NO) == ButtonType.YES){
            try {
                turnoRepo.eliminar(sel);
                turnos.setAll(turnoRepo.listar());
                mensaje("turno eliminado",true);
            } catch (PersistenciaException e) {
                mensaje("no se pudo eliminar el turno: "+e.getMessage(),false);
            }
        }
    }

    @FXML
    void btnGuardar(ActionEvent event) {
        if (!validarFormulario()){
            return;
        }
        Turno turno = new Turno();
        turno.setDoctor(cmbDoctor.getValue());
        turno.setPaciente(cmbPaciente.getValue());
        turno.setFecha(String.valueOf(dpkFecha.getValue()));
        turno.setHora(txtHora.getText());
        try {
            turnoRepo.agregar(turno);
            turnos.setAll(turnoRepo.listar());
            mensaje("guadardo",true);
        } catch (PersistenciaException e) {
            mensaje("error al guadar: "+e.getMessage(),false);
        }
        limpiarCampos();
    }

    @FXML
    void btnNuevoDoctor(ActionEvent event) {
        App.app.setScene(Paths.DOCTOR_VIEW);
    }

    @FXML
    void btnNuevoPaciente(ActionEvent event) {
        App.app.setScene(Paths.PACIENTE_VIEW);
    }

    @FXML
    private void initialize(){
        colDoctor.setCellValueFactory(c -> new ReadOnlyObjectWrapper<>(c.getValue().getDoctor()));
        colPaciente.setCellValueFactory(c -> new ReadOnlyObjectWrapper<>(c.getValue().getPaciente()));
        colFecha.setCellValueFactory(c -> new ReadOnlyObjectWrapper<>(c.getValue().getFecha()));
        colHora.setCellValueFactory(c -> new ReadOnlyObjectWrapper<>(c.getValue().getHora()));
        tblTurnos.setItems(turnos);
        cmbDoctor.setItems(doctors);
        cmbPaciente.setItems(pacientes);

        try {
            turnoRepo.cargar();
            turnos.setAll(turnoRepo.listar());

            doctorRepo.cargar();
            doctors.setAll(doctorRepo.listar());

            pacienteRepo.cargar();
            pacientes.setAll(pacienteRepo.listar());

            mensaje("datos cargado correctamente",true);
        }catch (PersistenciaException e){
            mensaje("no se pudo cargar: "+e.getMessage(),false);
        }
    }

    private void limpiarCampos(){
        cmbDoctor.getSelectionModel().clearSelection();
        cmbPaciente.getSelectionModel().clearSelection();
        dpkFecha.setValue(null);
        txtHora.clear();
    }


    private boolean validarFormulario(){
        if (cmbDoctor.getValue() == null || cmbPaciente.getValue() == null|| dpkFecha.getValue() == null
        || txtHora.getText().isBlank()){
            mensaje("debes completar todos loc campos",false);
            return false;
        }
        if (dpkFecha.getValue().isBefore(LocalDate.now())){
            mensaje("no pudes eligir fecha del pasado",false);
            return false;
        }
        if (!txtHora.getText().matches("^\\d{0,2}(:?\\d{0,2})?$")){
            mensaje("debes ingrear horario valido",false);
            return false;
        }

        Doctor doctorSeleccionado = cmbDoctor.getValue();
        String fechaSeleccionada = String.valueOf(dpkFecha.getValue());
        String horaSeleccionada = txtHora.getText().trim();

        boolean doctorOcupado = turnos.stream().anyMatch(t ->
                t.getDoctor() != null && t.getDoctor().equals(doctorSeleccionado) &&
                        fechaSeleccionada.equals(t.getFecha()) &&
                        horaSeleccionada.equals(t.getHora())
        );

        if (doctorOcupado) {
            mensaje("El doctor ya tiene un turno a esa hora", false);
            return false;
        }

        return true;
    }

    private void mensaje(String mensaje,boolean exito){
        if (lblMensaje != null){
            lblMensaje.setText(mensaje);
            lblMensaje.getStyleClass().add(exito? "resultado-ok": "resultado-error");
        }
    }



}
