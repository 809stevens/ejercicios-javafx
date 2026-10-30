# Ejercicio 1 — Veterinaria

Proyecto Maven + JavaFX (FXML). **Sin persistencia:** los datos solo viven en memoria mientras la aplicación está abierta.

## Cómo abrirlo en IntelliJ

1. `File → Open` → selecciona la carpeta `ej01-veterinaria`.
2. Espera a que Maven sincronice.
3. Ejecuta la clase `stevens.ej01.application.Launcher` (o `mvn clean javafx:run`).

## Estructura base

- `pom.xml` → dependencias y configuración Maven
- `src/main/resources/stevens/ej01/Main.fxml` → vista principal (empieza aquí)
- `stevens.ej01.controllers.MainController` → controlador de la vista principal
- `stevens.ej01.model` → crea aquí tus entidades
- `stevens.ej01.application` → punto de entrada (`Launcher` / `App`)

## Estructura de datos: el gestor (en memoria)

Crea la clase `stevens.ej01.gestores.GestorVeterinaria`. Es quien guarda los datos mientras la app está abierta (no hay persistencia): el controlador solo maneja la interfaz y le delega el CRUD.

- `ObservableList` como fuente de la `TableView` (la tabla se actualiza sola).
- `HashMap<String, Dueño>` con clave única (por ejemplo el teléfono) para validar duplicados sin recorrer la lista.
- No hace falta tocar `module-info.java`: solo FXML necesita `opens`.

```java
package stevens.ej01.gestores;

public class GestorVeterinaria {
    // TODO: ObservableList<Mascota> mascotas
    // TODO: ObservableList<Dueño> dueños
    // TODO: HashMap<String, Dueño> dueñosPorTelefono

    // TODO: boolean guardarDueño(Dueño d)        → false si el teléfono ya existe
    // TODO: void guardarMascota(Mascota m)
    // TODO: void actualizarMascota(Mascota m)
    // TODO: void eliminarMascota(Mascota m)
    // TODO: ObservableList<Mascota> mascotas()
}
```

## Enunciado

### Entidades

- **Dueño**: nombre, teléfono, email.
- **Mascota**: nombre, especie, raza, fecha de nacimiento, dueño (relación con la entidad anterior).

### Requisitos

1. CRUD completo de mascotas en la ventana principal con `TableView` (columnas: nombre, especie, raza, edad calculada, dueño).
2. Alta de dueños desde una ventana secundaria **modal** (botón "Nuevo dueño"); al guardar, se cierra la ventana y se actualiza el `ComboBox` de dueños.
3. `ComboBox` para vincular cada mascota con su dueño en el formulario.
4. Filtro por especie con `TextField` usando `FilteredList`.
5. Seleccionar una fila de la tabla carga los datos en el formulario (base para actualizar y eliminar).
6. Validaciones con `Alert`: campos vacíos y fecha de nacimiento futura.
7. Eliminar con confirmación (`Alert` de tipo `CONFIRMATION`).

### Conceptos JavaFX a practicar

`TableView` + `ObservableList`, `ComboBox`, `FilteredList`, ventanas modales (`Stage.initModality`), comunicación entre ventanas, `Alert`, `LocalDate`.
