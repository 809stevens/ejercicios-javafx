# Ejercicio 8 — Agenda de contactos

Proyecto Maven + JavaFX (FXML). **Sin persistencia:** los datos solo viven en memoria mientras la aplicación está abierta (no se recarga nada al iniciar).

## Cómo abrirlo en IntelliJ

1. `File → Open` → selecciona la carpeta `ej08-agenda`.
2. Espera a que Maven sincronice.
3. Ejecuta la clase `stevens.ej08.application.Launcher` (o `mvn clean javafx:run`).

## Estructura base

- `pom.xml` → dependencias y configuración Maven
- `src/main/resources/stevens/ej08/Main.fxml` → vista principal (empieza aquí)
- `stevens.ej08.controllers.MainController` → controlador de la vista principal
- `stevens.ej08.model` → crea aquí tus entidades
- `stevens.ej08.application` → punto de entrada (`Launcher` / `app`)

## Estructura de datos: el gestor (en memoria)

Crea la clase `stevens.ej08.gestores.GestorAgenda`. Es quien guarda los datos mientras la app está abierta (no hay persistencia): el controlador solo maneja la interfaz y le delega el CRUD.

- `ObservableList<Contacto>` como fuente de la `TableView` (la búsqueda instantánea se aplica con `FilteredList` encima).
- `HashMap<String, Contacto>` con clave = **email** (o teléfono): valida duplicados y localiza rápido el contacto que la ventana de edición va a modificar.
- No hace falta tocar `module-info.java`: solo FXML necesita `opens`.

```java
package stevens.ej08.gestores;

public class GestorAgenda {
    // TODO: ObservableList<Contacto> contactos
    // TODO: HashMap<String, Contacto> contactosPorEmail

    // TODO: boolean guardarContacto(Contacto c)            → false si el email ya existe
    // TODO: void actualizarContacto(Contacto original, Contacto editado)
    // TODO: void eliminarContacto(Contacto c)
}
```

> El extra de exportar CSV vive aquí: `void exportarCsv(List<Contacto> visibles)` usa `PrintWriter`/`FileWriter`.

## Enunciado

### Entidad

- **Contacto**: nombre, teléfono, email.

### Requisitos

1. CRUD principal en la ventana principal con `TableView` (columnas: nombre, teléfono, email).
2. Ventana de **edición** secundaria: se abre con el botón "Editar" tras seleccionar una fila, recibe el contacto seleccionado (`controller.setContacto(...)`) y **devuelve el contacto modificado** a la ventana principal al guardar.
3. Búsqueda instantánea por nombre o teléfono con `TextField` (`FilteredList`).
4. Validar el formato del email con expresión regular → `Alert` de error si no coincide.
5. Ordenamiento por columna al hacer clic en el encabezado de la `TableView`.
6. `Label` con el conteo de contactos mostrados / total.

### Extra opcional

- Botón "Exportar CSV" que escribe la tabla actualmente visible en un archivo `contactos.csv` (no es persistencia: la app no lo vuelve a leer al iniciar).

### Conceptos JavaFX a practicar

Ventana de edición con retorno de datos (patrón enviar/recibir), `FilteredList`, expresiones regulares, ordenamiento de columnas, `FileWriter`/`PrintWriter`.
