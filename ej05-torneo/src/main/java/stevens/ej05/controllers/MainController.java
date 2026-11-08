package stevens.ej05.controllers;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import stevens.ej05.model.Participante;
import stevens.ej05.repository.ParticipanteRepository;

/**
 * Controlador de la vista principal del torneo.
 *
 * TODO: completa initialize(), los manejadores y el cálculo del puntaje total.
 * Los @FXML ya declaran los controles que debe tener la vista; usa los mismos
 * fx:id en Scene Builder.
 */
public class MainController {

    @FXML
    private ComboBox<String> cmbTipo;

    @FXML
    private TextField txtNombre;

    @FXML
    private TextField txtEquipo;

    @FXML
    private TextField txtEdad;

    @FXML
    private TextField txtNickname;

    @FXML
    private TextField txtRol;

    @FXML
    private TextField txtPartidasGanadas;

    @FXML
    private TextField txtEspecialidad;

    @FXML
    private TextField txtAniosExperiencia;

    @FXML
    private Label lblNickname;

    @FXML
    private Label lblRol;

    @FXML
    private Label lblPartidasGanadas;

    @FXML
    private Label lblEspecialidad;

    @FXML
    private Label lblAniosExperiencia;

    @FXML
    private Label lblMensaje;

    @FXML
    private Label lblPuntajeTotal;

    @FXML
    private TableView<Participante> tblParticipantes;

    @FXML
    private TableColumn<Participante, String> colTipo;

    @FXML
    private TableColumn<Participante, String> colNombre;

    @FXML
    private TableColumn<Participante, String> colEquipo;

    @FXML
    private TableColumn<Participante, Integer> colEdad;

    @FXML
    private TableColumn<Participante, Integer> colPuntaje;

    @FXML
    private TableColumn<Participante, String> colDatosEspecificos;

    private final ObservableList<Participante> participantes = FXCollections.observableArrayList();
    private ParticipanteRepository participanteRepo;

    @FXML
    public void initialize() {
        // TODO 1: crear participanteRepo con dos GsonParticipanteDao:
        //         new ParticipanteRepository(
        //             new GsonParticipanteDao(Paths.JUGADORES, Jugador.class),
        //             new GsonParticipanteDao(Paths.ENTRENADORES, Entrenador.class));
        // TODO 2: configurar las columnas con ReadOnlyObjectWrapper.
        // TODO 3: tblParticipantes.setItems(participantes);
        // TODO 4: cmbTipo.getItems().addAll("Jugador", "Entrenador") y mostrar el primer tipo.
        // TODO 5: cargar los datos con participanteRepo.cargar() y refrescar la tabla.
    }

    @FXML
    public void cmbTipoCambio(ActionEvent event) {
        // TODO: mostrar solo los campos del subtipo seleccionado.
    }

    @FXML
    public void btnRegistrar(ActionEvent event) {
        // TODO: validar, crear Jugador o Entrenador, llamar a agregar(...) y refrescar.
    }

    @FXML
    public void btnEliminar(ActionEvent event) {
        // TODO: comprobar que haya una fila seleccionada, eliminar y refrescar.
    }

    private void actualizarPuntajeTotal() {
        // TODO: sumar participante.calcularPuntaje() y mostrarlo en lblPuntajeTotal.
    }

    private void mostrarMensaje(String texto, boolean exito) {
        // TODO: lblMensaje.setText(texto) y estilos resultado-ok / resultado-error.
    }
}
