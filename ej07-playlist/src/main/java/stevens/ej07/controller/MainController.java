package stevens.ej07.controller;

import javafx.beans.binding.BooleanBinding;
import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.beans.property.ReadOnlyStringWrapper;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import stevens.ej07.dao.GsonContenidoDao;
import stevens.ej07.dao.PersistenciaException;
import stevens.ej07.model.Cancion;
import stevens.ej07.model.Contenido;
import stevens.ej07.model.Podcast;
import stevens.ej07.repository.ContenidoRepository;
import stevens.ej07.repository.Repository;
import stevens.ej07.util.Paths;

public class MainController {

    @FXML
    private ComboBox<String> cmbTipo;

    @FXML
    private CheckBox chkDescargado;

    @FXML
    private CheckBox chkExplicitas;

    @FXML
    private TableColumn<Contenido, String> colAutor;

    @FXML
    private TableColumn<Contenido, String> colDatosEspecificos;

    @FXML
    private TableColumn<Contenido, Integer> colDuracion;

    @FXML
    private TableColumn<Contenido, String> colGenero;

    @FXML
    private TableColumn<Contenido, Double> colPeso;

    @FXML
    private TableColumn<Contenido, String> colTipo;

    @FXML
    private TableColumn<Contenido, String> colTitulo;

    @FXML
    private Label lblAlbum;

    @FXML
    private Label lblEpisodio;

    @FXML
    private Label lblMensaje;

    @FXML
    private Label lblPlataforma;

    @FXML
    private TableView<Contenido> tblContenido;

    @FXML
    private TextField txtAlbum;

    @FXML
    private TextField txtAutor;

    @FXML
    private TextField txtDuracion;

    @FXML
    private TextField txtEpisodio;


    @FXML
    private TextField txtGenero;

    @FXML
    private TextField txtPlataforma;

    @FXML
    private TextField txtTitulo;

    private final ObservableList<Contenido> contenidos = FXCollections.observableArrayList();
    private final Repository<Contenido> contenidoRepo = new ContenidoRepository(new GsonContenidoDao(Paths.CANCION, Cancion.class),
            new GsonContenidoDao(Paths.PODCAST,Podcast.class)
            );


    private String datosEspecifico(Contenido contenido){
        if (contenido instanceof Cancion cancion){
            return cancion.getAlbum() + " "+cancion.isExplicita();
        } else if (contenido instanceof Podcast podcast) {
            return podcast.getNumeroEpisodio()+" "+podcast.getPlataforma()+" "+podcast.estaDescargado();
        }
        return "";
    }

    @FXML
    void btnCancelar(ActionEvent event) {
        limpiarCampo();
    }

    @FXML
    void btnEliminar(ActionEvent event) {
        Contenido sel = tblContenido.getSelectionModel().getSelectedItem();
        if (sel == null){
            mensaje("debes seleccionar un compo");
            return;
        }
        try {
            contenidoRepo.eliminar(sel);
            contenidos.setAll(contenidoRepo.listar());
            mensaje(sel.getAutor()+" Eliminado");
        }catch (PersistenciaException e){
            mensaje("no se pudo eliminar");
        }

    }

    @FXML
    void btnGuardar(ActionEvent event) {
        String tipo = cmbTipo.getValue();
        if (tipo == null){
            mensaje("selecciona una de las opciones de tipo");
        }
        Contenido contenido = null;
        String titulo = txtTitulo.getText().trim();
        String autor = txtAutor.getText().trim();
        String genero = txtGenero.getText().trim();
        String duracionTxt = txtDuracion.getText().trim();
        int duracion = duracionTxt.isEmpty() ? 0: Integer.parseInt(duracionTxt);
        String album = txtAlbum.getText().trim();
        boolean explicitas = chkExplicitas.isSelected();
        String episodiotxt = txtEpisodio.getText().trim();
        int episodio = episodiotxt.isEmpty() ? 0: Integer.parseInt(episodiotxt);

        String plataforma = txtPlataforma.getText().trim();
        boolean descaragadochk = chkDescargado.isSelected();
        String descargado = String.valueOf(descaragadochk);
        try {
            if ("Cancion".equals(tipo)){
                contenido = new Cancion(titulo,autor,genero,duracion,album,explicitas);
            } else if ("Podcast".equals(tipo)) {
                contenido = new Podcast(titulo,autor,genero,duracion,episodio,plataforma);
            }else {
                mensaje("Tipo no reconocido");
            }

            if (contenido != null){
                contenidoRepo.agregar(contenido);
                contenidos.setAll(contenidoRepo.listar());
                mensaje(contenido.getAutor()+" Guardado");
                limpiarCampo();
            }

        }catch (NumberFormatException e){
            mensaje("Error!! revisa los campos "+e.getMessage());
        }catch (PersistenciaException e){
            mensaje("no se pudo guadar "+e.getMessage());
        }

    }

    @FXML
    private void initialize(){
        cmbTipo.getItems().addAll("Cancion","Podcast");
        cmbTipo.setValue("Cancion");
        BooleanBinding esCancion = cmbTipo.valueProperty().isEqualTo("Cancion");

        lblAlbum.visibleProperty().bind(esCancion);
        txtAlbum.visibleProperty().bind(esCancion);
        chkExplicitas.visibleProperty().bind(esCancion);

        lblEpisodio.visibleProperty().bind(esCancion.not());
        txtEpisodio.visibleProperty().bind(esCancion.not());
        lblPlataforma.visibleProperty().bind(esCancion.not());
        txtPlataforma.visibleProperty().bind(esCancion.not());
        chkDescargado.visibleProperty().bind(esCancion.not());
        tblContenido.setItems(contenidos);

        colTipo.setCellValueFactory(c -> new ReadOnlyStringWrapper(c.getValue().getTipo()));
        colAutor.setCellValueFactory(c -> new ReadOnlyStringWrapper(c.getValue().getAutor()));
        colTitulo.setCellValueFactory(c -> new ReadOnlyStringWrapper(c.getValue().getTitulo()));
        colGenero.setCellValueFactory(c -> new ReadOnlyStringWrapper(c.getValue().getGenero()));
        colDuracion.setCellValueFactory(c -> new ReadOnlyObjectWrapper<>(c.getValue().getDuracionSegundos()));
        colDatosEspecificos.setCellValueFactory(c -> new ReadOnlyObjectWrapper<>(datosEspecifico(c.getValue())));
        colPeso.setCellValueFactory(c -> new ReadOnlyObjectWrapper<>(c.getValue().calcularPesoMB()));

        try {
            contenidoRepo.cargar();
            contenidos.setAll(contenidoRepo.listar());
            mensaje("Datos cargados correctamente");
        } catch (PersistenciaException e) {
            mensaje("Error al cargar los datos");
        }
    }


    private void limpiarCampo(){
        txtTitulo.clear();
        txtAutor.clear();
        txtGenero.clear();
        txtDuracion.clear();
        txtAlbum.clear();
        chkExplicitas.setSelected(false);
        txtEpisodio.clear();
        txtPlataforma.clear();
        chkDescargado.setSelected(false);
    }

    private void mensaje(String texto){
        if (lblMensaje != null){
            lblMensaje.setText(texto);
        }
    }
}
