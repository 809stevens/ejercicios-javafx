# Ejercicio 05 — Torneo de videojuegos

Proyecto base con el **modelo ya implementado** al estilo de la Evaluación Parcial 2.
Tu trabajo es la capa de persistencia (DAO/Repository), el controlador y la vista.

## Cómo abrirlo en IntelliJ

1. `File → Open` → selecciona la carpeta `ej05-torneo`.
2. Espera a que Maven sincronice.
3. Ejecuta la clase `stevens.ej05.application.Launcher` (o `mvn clean javafx:run`).

## Qué ya está hecho (no lo reescribas)

- `stevens.ej05.model.Participante`: clase **abstracta** con las reglas y validaciones comunes.
- `stevens.ej05.model.Jugador` y `stevens.ej05.model.Entrenador`: los dos subtipos.
- Una interfaz de negocio del modelo (como `Despachable` en el vivero).
- `stevens.ej05.dao.ParticipanteDao`: interfaz DAO de lectura y escritura.
- `stevens.ej05.repository.JsonRepository`: interfaz del repositorio.
- `stevens.ej05.dao.PersistenciaException`, `stevens.ej05.utils.Paths`.
- `stevens.ej05.application.App` y `stevens.ej05.application.Launcher`.
- `pom.xml` con JavaFX y Gson, y `module-info.java` configurado.

## Qué debes programar tú

1. **`dao/GsonParticipanteDao`** (esqueleto con `TODO`)
   - `cargar()`: si el archivo no existe, devolver una lista vacía.
   - `guardar()`: crear la carpeta si falta y sobrescribir el archivo completo.
   - Envolver `IOException` y `JsonParseException` en `PersistenciaException`.
   - Reemplaza el `UnsupportedOperationException` por la implementación.

2. **`repository/ParticipanteRepository`** (esqueleto con `TODO`)
   - `cargar()`: cargar los dos archivos y juntar los resultados.
   - `listar()`: devolver una copia de la lista.
   - `agregar()` y `eliminar()`: guardar primero en los DAO sobre una copia y solo después actualizar la lista en memoria.
   - `guardarPorTipo()`: separar con `instanceof` y llamar al DAO correspondiente.

3. **`controllers/MainController`** (esqueleto con `TODO`)
   - Configurar las columnas, enlazar la `ObservableList` y cargar los datos.
   - Validar el formulario y crear el subtipo correcto según el `ComboBox`.
   - Registrar, eliminar y mostrar los mensajes en `lblMensaje`.
   - Usar los valores calculados del modelo; no los recalcules en el controlador.

4. **`resources/stevens/ej05/Main-view.fxml`**
   - Construir la vista con Scene Builder usando los `fx:id` que ya declara el controlador.
   - Debe incluir el formulario, la tabla, los botones y el área de mensajes.

## Reglas de negocio (ya implementadas en el modelo)

- **Jugador:** puntaje = `partidasGanadas * 12`, más **30** si el rol es `DPS` o **20** para los demás roles.
- **Entrenador:** puntaje = `40 + aniosExperiencia * 8`.
- **Comunes:** nombre y equipo no vacíos; edad entre **16 y 80** años.
- El modelo ya calcula `calcularPuntaje()`, `getTipo()` y `esDestacado()` (puntaje mayor o igual a 100).
- `Competitivo` lo implementa solo `Jugador`; `clasifica()` indica si alcanza 100 puntos.

## Columnas de la tabla

Tipo, Nombre, Equipo, Edad, Puntaje y Datos específicos.

## Datos de prueba

| Tipo | Nombre | Equipo | Edad | Dato específico | Puntaje |
|---|---|---|---|---|---|
| Jugador | Ana | Lobos | 22 | Nick: AnaPro, rol DPS, 8 partidas | 126 |
| Jugador | Beto | Lobos | 25 | Nick: BetoTank, rol Tanque, 5 partidas | 80 |
| Entrenador | Carla | Lobos | 35 | Estrategia, 10 años | 120 |
| Entrenador | Diego | Tigres | 42 | Preparación física, 5 años | 80 |

- Muestra un `Label` con el puntaje total de los participantes.
- En la columna de datos específicos indica si el participante es destacado (`esDestacado()`).

## Persistencia

- Los archivos se guardan en la **raíz del proyecto**, junto a `pom.xml`: `jugadores.json` y `entrenadores.json`.
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
