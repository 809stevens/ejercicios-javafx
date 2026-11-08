package stevens.ej04.controllers;

import javafx.beans.binding.BooleanBinding;
import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import stevens.ej04.dao.GsonProductoDao;
import stevens.ej04.dao.PersistenciaException;
import stevens.ej04.model.Producto;
import stevens.ej04.model.ProductoNoPerecedero;
import stevens.ej04.model.ProductoPerecedero;
import stevens.ej04.repository.JsonRepository;
import stevens.ej04.repository.ProductoRepository;
import stevens.ej04.utils.Paths;

public class MainController {

    @FXML
    private ComboBox<String> cmbTipo;

    @FXML
    private TableColumn<Producto, String> colCategoria;

    @FXML
    private TableColumn<Producto, String> colDatosEspecifico;

    @FXML
    private TableColumn<Producto, String> colNombre;

    @FXML
    private TableColumn<Producto, Integer> colPrecioBase;

    @FXML
    private TableColumn<Producto, Integer> colPrecioFinal;

    @FXML
    private TableColumn<Producto, Integer> colStock;

    @FXML
    private TableColumn<Producto, String> colTipo;

    @FXML
    private Label lblDias;

    @FXML
    private Label lblGarantia;

    @FXML
    private Label lblMensaje;

    @FXML
    private TableView<Producto> tblProducto;

    @FXML
    private TextField txtCategoria;

    @FXML
    private TextField txtDias;

    @FXML
    private TextField txtGarantia;

    @FXML
    private TextField txtNombre;

    @FXML
    private TextField txtPrecio;

    @FXML
    private TextField txtStock;

    private final ObservableList<Producto> productos = FXCollections.observableArrayList();
    private final JsonRepository<Producto> productoRepo = new ProductoRepository(new GsonProductoDao(Paths.PERECEDEROS, ProductoPerecedero.class),
            new GsonProductoDao(Paths.NO_PERECEDEROS, ProductoNoPerecedero.class)
            );

    private String datosEspecifico(Producto producto){
        if (producto instanceof ProductoPerecedero perecedero){
            return perecedero.getDiasParaVencer()+" dias";
        } else if (producto instanceof ProductoNoPerecedero noPerecedero) {
            return "Garantía "+noPerecedero.getMesesGarantia()+" meses";
        }
        return "";

    }

    @FXML
    void btnCancelar(ActionEvent event) {
        limpiarCampos();
    }

    @FXML
    void btnEliminar(ActionEvent event) {
        Producto sel = tblProducto.getSelectionModel().getSelectedItem();
        if (sel == null){
            mensaje("Debes seleccionar un item para eliminar");
            return;
        }
        Alert confirmacion = new Alert(Alert.AlertType.CONFIRMATION,
                "eliminar\n"+sel.getNombre()+"?",ButtonType.YES,ButtonType.NO);
        if (confirmacion.showAndWait().orElse(ButtonType.NO) == ButtonType.YES){
            try {
                productoRepo.eliminar(sel);
                productos.setAll(productoRepo.listar());
                mensaje(sel.getNombre()+" Eliminado correctamente");
            }catch (PersistenciaException e){
                mensaje("Error al eliminar");
            }
        }

    }

    @FXML
    void btnRegistrar(ActionEvent event) {
        String tipo = cmbTipo.getValue();
        if (tipo == null) {
            mensaje("Por favor, selecciona un tipo de producto");
            return;
        }

        try {
            String nombre = txtNombre.getText().trim();

            String categoria = txtCategoria.getText().trim();
            int precioBase = Integer.parseInt(txtPrecio.getText().trim());
            int stock = Integer.parseInt(txtStock.getText().trim());


            String diasTxt = txtDias.getText().trim();
            int diasParaVencer = diasTxt.isEmpty() ? 0 : Integer.parseInt(diasTxt);

            String garantiaTxt = txtGarantia.getText().trim();
            int mesesGarantia = garantiaTxt.isEmpty() ? 0 : Integer.parseInt(garantiaTxt);



            Producto producto = null;

            if ("ProductoPerecedero".equals(tipo)) {
                producto = new ProductoPerecedero(nombre, categoria, precioBase, stock, diasParaVencer);
            } else if ("ProductoNoPerecedero".equals(tipo)) {
                producto = new ProductoNoPerecedero(nombre, categoria, precioBase, stock, mesesGarantia);
            }

            if (producto != null) {
                productoRepo.agregar(producto);
                productos.setAll(productoRepo.listar());
                mensaje(producto.getNombre() + " guardado correctamente");
                limpiarCampos();
            } else {
                mensaje("Tipo de producto no reconocido");
            }

        } catch (NumberFormatException e) {
            mensaje("Error: Revisa que los campos numéricos sean válidos");
        } catch (PersistenciaException e) {
            mensaje("Error al guardar en el repositorio");
        }
    }


    @FXML
    public void initialize(){
        cmbTipo.getItems().addAll("ProductoPerecedero", "ProductosNoPerecedero");
        cmbTipo.setValue("ProductoPerecedero");
        BooleanBinding esPerecedero = cmbTipo.valueProperty().isEqualTo("ProductoPerecedero");

        txtDias.visibleProperty().bind(esPerecedero);
        lblDias.visibleProperty().bind(esPerecedero);
        txtGarantia.visibleProperty().bind(esPerecedero.not());
        lblGarantia.visibleProperty().bind(esPerecedero.not());

        colTipo.setCellValueFactory(c -> new ReadOnlyObjectWrapper<>(c.getValue().getTipo()));
        colNombre.setCellValueFactory(c -> new ReadOnlyObjectWrapper<>(c.getValue().getNombre()));
        colCategoria.setCellValueFactory(c -> new ReadOnlyObjectWrapper<>(c.getValue().getCategoria()));
        colPrecioBase.setCellValueFactory(c -> new ReadOnlyObjectWrapper<>(c.getValue().getPrecioBase()));
        colStock.setCellValueFactory(c -> new ReadOnlyObjectWrapper<>(c.getValue().getStock()));
        colDatosEspecifico.setCellValueFactory(c -> new ReadOnlyObjectWrapper<>(datosEspecifico(c.getValue())));
        colPrecioFinal.setCellValueFactory(c -> new ReadOnlyObjectWrapper<>(c.getValue().calcularPrecioFinal()));
        tblProducto.setItems(productos);

        try {
            productoRepo.cargar();
            productos.setAll(productoRepo.listar());
            mensaje("Datos cargados correctamente");
        } catch (PersistenciaException e) {
            mensaje("Error al cargar");
        }
    }


    private void mensaje(String mensaje){
        if (lblMensaje != null){
            lblMensaje.setText(mensaje);
        }
    }

    private void  limpiarCampos(){
        txtNombre.clear();
        txtCategoria.clear();
        txtPrecio.clear();
        txtStock.clear();
        txtDias.clear();
        txtGarantia.clear();
    }

}
