# Ejercicio 2 — Biblioteca

Proyecto Maven + JavaFX (FXML). **Sin persistencia:** los datos solo viven en memoria mientras la aplicación está abierta.

## Cómo abrirlo en IntelliJ

1. `File → Open` → selecciona la carpeta `ej02-biblioteca`.
2. Espera a que Maven sincronice.
3. Ejecuta la clase `stevens.ej02.application.Launcher` (o `mvn clean javafx:run`).

## Estructura base

- `pom.xml` → dependencias y configuración Maven
- `src/main/resources/stevens/ej02/Main.fxml` → vista principal (empieza aquí)
- `stevens.ej02.controllers.MainController` → controlador de la vista principal
- `stevens.ej02.model` → crea aquí tus entidades
- `stevens.ej02.application` → punto de entrada (`Launcher` / `App`)

## Estructura de datos: el gestor (en memoria)

Crea la clase `stevens.ej02.gestores.GestorBiblioteca`. Es quien guarda los datos mientras la app está abierta (no hay persistencia): el controlador solo maneja la interfaz y le delega el CRUD.

- `ObservableList<Libro>` como fuente de la `TableView`.
- `HashMap<String, Libro>` con clave = **ISBN**: sirve a la vez para validar duplicados y para traer el libro en el detalle de doble clic sin recorrer la lista.
- No hace falta tocar `module-info.java`: solo FXML necesita `opens`.

```java
package stevens.ej02.gestores;

public class GestorBiblioteca {
    // TODO: ObservableList<Libro> libros
    // TODO: HashMap<String, Libro> librosPorIsbn

    // TODO: boolean guardarLibro(Libro l)   → false si el ISBN ya existe
    // TODO: void actualizarLibro(Libro l)
    // TODO: void eliminarLibro(Libro l)
    // TODO: Libro buscarPorIsbn(String isbn)
    // TODO: boolean prestar(Libro l)        → false si ya está prestado (el controlador muestra el Alert)
}
```

## Enunciado

### Entidad

- **Libro**: título, autor, ISBN, año, prestado (`boolean`), fecha de devolución.

### Requisitos

1. CRUD completo de libros con `TableView` (columnas: título, autor, ISBN, año, prestado, fecha de devolución).
2. Columna "Prestado" con `CheckBox` editable (`CheckBoxTableCell` con `setEditable(true)`).
3. Regla de negocio: si el libro ya está prestado, el botón "Prestar" muestra un `Alert` de error.
4. Doble clic en una fila → ventana secundaria con el detalle del libro en modo **solo lectura**: solo `Label` con los datos y un botón "Cerrar" (no hay campos editables ni botón de guardar).
5. Búsqueda por título o autor con `TextField` (`FilteredList`).
6. Validar ISBN duplicado al guardar → `Alert`.

### Conceptos JavaFX a practicar

`setCellFactory`, `CheckBoxTableCell`, eventos de doble clic, ventanas modales y paso de datos entre ventanas (`controller.setLibro(...)`), `FilteredList`, `Alert`.
