package stevens.ej08.controllers;

import javafx.animation.PauseTransition;
import javafx.beans.property.ReadOnlyStringWrapper;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.util.Duration;
import stevens.ej08.dao.GsonContactoDao;
import stevens.ej08.dao.PersistenciaException;
import stevens.ej08.model.Contacto;
import stevens.ej08.repository.ContactoRepository;
import stevens.ej08.repository.Repository;
import stevens.ej08.utils.Paths;

public class MainController {

    @FXML
    private TableColumn<Contacto, String> colNombre;

    @FXML
    private TableColumn<Contacto, String> colEmail;

    @FXML
    private TableColumn<Contacto, String> colTelefono;

    @FXML
    private TableView<Contacto> tblContacto;

    @FXML
    private TextField txtNombre;

    @FXML
    private TextField txtEmail;

    @FXML
    private TextField txtTelefono;

    @FXML
    private Label lblMensaje;

    private final ObservableList<Contacto> contactos = FXCollections.observableArrayList();
    private Repository<Contacto> contactoRepo;

    @FXML
    public void initialize() {
        colNombre.setCellValueFactory(celda -> new ReadOnlyStringWrapper(celda.getValue().getNombre()));
        colEmail.setCellValueFactory(celda -> new ReadOnlyStringWrapper(celda.getValue().getEmail()));
        colTelefono.setCellValueFactory(celda -> new ReadOnlyStringWrapper(celda.getValue().getTelefono()));

        tblContacto.setItems(contactos);

        contactoRepo = new ContactoRepository(new GsonContactoDao(Paths.CONTACTOS,Contacto.class));

        try {
            contactoRepo.cargar();
            contactos.setAll(contactoRepo.listar());
            mensaje("Datos cargado correctamente",true);
        }catch (PersistenciaException e){
            mensaje("Error al cargar"+ e.getMessage(),true);
        }



    }

    @FXML
    void btnGuardar(ActionEvent event) {
        if (!validarFormulario()){
            return;
        }
        Contacto contacto = new Contacto();

        contacto.setNombre(txtNombre.getText());
        contacto.setEmail(txtEmail.getText());
        contacto.setTelefono(txtTelefono.getText());

        try {
            contactoRepo.agregar(contacto);
            contactos.setAll(contactoRepo.listar());
            mensaje(contacto.getNombre() + " Guardado correctamente", true);


        }catch (PersistenciaException e){
            mensaje("Error al guardar: "+ e.getMessage(), false);
        }

        limpiarFormulario();
    }

    @FXML
    void btneliminar(ActionEvent event) {
        Contacto sel = tblContacto.getSelectionModel().getSelectedItem();

        if (sel == null){
            mensaje("Selecciona un contacto para eliminar",false);
            return;
        }

        try {
            contactoRepo.eliminar(sel);
            contactos.setAll(contactoRepo.listar());
            mensaje(sel.getNombre()+ " eliminado",true);
            limpiarFormulario();
        }catch (PersistenciaException e){
            mensaje("Error al eliminar: "+e.getMessage(),false);
        }
    }

    @FXML
    void btnCancelar(ActionEvent event) {
        limpiarFormulario();
    }

    private void limpiarFormulario() {
        txtNombre.clear();
        txtEmail.clear();
        txtTelefono.clear();
        tblContacto.getSelectionModel().clearSelection();
    }

    private void mensaje(String texto, boolean exito) {
        if (lblMensaje != null) {
            lblMensaje.setText(texto);
            lblMensaje.getStyleClass().removeAll("resultado-ok", "resultado-error");
            lblMensaje.getStyleClass().add(exito ? "resultado-ok" : "resultado-error");

            PauseTransition pausa = new PauseTransition(Duration.seconds(4));

            pausa.setOnFinished(event -> {
                lblMensaje.setText("");
                lblMensaje.getStyleClass().removeAll("resultado-ok", "resultado-error");
            });
            pausa.play();
        }
    }

    private boolean validarFormulario(){
        if (txtNombre.getText().isBlank() || txtEmail.getText().isBlank() || txtTelefono.getText().isBlank()){
            mensaje("Completa todos los campos ",false);
            return false;
        }
        return true;
    }
}