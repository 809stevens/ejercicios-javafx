# Ejercicio 3 — Turnos médicos

Proyecto Maven + JavaFX (FXML). **Sin persistencia:** los datos solo viven en memoria mientras la aplicación está abierta.

## Cómo abrirlo en IntelliJ

1. `File → Open` → selecciona la carpeta `ej03-turnos`.
2. Espera a que Maven sincronice.
3. Ejecuta la clase `stevens.ej03.application.Launcher` (o `mvn clean javafx:run`).

## Estructura base

- `pom.xml` → dependencias y configuración Maven
- `src/main/resources/Main.fxml` → vista principal (empieza aquí)
- `stevens.ej03.controllers.MainController` → controlador de la vista principal
- `stevens.ej03.model` → crea aquí tus entidades
- `stevens.ej03.application` → punto de entrada (`Launcher` / `app`)

## Estructura de datos: el gestor (en memoria)

Crea la clase `stevens.ej03.gestores.GestorTurnos`. Es quien guarda los datos mientras la app está abierta (no hay persistencia): el controlador solo maneja la interfaz y le delega el CRUD.

- `ObservableList` para doctores, pacientes y turnos (fuente de las tablas y `ComboBox`).
- `HashMap<String, Turno>` con clave compuesta `"fecha|hora|doctor"`: permite responder "¿ese horario está ocupado?" en un solo paso antes de guardar.
- No hace falta tocar `module-info.java`: solo FXML necesita `opens`.

```java
package stevens.ej03.gestores;

public class GestorTurnos {
    // TODO: ObservableList<Doctor> doctores
    // TODO: ObservableList<Paciente> pacientes
    // TODO: ObservableList<Turno> turnos
    // TODO: HashMap<String, Turno> turnosOcupados   → clave "fecha|hora|doctor"

    // TODO: void guardarDoctor(Doctor d)
    // TODO: boolean guardarTurno(Turno t)     → false si la clave ya está ocupada
    // TODO: void actualizarTurno(Turno t)
    // TODO: void eliminarTurno(Turno t)
}
```

## Enunciado

### Entidades

- **Doctor**: nombre, especialidad.
- **Paciente**: nombre, teléfono.
- **Turno**: fecha, hora, doctor, paciente.

### Requisitos

1. Ventana principal con `TableView` que lista los turnos.
2. Formulario de turno con `DatePicker` + `ComboBox` de doctores + `ComboBox` de pacientes + campo de hora.
3. Ventana **modal** para dar de alta doctores (botón "Nuevo doctor"); al guardar se cierra y se actualiza el `ComboBox`.
4. Celdas de la tabla coloreadas con `setCellFactory`: verde = horario libre, rojo = horario ocupado (mismo doctor con la misma hora ya registrada).
5. No permitir crear un turno si el mismo doctor ya tiene esa hora y fecha → `Alert` de error.
6. CRUD de turnos (crear, actualizar, eliminar con confirmación); los doctores solo se dan de alta y se listan.

### Conceptos JavaFX a practicar

`DatePicker`, `ComboBox`, `setCellFactory` con estilo condicional, detección de duplicados, ventanas modales, `Alert`.
