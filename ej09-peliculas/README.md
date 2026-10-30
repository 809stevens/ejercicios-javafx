# Ejercicio 9 - Catalogo de peliculas (practica DAO + Repository)

Ejercicio de **una sola entidad** (`Pelicula`) para practicar el flujo completo
`Controlador -> Repository -> DAO -> JSON`.

## Que ya esta hecho

- `pom.xml` con JavaFX, Gson y el plugin `javafx-maven-plugin`.
- `module-info.java` con los `requires`, `exports` y `opens` necesarios.
- `App` y `Launcher` funcionando.
- `model/Pelicula` (entidad) completa.
- `utils/Paths` con la ruta `peliculas.json` y la vista.
- Vista `Main.fxml` con formulario, tabla y botones.
- `PersistenciaException`, `PeliculaDao` y `Repository` (interfaces).
- Esqueletos de `GsonPeliculaDao`, `PeliculaRepository` y `MainController`
  con comentarios `TODO`.

## Que tienes que programar tu

En este orden:

1. `dao/GsonPeliculaDao`
   - `cargar()`: si el archivo no existe, devolver lista vacia.
   - `guardar()`: crear carpetas si faltan y sobrescribir el archivo.
   - Envolver `IOException` / `JsonParseException` en `PersistenciaException`.
   - Necesitas `Type tipoLista = TypeToken.getParameterized(List.class, Pelicula.class).getType();`

2. `repository/PeliculaRepository`
   - `cargar()`: limpiar y copiar lo que devuelve el DAO.
   - `listar()`: devolver `new ArrayList<>(peliculas)`.
   - `agregar()` / `eliminar()`: guardar primero en el DAO sobre una copia y
     solo despues actualizar la lista en memoria.

3. `controllers/MainController`
   - `initialize()`: enlazar columnas, `tblPeliculas.setItems(peliculas)` y
     cargar desde el repositorio.
   - `btnGuardar`: validar, crear `Pelicula`, `repo.agregar(...)`, refrescar.
   - `btnEliminar`: comprobar seleccion, `repo.eliminar(...)`, refrescar.
   - `btnLimpiar`: vaciar campos.

## Como probar

```bash
cd E:\ejercicios-javafx\ej09-peliculas
mvn clean javafx:run
```

El archivo `peliculas.json` se crea solo en la carpeta del proyecto cuando
guardes la primera pelicula. Si cierras y vuelves a abrir, la tabla debe
mostrar las mismas peliculas.

## Regla de oro del Repository

```java
List<Pelicula> copia = new ArrayList<>(peliculas);
copia.add(nueva);
dao.guardar(copia);   // si falla, no se toca la lista en memoria
peliculas.add(nueva);
```

Asi lo que muestra la tabla y lo que esta en el archivo siempre coinciden.
