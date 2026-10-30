package stevens.ej01.controllers;

import javafx.animation.PauseTransition;
import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.util.Duration;
import stevens.ej01.application.App;
import stevens.ej01.dao.GsonDuenioDao;
import stevens.ej01.dao.GsonMascotaDao;
import stevens.ej01.dao.PersistenciaException;
import stevens.ej01.model.Duenio;
import stevens.ej01.model.Mascota;
import stevens.ej01.repository.DuenioRepository;
import stevens.ej01.repository.MascotaRepository;
import stevens.ej01.repository.Repository;
import stevens.ej01.utils.Paths;

import java.time.LocalDate;

public class MainController {

    @FXML
    private TableColumn<Mascota, String> colNombre;

    @FXML
    private TableColumn<Mascota, String> colEspecie;

    @FXML
    private TableColumn<Mascota, String> colRaza;

    @FXML
    private TableColumn<Mascota, String> colFecNac;

    @FXML
    private TableColumn<Mascota, Duenio> colDuenio;

    @FXML
    private ComboBox<Duenio> cmbDuenio;

    @FXML
    private TableView<Mascota> tblMascota;

    @FXML
    private TextField txtRaza;

    @FXML
    private TextField txtEspecieMascota;

    @FXML
    private DatePicker txtFecNac;

    @FXML
    private TextField txtFiltrar;

    @FXML
    private TextField txtNombreMascota;

    @FXML
    private Label lblMensaje;

    private final ObservableList<Mascota> mascotas = FXCollections.observableArrayList();
    private final ObservableList<Duenio> duenios = FXCollections.observableArrayList();

    private Repository<Mascota> mascotaRepo;
    private Repository<Duenio> duenioRepo;

    @FXML
    public void initialize() {
        colNombre.setCellValueFactory(celda -> new ReadOnlyObjectWrapper<>(celda.getValue().getNombre()));
        colEspecie.setCellValueFactory(celda -> new ReadOnlyObjectWrapper<>(celda.getValue().getEspecie()));
        colRaza.setCellValueFactory(celda -> new ReadOnlyObjectWrapper<>(celda.getValue().getRaza()));
        colFecNac.setCellValueFactory(celda -> new ReadOnlyObjectWrapper<>(celda.getValue().getFechaNac()));
        colDuenio.setCellValueFactory(celda -> new ReadOnlyObjectWrapper<>(celda.getValue().getDuenio()));

        tblMascota.setItems(mascotas);
        cmbDuenio.setItems(duenios);

        mascotaRepo = new MascotaRepository(new GsonMascotaDao(Paths.MASCOTAS, Mascota.class));
        duenioRepo = new DuenioRepository(new GsonDuenioDao(Paths.DUENIOS, Duenio.class));

        try {
            duenioRepo.cargar();
            duenios.setAll(duenioRepo.listar());

            mascotaRepo.cargar();
            mascotas.setAll(mascotaRepo.listar());

            mensaje("Datos cargados correctamente.", true);
        } catch (PersistenciaException e) {
            mensaje("Error al cargar: " + e.getMessage(), false);
        }

        tblMascota.setOnMouseClicked(e -> {
            if (tblMascota.getSelectionModel().getSelectedItem() != null) {
                cargarCampos();
            }
        });
    }

    @FXML
    void btnActualizar(ActionEvent event) {
        Mascota seleccionada = tblMascota.getSelectionModel().getSelectedItem();
        if (seleccionada == null) {
            mensaje("Selecciona una mascota para actualizar.", false);
            return;
        }

        if (!validarFormulario()) {
            return;
        }

        seleccionada.setNombre(txtNombreMascota.getText());
        seleccionada.setEspecie(txtEspecieMascota.getText());
        seleccionada.setRaza(txtRaza.getText());
        seleccionada.setFechaNac(txtFecNac.getValue().toString());
        seleccionada.setDuenio(cmbDuenio.getValue());

        try {
            mascotaRepo.eliminar(seleccionada);
            mascotaRepo.agregar(seleccionada);
            mascotas.setAll(mascotaRepo.listar());
            mensaje("Mascota actualizada.", true);
            limpiarFormulario();
        } catch (PersistenciaException e) {
            mensaje("Error al actualizar: " + e.getMessage(), false);
        }
    }

