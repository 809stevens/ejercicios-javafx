package stevens.ej01.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import stevens.ej01.application.App;
import stevens.ej01.dao.GsonDuenioDao;
import stevens.ej01.dao.PersistenciaException;
import stevens.ej01.model.Duenio;
import stevens.ej01.repository.DuenioRepository;
import stevens.ej01.repository.Repository;
import stevens.ej01.utils.Paths;

public class DuenioController {

    @FXML
    private TextField txtEmail;

    @FXML
    private TextField txtNombre;

    @FXML
    private TextField txtTelefono;

    @FXML
    private Label lblMensaje;

    private final Repository<Duenio> duenioRepo;

    public DuenioController() {
        duenioRepo = new DuenioRepository(new GsonDuenioDao(Paths.DUENIOS, Duenio.class));
    }

    @FXML
    void btnCancelar(ActionEvent event) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setHeaderText(null);
        alert.setTitle("CONFIRMAR");
        alert.setContentText("Seguro que quieres cancelar el proceso");
        alert.showAndWait();
        App.app.setScene(Paths.MASCOTA_VIEW);
    }

    @FXML
    void btnGuardar(ActionEvent event) {
        String nombre = txtNombre.getText().trim();
        String telefono = txtTelefono.getText().trim();
        String email = txtEmail.getText().trim();

        if (nombre.isEmpty() || telefono.isEmpty() || email.isEmpty()) {
            mensaje("Todos los campos son obligatorios.", false);
            return;
        }

        Duenio duenio = new Duenio(nombre, telefono, email);

        try {
            duenioRepo.agregar(duenio);
            mensaje(nombre + " guardado correctamente.", true);
            limpiarFormulario();
            App.app.setScene(Paths.MASCOTA_VIEW);
        } catch (PersistenciaException e) {
            mensaje("Error al guardar: " + e.getMessage(), false);
        }

    }

    private void limpiarFormulario() {
        txtNombre.clear();
        txtTelefono.clear();
        txtEmail.clear();
    }

    private void mensaje(String texto, boolean exito) {
        if (lblMensaje != null) {
            lblMensaje.setText(texto);
            lblMensaje.getStyleClass().removeAll("resultado-ok", "resultado-error");
            lblMensaje.getStyleClass().add(exito ? "resultado-ok" : "resultado-error");
        }
    }
}