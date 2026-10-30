# Ejercicio 4 — Inventario de supermercado

Proyecto Maven + JavaFX (FXML). **Sin persistencia:** los datos solo viven en memoria mientras la aplicación está abierta.

## Cómo abrirlo en IntelliJ

1. `File → Open` → selecciona la carpeta `ej04-inventario`.
2. Espera a que Maven sincronice.
3. Ejecuta la clase `stevens.ej04.application.Launcher` (o `mvn clean javafx:run`).

## Estructura base

- `pom.xml` → dependencias y configuración Maven
- `src/main/resources/Main.fxml` → vista principal (empieza aquí)
- `stevens.ej04.controllers.MainController` → controlador de la vista principal
- `stevens.ej04.model` → crea aquí tus entidades
- `stevens.ej04.application` → punto de entrada (`Launcher` / `app`)

## Estructura de datos: el gestor (en memoria)

Crea la clase `stevens.ej04.gestores.GestorInventario`. Es quien guarda los datos mientras la app está abierta (no hay persistencia): el controlador solo maneja la interfaz y le delega el CRUD.

- `ObservableList<Producto>` como fuente de la `TableView`.
- `HashMap<String, Producto>` con clave = **nombre** para validar duplicados.
- `ObservableList<String>` (o `HashSet<String>`) para las categorías dadas de alta en la ventana modal.
- No hace falta tocar `module-info.java`: solo FXML necesita `opens`.

```java
package stevens.ej04.gestores;

public class GestorInventario {
    // TODO: ObservableList<Producto> productos
    // TODO: ObservableList<String> categorias
    // TODO: HashMap<String, Producto> productosPorNombre

    // TODO: boolean guardarProducto(Producto p)   → false si el nombre ya existe
    // TODO: void actualizarProducto(Producto p)
    // TODO: void eliminarProducto(Producto p)
    // TODO: void agregarCategoria(String c)
    // TODO: double valorTotal()                   → Σ precio × stock
}
```

## Enunciado

### Entidad

- **Producto**: nombre, categoría, precio, stock.

### Requisitos

1. CRUD completo de productos con `TableView` (columnas: nombre, categoría, precio, stock).
2. Si el `stock` es menor a 5, el texto de la celda se muestra en rojo (`setCellFactory` + estilo condicional).
3. `Label` con el valor total del inventario (Σ precio × stock) que se recalcula después de cada operación: crear, actualizar y eliminar.
4. Ventana **modal** para dar de alta categorías (botón "Nueva categoría"); las categorías se eligen en un `ComboBox` dentro del formulario del producto.
5. Filtro por categoría con `ComboBox` que actualiza la tabla.
6. Validaciones con `Alert`: precio y stock no pueden ser negativos, campos vacíos.

### Conceptos JavaFX a practicar

`setCellFactory` con estilo condicional, recálculo de totales sobre `ObservableList`, `ComboBox`, ventanas modales, validaciones.
