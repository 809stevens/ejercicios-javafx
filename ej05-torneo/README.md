# Ejercicio 5 — Torneo de videojuegos

Proyecto Maven + JavaFX (FXML). **Sin persistencia:** los datos solo viven en memoria mientras la aplicación está abierta.

## Cómo abrirlo en IntelliJ

1. `File → Open` → selecciona la carpeta `ej05-torneo`.
2. Espera a que Maven sincronice.
3. Ejecuta la clase `stevens.ej05.application.Launcher` (o `mvn clean javafx:run`).

## Estructura base

- `pom.xml` → dependencias y configuración Maven
- `src/main/resources/Main.fxml` → vista principal (empieza aquí)
- `stevens.ej05.controllers.MainController` → controlador de la vista principal
- `stevens.ej05.model` → crea aquí tus entidades
- `stevens.ej05.application` → punto de entrada (`Launcher` / `app`)

## Estructura de datos: el gestor (en memoria)

Crea la clase `stevens.ej05.gestores.GestorTorneo`. Es quien guarda los datos mientras la app está abierta (no hay persistencia): el controlador solo maneja la interfaz y le delega el CRUD.

- `ObservableList<Equipo>` y `ObservableList<Jugador>`: la primera alimenta la tabla de arriba y la segunda la de abajo (puedes refiltrarla con `setAll(...)` al cambiar la selección).
- `HashMap<String, Equipo>` con clave = **nombre** para validar duplicados.
- No hace falta tocar `module-info.java`: solo FXML necesita `opens`.

```java
package stevens.ej05.gestores;

public class GestorTorneo {
    // TODO: ObservableList<Equipo> equipos
    // TODO: ObservableList<Jugador> jugadores
    // TODO: HashMap<String, Equipo> equiposPorNombre

    // TODO: boolean guardarEquipo(Equipo e)    → false si el nombre ya existe
    // TODO: void actualizarEquipo(Equipo e)
    // TODO: void eliminarEquipo(Equipo e)      → aquí decides qué pasa con sus jugadores
    // TODO: boolean guardarJugador(Jugador j)  → false si el nickname ya existe
    // TODO: void actualizarJugador(Jugador j)
    // TODO: void eliminarJugador(Jugador j)
    // TODO: List<Jugador> jugadoresDe(Equipo e)
}
```

## Enunciado

### Entidades

- **Equipo**: nombre, juego, región.
- **Jugador**: nickname, rol, edad, equipo (relación con la entidad anterior).

### Requisitos (patrón maestro–detalle con dos tablas)

1. Ventana principal con **dos `TableView` en paralelo**: la de arriba lista los equipos y la de abajo los jugadores.
2. Al seleccionar un equipo en la tabla superior, la inferior muestra **solo los jugadores de ese equipo** (listener sobre el `SelectionModel`).
3. CRUD de equipos en una ventana **modal** (botón "Nuevo equipo" / "Editar equipo").
4. CRUD de jugadores en el formulario de abajo; el equipo asignado es el que está seleccionado en la tabla superior.
5. Validar nickname duplicado → `Alert` de error.
6. Eliminar un equipo con confirmación (`Alert`): decide y documenta en este README qué pasa con sus jugadores (¿se eliminan con él? ¿se quedan sin equipo?).

### Conceptos JavaFX a practicar

Patrón maestro–detalle, dos `TableView` sincronizadas con listeners, relaciones entre entidades, ventanas modales, `Alert`.
