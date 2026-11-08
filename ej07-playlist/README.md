# Ejercicio 07 — Playlist de música

Proyecto base con el **modelo ya implementado** al estilo de la Evaluación Parcial 2.
Tu trabajo es la capa de persistencia (DAO/Repository), el controlador y la vista.

## Cómo abrirlo en IntelliJ

1. `File → Open` → selecciona la carpeta `ej07-playlist`.
2. Espera a que Maven sincronice.
3. Ejecuta la clase `stevens.ej07.application.Launcher` (o `mvn clean javafx:run`).

## Qué ya está hecho (no lo reescribas)

- `stevens.ej07.model.Contenido`: clase **abstracta** con las reglas y validaciones comunes.
- `stevens.ej07.model.Cancion` y `stevens.ej07.model.Podcast`: los dos subtipos.
- Una interfaz de negocio del modelo (como `Despachable` en el vivero).
- `stevens.ej07.dao.ContenidoDao`: interfaz DAO de lectura y escritura.
- `stevens.ej07.repository.JsonRepository`: interfaz del repositorio.
- `stevens.ej07.dao.PersistenciaException`, `stevens.ej07.utils.Paths`.
- `stevens.ej07.application.App` y `stevens.ej07.application.Launcher`.
- `pom.xml` con JavaFX y Gson, y `module-info.java` configurado.

## Qué debes programar tú

1. **`dao/GsonContenidoDao`** (esqueleto con `TODO`)
   - `cargar()`: si el archivo no existe, devolver una lista vacía.
   - `guardar()`: crear la carpeta si falta y sobrescribir el archivo completo.
   - Envolver `IOException` y `JsonParseException` en `PersistenciaException`.
   - Reemplaza el `UnsupportedOperationException` por la implementación.

2. **`repository/ContenidoRepository`** (esqueleto con `TODO`)
   - `cargar()`: cargar los dos archivos y juntar los resultados.
   - `listar()`: devolver una copia de la lista.
   - `agregar()` y `eliminar()`: guardar primero en los DAO sobre una copia y solo después actualizar la lista en memoria.
   - `guardarPorTipo()`: separar con `instanceof` y llamar al DAO correspondiente.

3. **`controllers/MainController`** (esqueleto con `TODO`)
   - Configurar las columnas, enlazar la `ObservableList` y cargar los datos.
   - Validar el formulario y crear el subtipo correcto según el `ComboBox`.
   - Registrar, eliminar y mostrar los mensajes en `lblMensaje`.
   - Usar los valores calculados del modelo; no los recalcules en el controlador.

4. **`resources/stevens/ej07/Main-view.fxml`**
   - Construir la vista con Scene Builder usando los `fx:id` que ya declara el controlador.
   - Debe incluir el formulario, la tabla, los botones y el área de mensajes.

## Reglas de negocio (ya implementadas en el modelo)

- **Canción:** peso estimado = `duracionSegundos * 320 / 8 / 1024` MB (calidad 320 kbps).
- **Podcast:** peso estimado = `duracionSegundos * 128 / 8 / 1024` MB (calidad 128 kbps).
- **Comunes:** título, autor y género no vacíos; duración entre **1 y 36000** segundos.
- El modelo ya entrega `getDuracionFormateada()` en formato `mm:ss`, `calcularPesoMB()`, `getTipo()` y `esContenidoLargo()`.
- `Descargable` lo implementa solo `Podcast`; `descargar()` cambia el estado a descargado.

## Columnas de la tabla

Tipo, Título, Autor, Género, Duración, Peso (MB) y Datos específicos.

## Datos de prueba

| Tipo | Título | Autor | Género | Duración (s) | Dato específico | Peso (MB) |
|---|---|---|---|---|---|---|
| Canción | Bohemian Rhapsody | Queen | Rock | 354 | Álbum: A Night at the Opera, no explícita | 13,8 |
| Canción | Numb | Linkin Park | Rock | 185 | Álbum: Meteora, no explícita | 7,2 |
| Podcast | Episodio 1 | Radio Duoc | Tecnología | 1800 | Episodio 1, Spotify, descargado | 28,1 |
| Podcast | Episodio 2 | Radio Duoc | Tecnología | 2400 | Episodio 2, YouTube, no descargado | 37,5 |

- Muestra un `Label` con la duración total de la playlist en formato `mm:ss`.
- En la columna de datos específicos muestra el álbum o el episodio/plataforma y si está descargado.

## Persistencia

- Los archivos se guardan en la **raíz del proyecto**, junto a `pom.xml`: `canciones.json` y `podcasts.json`.
- Si no existen, la aplicación inicia con la tabla vacía y sin errores.
- Cada registro y cada eliminación reescriben los archivos completos.

## Checklist

- [ ] El proyecto compila y abre la ventana.
- [ ] Al iniciar sin JSON, la tabla está vacía y no hay error.
- [ ] Registro un elemento de cada subtipo y aparece de inmediato en la tabla.
- [ ] Cierro y vuelvo a abrir: los datos siguen ahí.
- [ ] Elimino un elemento: desaparece de la tabla y del JSON.
- [ ] Si no selecciono nada y presiono Eliminar, aparece un mensaje.
- [ ] Si escribo un dato inválido, aparece el mensaje y no se guarda nada.
- [ ] El controlador no lee ni escribe archivos directamente.
