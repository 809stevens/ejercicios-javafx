package stevens.ej02.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.stage.Stage;
import stevens.ej02.model.Libro;

public class DetalleController {

    @FXML
    private Label lblAnioDetalles;

    @FXML
    private Label lblAutorDetalles;

    @FXML
    private Label lblFechaDevDetalles;

    @FXML
    private Label lblPrestadoDetalles;

    @FXML
    private Label lblIsbnDetalles;

    @FXML
    private Label lblTituloDetalles;

    private Libro libro;

    public void setLibro(Libro l){
        this.libro = l;
        mostrarDetalles();
    }

    private void mostrarDetalles(){
        if (libro == null){
            return;
        }
        lblTituloDetalles.setText(libro.getTitulo());
        lblAutorDetalles.setText(libro.getAutor());
        lblIsbnDetalles.setText(String.valueOf(libro.getIsbn()));
        lblAnioDetalles.setText(libro.getAnio());
        lblFechaDevDetalles.setText(libro.getFechaDev());
        lblPrestadoDetalles.setText(libro.isPrestado() ? "Si":"No");
    }

    @FXML
    void btnCerrar(ActionEvent event) {
        Stage stage = (Stage) lblTituloDetalles.getScene().getWindow();
        stage.close();
    }

}
