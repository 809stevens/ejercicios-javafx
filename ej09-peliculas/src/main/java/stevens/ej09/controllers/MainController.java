package stevens.ej09.controllers;

import javafx.beans.property.ReadOnlyObjectProperty;
import javafx.beans.property.ReadOnlyStringWrapper;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import stevens.ej09.dao.GsonPeliculaDao;
import stevens.ej09.dao.PersistenciaException;
import stevens.ej09.model.Pelicula;
import stevens.ej09.repository.PeliculaRepository;
import stevens.ej09.repository.Repository;
import stevens.ej09.utils.Paths;

import java.io.IOException;
import java.nio.file.Path;

public class MainController {

    @FXML
    private CheckBox chkVista;

    @FXML
    private TableColumn<Pelicula, Integer> colAnio;

    @FXML
    private TableColumn<Pelicula, String> colDirector;

    @FXML
    private TableColumn<Pelicula, Integer> colDuracion;

    @FXML
    private TableColumn<Pelicula, String> colGenero;

    @FXML
    private TableColumn<Pelicula, String> colTitulo;

    @FXML
    private TableColumn<Pelicula, Boolean> colVista;

    @FXML
    private Label lblMensaje;

    @FXML
    private TableView<Pelicula> tblPeliculas;

    @FXML
    private TextField txtAnio;

    @FXML
    private TextField txtDirector;

    @FXML
    private TextField txtDuracion;

    @FXML
    private TextField txtGenero;

    @FXML
    private TextField txtTitulo;

    private final ObservableList<Pelicula> peliculas = FXCollections.observableArrayList();
    private Repository<Pelicula> peliculaRepo = new PeliculaRepository(new GsonPeliculaDao(Paths.PELICULAS,Pelicula.class));

    @FXML
    void btnEliminar(ActionEvent event) {
        Pelicula sel = tblPeliculas.getSelectionModel().getSelectedItem();
        if (sel == null){
            mensaje("Selecciona una pelicula para eliminar", false);
            return;
        }
        try {
            peliculaRepo.eliminar(sel);
            peliculas.setAll(peliculaRepo.listar());
            mensaje(sel.getTitulo()+" eliminado correctamente", true);
        }catch (PersistenciaException | IOException e){
            mensaje("ERROR al eliminar: "+ e.getMessage(),false);
        }
    }

    @FXML
    void btnGuardar(ActionEvent event) {
        if (!validarFormulario()){
            return;
        }
        Pelicula pelicula = new Pelicula();
        pelicula.setTitulo(txtTitulo.getText());
        pelicula.setDirector(txtDirector.getText());
        pelicula.setAnio(Integer.parseInt(txtAnio.getText().trim()));
        pelicula.setDuracionMin(Integer.parseInt(txtDuracion.getText().trim()));
        pelicula.setGenero(txtGenero.getText());
        pelicula.setVista(chkVista.isSelected());
        tblPeliculas.setItems(peliculas);


        try {
            peliculaRepo.agregar(pelicula);
            peliculas.setAll(peliculaRepo.listar());
            mensaje(pelicula.getTitulo()+ " Pelicula guardado correctamente", true);
        }catch (PersistenciaException e){
            mensaje("Error al guardar: "+ e.getMessage(), false);

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        Limpiar();
    }

    @FXML
    void btnLimpiar(ActionEvent event) {
        Limpiar();

    }

    public void initialize(){
        colTitulo.setCellValueFactory(new PropertyValueFactory<>("titulo"));
        colDirector.setCellValueFactory( c -> new ReadOnlyStringWrapper(c.getValue().getDirector()));
        colDuracion.setCellValueFactory(new PropertyValueFactory<>("duracionMin"));
        colAnio.setCellValueFactory(new  PropertyValueFactory<>("anio"));
        colGenero.setCellValueFactory(new PropertyValueFactory<>("genero"));
        colVista.setCellValueFactory(new PropertyValueFactory<>("vista"));
        colVista.setEditable(true);
        tblPeliculas.setItems(peliculas);

        try {
            peliculaRepo.cargar();
            peliculas.setAll(peliculaRepo.listar());
            mensaje("Catalogo cargado correctamete", true);
        }catch (PersistenciaException e){
            mensaje("Error al cargar catalogo ", false);
        }


    }

    private boolean validarFormulario(){
        if (txtTitulo.getText().isBlank() | txtDirector.getText().isBlank() | txtGenero.getText().isBlank()){
            mensaje("Debes completar los paremetos", false);
            return false;
        }
        int anio;
        try {
            anio = Integer.parseInt(txtAnio.getText().trim());

        }catch (NumberFormatException e){
            mensaje("El año deber ser un numero",false);
            return false;
        }
        int duracion = 0;
        try {
            duracion = Integer.parseInt(txtDuracion.getText().trim());
        }catch (NumberFormatException e){
            mensaje("la duracion debe ser un numero",false);
            return false;
        }
        if (duracion < 1 ){
            mensaje("la duracion debe ser mayor a 1 minuto",false);
            return false;
        }
        return true;

    }


    private void mensaje(String texto, boolean exito){
        if (lblMensaje != null){
            lblMensaje.setText(texto);

        }
    }

    private void Limpiar(){
        txtDuracion.clear();
        txtTitulo.clear();
        txtDirector.clear();
        txtGenero.clear();
        txtAnio.clear();
        chkVista.setSelected(false);

    }

    private void mostrarFormulario(Pelicula pelicula){
        txtTitulo.setText(pelicula.getTitulo());
        txtDirector.setText(pelicula.getDirector());
        txtAnio.setText(String.valueOf(pelicula.getAnio()));
        colDuracion.setText(String.valueOf(pelicula.getDuracionMin()));
        chkVista.setSelected(pelicula.isVista());
    }

}
