package stevens.ej02.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.CheckBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import stevens.ej02.application.App;
import stevens.ej02.dao.GsonLibroDao;
import stevens.ej02.dao.PersistenciaException;
import stevens.ej02.model.Libro;
import stevens.ej02.repository.LibroRepository;
import stevens.ej02.repository.Repository;
import stevens.ej02.utils.Paths;

import java.time.LocalDate;

public class LibroController {

    @FXML
    private DatePicker txtAnio;

    @FXML
    private TextField txtAutor;

    @FXML
    private DatePicker txtFechaDev;

    @FXML
    private TextField txtIsbn;

    @FXML
    private CheckBox txtPrestada;

    @FXML
    private TextField txtTitulo;

    private final Repository<Libro> libroRepo;

    public LibroController() {
        libroRepo = new LibroRepository(new GsonLibroDao(Paths.LIBROS, Libro.class));
    }

    @FXML
    void btnCancelar(ActionEvent event) {
        App.app.setScene(Paths.MAIN_VIEW);
    }

    @FXML
    void btnGuardar(ActionEvent event) {
        if (!validarFormulario()) {
            return;
        }

        int isbn = Integer.parseInt(txtIsbn.getText().trim());

        try {
            libroRepo.cargar();
            if (libroRepo.listar().stream().anyMatch(l -> l.getIsbn() == isbn)) {
                new Alert(Alert.AlertType.WARNING, "Ya existe un libro con el ISBN " + isbn + ".").show();
                return;
            }
        } catch (PersistenciaException e) {
            new Alert(Alert.AlertType.ERROR, "Error al verificar ISBN: " + e.getMessage()).show();
            return;
        }

        Libro libro = new Libro();
        libro.setTitulo(txtTitulo.getText().trim());
        libro.setAutor(txtAutor.getText().trim());
        libro.setIsbn(isbn);
        libro.setAnio(txtAnio.getValue().toString());
        libro.setFechaDev(txtFechaDev.getValue().toString());
        libro.setPrestado(txtPrestada.isSelected());

        try {
            libroRepo.agregar(libro);
            App.app.setScene(Paths.MAIN_VIEW);
        } catch (PersistenciaException e) {
            new Alert(Alert.AlertType.ERROR, "Error al guardar: " + e.getMessage()).show();
        }
    }

    private boolean validarFormulario() {
        if (txtTitulo.getText().isBlank() || txtAutor.getText().isBlank()) {
            new Alert(Alert.AlertType.WARNING, "Completa todos los campos del libro.").show();
            return false;
        }
        if (!txtIsbn.getText().trim().matches("\\d+")) {
            new Alert(Alert.AlertType.WARNING, "El ISBN debe ser un número.").show();
            return false;
        }
        if (txtFechaDev.getValue() == null || txtAnio.getValue() == null) {
            new Alert(Alert.AlertType.WARNING, "Indica la fecha.").show();
            return false;
        }
        if (txtAnio.getValue().isAfter(LocalDate.now())) {
            new Alert(Alert.AlertType.WARNING, "El año no puede ser futuro.").show();
            return false;
        }
        return true;
    }
}