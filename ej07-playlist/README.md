# Ejercicio 7 — Playlist de música

Proyecto Maven + JavaFX (FXML). **Sin persistencia:** los datos solo viven en memoria mientras la aplicación está abierta.

## Cómo abrirlo en IntelliJ

1. `File → Open` → selecciona la carpeta `ej07-playlist`.
2. Espera a que Maven sincronice.
3. Ejecuta la clase `stevens.ej07.application.Launcher` (o `mvn clean javafx:run`).

## Estructura base

- `pom.xml` → dependencias y configuración Maven
- `src/main/resources/Main.fxml` → vista principal (empieza aquí)
- `stevens.ej07.controllers.MainController` → controlador de la vista principal
- `stevens.ej07.model` → crea aquí tus entidades
- `stevens.ej07.application` → punto de entrada (`Launcher` / `app`)

## Estructura de datos: el gestor (en memoria)

Crea la clase `stevens.ej07.gestores.GestorPlaylists`. Es quien guarda los datos mientras la app está abierta (no hay persistencia): el controlador solo maneja la interfaz y le delega el CRUD.

- `ObservableList<Playlist>` para la lista de playlists y `ObservableList<Cancion>` global de todas las canciones.
- `HashMap<String, Playlist>` con clave = **nombre** para validar playlists duplicadas/vacías y para buscar el destino al mover una canción.
- No hace falta tocar `module-info.java`: solo FXML necesita `opens`.

```java
package stevens.ej07.gestores;

public class GestorPlaylists {
    // TODO: ObservableList<Playlist> playlists
    // TODO: ObservableList<Cancion> canciones        → todas, sin importar la playlist
    // TODO: HashMap<String, Playlist> playlistsPorNombre

    // TODO: boolean guardarPlaylist(String nombre)   → false si está vacía o ya existe
    // TODO: boolean guardarCancion(Cancion c)        → false si título + artista ya existen
    // TODO: void eliminarCancion(Cancion c)
    // TODO: List<Cancion> cancionesDe(Playlist p)
    // TODO: void moverCancion(Cancion c, Playlist destino)  → tras llamarlo, ambas vistas se refrescan
}
```

## Enunciado

### Entidades

- **Playlist**: nombre.
- **Canción**: título, artista, género, duración en segundos, playlist (relación).

### Requisitos

1. Lista de playlists (`ListView` o `TableView`) a la izquierda y `TableView` con las canciones de la playlist seleccionada a la derecha.
2. CRUD de canciones; la playlist asignada es la que está seleccionada en la lista.
3. Mostrar la duración en formato `mm:ss` en la tabla (ejemplo: 245 s → `04:05`).
4. Doble clic en una canción → ventana secundaria con todos sus detalles.
5. Botón "Mover a otra playlist" → ventana con `ComboBox` de playlists destino; al aceptar, la canción se mueve y **ambas vistas se actualizan**.
6. Validaciones con `Alert`: nombre de playlist vacío y canción repetida (mismo título + artista).

### Conceptos JavaFX a practicar

Dos vistas sincronizadas, `ListView`, selección con `SelectionModel`, paso de datos entre ventanas, formateo personalizado de duración, `FilteredList` opcional.
