# Ejercicio 6 — Gastos personales

Proyecto Maven + JavaFX (FXML). **Sin persistencia:** los datos solo viven en memoria mientras la aplicación está abierta (no se guarda nada en archivos).

## Cómo abrirlo en IntelliJ

1. `File → Open` → selecciona la carpeta `ej06-gastos`.
2. Espera a que Maven sincronice.
3. Ejecuta la clase `stevens.ej06.application.Launcher` (o `mvn clean javafx:run`).

## Estructura base

- `pom.xml` → dependencias y configuración Maven
- `src/main/resources/Main.fxml` → vista principal (empieza aquí)
- `stevens.ej06.controllers.MainController` → controlador de la vista principal
- `stevens.ej06.model` → crea aquí tus entidades
- `stevens.ej06.application` → punto de entrada (`Launcher` / `app`)

## Estructura de datos: el gestor (en memoria)

Crea la clase `stevens.ej06.gestores.GestorGastos`. Es quien guarda los datos mientras la app está abierta (no hay persistencia): el controlador solo maneja la interfaz y le delega el CRUD.

- `ObservableList<Gasto>` como fuente de la `TableView` (el filtro mes/año se aplica con `FilteredList` encima).
- `HashMap<Integer, Gasto>` con clave = **id** autogenerado para localizar el gasto a actualizar/eliminar.
- `ObservableList<String>` (o `HashSet<String>`) para las categorías.
- No hace falta tocar `module-info.java`: solo FXML necesita `opens`.

```java
package stevens.ej06.gestores;

public class GestorGastos {
    // TODO: ObservableList<Gasto> gastos
    // TODO: ObservableList<String> categorias
    // TODO: HashMap<Integer, Gasto> gastosPorId   → con id autogenerado

    // TODO: void guardarGasto(Gasto g)
    // TODO: void actualizarGasto(Gasto g)
    // TODO: void eliminarGasto(Gasto g)
    // TODO: void agregarCategoria(String c)
}
```

> El `Label` con la suma total se calcula en el controlador recorriendo la lista **ya filtrada** (o el gestor expone `double totalDe(List<Gasto> gastos)`).

## Enunciado

### Entidad

- **Gasto**: concepto, monto, fecha (`LocalDate`), categoría.

### Requisitos

1. CRUD completo de gastos con `TableView` (columnas: concepto, monto, fecha, categoría) y formulario con `DatePicker`.
2. Columna fecha con formato `dd-MM-yyyy` usando `setCellFactory` + `DateTimeFormatter`.
3. Filtro por **mes y año** con dos `ComboBox` que recalcula tanto la tabla como el total.
4. `Label` con la suma de los montos de los gastos actualmente filtrados (se actualiza tras cada operación y cada cambio de filtro).
5. Ventana **modal** para dar de alta categorías; las categorías se eligen con `ComboBox` en el formulario.

### Conceptos JavaFX a practicar

`DateTimeFormatter` en celdas (`setCellFactory`), doble filtro con `FilteredList`, suma dinámica sobre `ObservableList`, `DatePicker`, ventanas modales.