    @FXML
    void btnEliminar(ActionEvent event) {
        Mascota seleccionada = tblMascota.getSelectionModel().getSelectedItem();
        if (seleccionada == null) {
            mensaje("Selecciona una mascota para eliminar.", false);
            return;
        }

        try {
            mascotaRepo.eliminar(seleccionada);
            mascotas.setAll(mascotaRepo.listar());
            mensaje(seleccionada.getNombre() + " eliminada.", true);
            limpiarFormulario();
        } catch (PersistenciaException e) {
            mensaje("Error al eliminar: " + e.getMessage(), false);
        }
    }

    private void limpiarFormulario() {
        txtNombreMascota.clear();
        txtEspecieMascota.clear();
        txtRaza.clear();
        txtFecNac.setValue(null);
        cmbDuenio.getSelectionModel().clearSelection();
        tblMascota.getSelectionModel().clearSelection();
    }

    @FXML
    void btnGuardar(ActionEvent event) {
        if (!validarFormulario()) {
            return;
        }

        Mascota mascota = new Mascota();
        mascota.setNombre(txtNombreMascota.getText());
        mascota.setEspecie(txtEspecieMascota.getText());
        mascota.setRaza(txtRaza.getText());
        mascota.setFechaNac(txtFecNac.getValue().toString());
        mascota.setDuenio(cmbDuenio.getValue());

        try {
            mascotaRepo.agregar(mascota);
            mascotas.setAll(mascotaRepo.listar());
            mensaje(mascota.getNombre() + " guardada.", true);
            limpiarFormulario();
        } catch (PersistenciaException e) {
            mensaje("Error al guardar: " + e.getMessage(), false);
        }
    }

    @FXML
    void btnNuevoDuenio(ActionEvent event) {
        // TODO: Cambiar a la vista de Dueño
        App.app.setScene(Paths.DUENIO_VIEW);
    }

    private void cargarCampos() {
        Mascota mascota = tblMascota.getSelectionModel().getSelectedItem();

        txtNombreMascota.setText(mascota.getNombre());
        txtEspecieMascota.setText(mascota.getEspecie());
        txtRaza.setText(mascota.getRaza());
        cmbDuenio.setValue(mascota.getDuenio());
    }

    private boolean validarFormulario() {
        if (txtNombreMascota.getText().isBlank() || txtEspecieMascota.getText().isBlank()
                || txtRaza.getText().isBlank()) {
            mensaje("Completa todos los campos de la mascota.", false);
            return false;
        }
        if (txtFecNac.getValue() == null) {
            mensaje("Indica la fecha de nacimiento.", false);
            return false;
        }
        if (txtFecNac.getValue().isAfter(LocalDate.now())) {
            mensaje("La fecha de nacimiento no puede ser futura.", false);
            return false;
        }
        if (cmbDuenio.getValue() == null) {
            mensaje("Selecciona un dueño.", false);
            return false;
        }
        return true;
    }

    private void mensaje(String texto, boolean exito) {
        if (lblMensaje != null) {
            lblMensaje.setText(texto);
            lblMensaje.getStyleClass().removeAll("resultado-ok", "resultado-error");
            lblMensaje.getStyleClass().add(exito ? "resultado-ok" : "resultado-error");

            PauseTransition pausa = new PauseTransition(Duration.seconds(4));

            // 3. Definir la acción que ocurrirá cuando pasen los 10 segundos
            pausa.setOnFinished(event -> {
                lblMensaje.setText(""); // Borra el texto
                lblMensaje.getStyleClass().removeAll("resultado-ok", "resultado-error"); // Quita los estilos si lo deseas
            });

            // 4. Iniciar la cuenta regresiva
            pausa.play();
        }
    }
}