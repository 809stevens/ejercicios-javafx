package stevens.ej02.controllers;

import javafx.beans.property.ReadOnlyBooleanWrapper;
import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.CheckBoxTableCell;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import stevens.ej02.application.App;
import stevens.ej02.dao.GsonLibroDao;
import stevens.ej02.dao.PersistenciaException;
import stevens.ej02.model.Libro;
import stevens.ej02.repository.LibroRepository;
import stevens.ej02.repository.Repository;
import stevens.ej02.utils.Paths;

import java.io.IOException;

public class MainController {

    @FXML
    private TableColumn<Libro, String> colTitulo;

    @FXML
    private TableColumn<Libro, String> colAutor;

    @FXML
    private TableColumn<Libro, Integer> colIbns;

    @FXML
    private TableColumn<Libro, Boolean> colPrestado;

    @FXML
    private TableColumn<Libro, String> colanio;

    @FXML
    private TableColumn<Libro, String> colFechaDev;

    @FXML
    private CheckBox chkTodos;

    @FXML
    private TableView<Libro> tblLibros;

    @FXML
    private TextField txtBuscar;

    @FXML
    private TextField txtContador;

    @FXML
    private Label lblMensaje;

    private final ObservableList<Libro> libros = FXCollections.observableArrayList();
    private FilteredList<Libro> librosFiltrados;

    private Repository<Libro> libroRepo;

    @FXML
    public void initialize() {
        colTitulo.setCellValueFactory(celda -> new ReadOnlyObjectWrapper<>(celda.getValue().getTitulo()));
        colAutor.setCellValueFactory(celda -> new ReadOnlyObjectWrapper<>(celda.getValue().getAutor()));
        colIbns.setCellValueFactory(celda -> new ReadOnlyObjectWrapper<>(celda.getValue().getIsbn()));
        colanio.setCellValueFactory(celda -> new ReadOnlyObjectWrapper<>(celda.getValue().getAnio()));
        colFechaDev.setCellValueFactory(celda -> new ReadOnlyObjectWrapper<>(celda.getValue().getFechaDev()));
        colPrestado.setCellValueFactory(celda -> new ReadOnlyBooleanWrapper(celda.getValue().isPrestado()));
        colPrestado.setCellFactory(CheckBoxTableCell.forTableColumn(colPrestado));
        colPrestado.setEditable(true);

        tblLibros.setItems(libros);

        libroRepo = new LibroRepository(new GsonLibroDao(Paths.LIBROS, Libro.class));

        try {
            libroRepo.cargar();
            libros.setAll(libroRepo.listar());
            mensaje("Biblioteca cargada correctamente.", true);
        } catch (PersistenciaException e) {
            mensaje("Error al cargar: " + e.getMessage(), false);
        }

        librosFiltrados = new FilteredList<>(libros);
        tblLibros.setItems(librosFiltrados);

        txtBuscar.textProperty().addListener((obs, anterior, nuevo) ->
                librosFiltrados.setPredicate(libro -> coincide(libro, nuevo)));

        tblLibros.setOnMouseClicked(event -> {
            if (event.getClickCount() == 2) {
                abrirDetalle();
            }
        });

        libros.addListener((javafx.collections.ListChangeListener<Libro>) cambio -> actualizarContador());
        actualizarContador();
    }

    @FXML
    void BtnElimianr(ActionEvent event) {
        Libro sel = tblLibros.getSelectionModel().getSelectedItem();
        if (sel == null) {
            mensaje("Selecciona un libro primero.", false);
            return;
        }
        Alert confirmacion = new Alert(Alert.AlertType.CONFIRMATION,
                "Eliminar\n" + sel.getTitulo() + "\n?", ButtonType.YES, ButtonType.NO);
        if (confirmacion.showAndWait().orElse(ButtonType.NO) == ButtonType.YES) {
            try {
                libroRepo.eliminar(sel);
                libros.setAll(libroRepo.listar());
                mensaje(sel.getTitulo() + " eliminado.", true);
            } catch (PersistenciaException e) {
                mensaje("Error al eliminar: " + e.getMessage(), false);
            }
        }
    }

    @FXML
    void btnPrestar(ActionEvent event) {
        Libro sel = tblLibros.getSelectionModel().getSelectedItem();
        if (sel == null) {
            mensaje("Selecciona un libro primero.", false);
            return;
        }
        if (sel.isPrestado()) {
            mensaje("El libro \n" + sel.getTitulo() + "\n ya está prestado.", false);
            return;
        }
        sel.setPrestado(true);
        try {
            libroRepo.eliminar(sel);
            libroRepo.agregar(sel);
            libros.setAll(libroRepo.listar());
            mensaje("Libro prestado correctamente.", true);
        } catch (PersistenciaException e) {
            mensaje("Error al prestar: " + e.getMessage(), false);
        }
    }

    @FXML
    void chkTodos(ActionEvent event) {
        boolean marcar = chkTodos.isSelected();
        for (Libro libro : tblLibros.getItems()) {
            libro.setPrestado(marcar);
        }
        try {
            libroRepo.cargar();
            libros.setAll(libroRepo.listar());
        } catch (PersistenciaException e) {
            mensaje("Error al guardar cambios: " + e.getMessage(), false);
        }
    }

    @FXML
    void btnDetalles(ActionEvent event) {
        abrirDetalle();
    }

    @FXML
    void btnGuardar(ActionEvent event) {
        mensaje("Cambios guardados automáticamente.", true);
    }

    @FXML
    void btnNuevoLibro(ActionEvent event) {
        App.app.setScene(Paths.LIBRO_VIEW);
    }

    private boolean coincide(Libro libro, String texto) {
        if (texto == null || texto.isBlank()) {
            return true;
        }
        String busqueda = texto.trim().toLowerCase();
        return contiene(libro.getTitulo(), busqueda) || contiene(libro.getAutor(), busqueda);
    }

    private boolean contiene(String valor, String busqueda) {
        return valor != null && valor.toLowerCase().contains(busqueda);
    }

    private void actualizarContador() {
        if (libros.size() <= 1) {
            txtContador.setText(" " + libros.size() + " libro");
        } else {
            txtContador.setText(" " + libros.size() + " libros");
        }
    }

    private void abrirDetalle() {
        Libro sel = tblLibros.getSelectionModel().getSelectedItem();
        if (sel == null) {
            mensaje("Selecciona un libro primero.", false);
            return;
        }
        try {
            FXMLLoader loader = new FXMLLoader(App.class.getResource(Paths.DETALLES_VIEW));
            AnchorPane pane = loader.load();
            ((DetalleController) loader.getController()).setLibro(sel);

            Stage stage = new Stage();
            stage.initOwner(App.app.stageWindow);
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.setTitle("Detalle del libro");
            stage.setScene(new Scene(pane));
            stage.setResizable(false);
            stage.showAndWait();
        } catch (IOException e) {
            e.printStackTrace();
            mensaje("Error al abrir detalles: " + e.getMessage(), false);
        }
    }

    private void mensaje(String texto, boolean exito) {
        if (lblMensaje != null) {
            lblMensaje.setText(texto);
            lblMensaje.getStyleClass().removeAll("resultado-ok", "resultado-error");
            lblMensaje.getStyleClass().add(exito ? "resultado-ok" : "resultado-error");
        }
    }
}