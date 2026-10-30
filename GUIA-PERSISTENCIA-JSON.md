# Persistencia en JSON con Gson

Guía para los ejercicios de `E:\ejercicios-javafx`. La idea es la misma que usa el
proyecto del vivero: **la tabla y el archivo siempre muestran lo mismo**.

> Esta guía no resuelve ningún ejercicio. La configuración (Gson en el `pom.xml` y
> `module-info.java`) ya está aplicada en los 8 proyectos. El código del repositorio
> y del controlador lo escribes tú.

---

## 1. El patrón en cuatro pasos

```
Al iniciar:      archivo JSON  ->  repositorio  ->  ObservableList  ->  TableView
Al registrar:    formulario    ->  objeto       ->  ObservableList  ->  archivo JSON
Al eliminar:     fila          ->  ObservableList  ->  archivo JSON
Si no hay JSON:  lista vacía, sin mensaje de error
```

La clave es que **el archivo se reescribe completo** después de cada registro o
eliminación. No se agrega una línea al final: se guarda la lista entera. Así no hay
duplicados ni diferencias entre lo que ves y lo que está guardado.

---

## 2. Configuración (ya aplicada)

### 2.1 Dependencia en `pom.xml`

Cada proyecto ya tiene:

```xml
<dependency>
    <groupId>com.google.code.gson</groupId>
    <artifactId>gson</artifactId>
    <version>2.13.2</version>
</dependency>
```

Si Maven no la reconoce, en IntelliJ haz clic en **Load Maven Changes** (el icono de
la `m` con flechas) o ejecuta `mvn clean compile`.

### 2.2 `module-info.java`

Cada proyecto ya tiene:

```java
requires com.google.gson;
```

y, en la línea del modelo:

```java
opens stevens.ej08.model to javafx.fxml, javafx.controls, com.google.gson;
```

¿Por qué las dos cosas?

- `requires`: el proyecto necesita **leer** la librería Gson.
- `opens ... to com.google.gson`: Gson crea los objetos usando **reflexión**, es
  decir, lee y escribe los campos privados. Sin el `opens`, Java se lo impide y
  aparece un error como `module ... does not "opens ..." to module com.google.gson`.

Si tu paquete `model` todavía no existe, el compilador solo muestra un **warning**
(`package is empty or does not exist`). Desaparece cuando creas tu primera entidad.

---

## 3. ¿Dónde se guardan los archivos?

En la **raíz del proyecto**, al mismo nivel que `pom.xml`. Se logra con una ruta
relativa:

```java
Path archivo = Path.of("contactos.json").toAbsolutePath();
```

`Path.of("contactos.json")` es relativo al **directorio de trabajo** (`user.dir`).

- Con `mvn javafx:run`, ese directorio es la raíz del proyecto.
- Con IntelliJ, revisa en **Run > Edit Configurations** que *Working directory* sea
  la carpeta del proyecto.

**Nunca** escribas `C:\Users\...\contactos.json`: si mueves el proyecto o lo
entregas, deja de funcionar.

### ¿Un archivo o varios?

| Situación | Archivos |
|---|---|
| Una sola entidad (contactos, gastos, canciones) | `contactos.json` |
| Dos entidades relacionadas (dueño y mascota) | `duenios.json` y `mascotas.json`, o solo `mascotas.json` si el dueño va dentro |
| Herencia o subtipos (como el vivero) | un archivo por tipo: `interiores.json` y `exteriores.json` |

Nombres sugeridos:

| Ejercicio | Archivo sugerido |
|---|---|
| ej01-veterinaria | `mascotas.json` (y `duenios.json` si lo necesitas) |
| ej02-biblioteca | `libros.json` |
| ej03-turnos | `turnos.json` |
| ej04-inventario | `productos.json` |
| ej05-torneo | `torneos.json` o `jugadores.json` |
| ej06-gastos | `gastos.json` |
| ej07-playlist | `canciones.json` |
| ej08-agenda | `contactos.json` |

---

## 4. La clase de rutas

Centraliza los nombres de los archivos en una sola clase. En ej01 y ej02 ya existe
`Paths`; puedes reutilizarla.

```java
package stevens.ej08.utils;

import java.nio.file.Path;

public final class Rutas {
    public static final Path CONTACTOS = Path.of("contactos.json").toAbsolutePath();

    private Rutas() {
    }
}
```

Así, si cambias el nombre del archivo, lo cambias en un solo lugar.

---

## 5. El repositorio

### 5.1 La interfaz

```java
package stevens.ej08.repository;

import java.util.List;

public interface RepositorioJson<T> {
    List<T> cargar();
    void guardar(List<T> elementos);
}
```

- `cargar()` devuelve **siempre** una lista, nunca `null`.
- `guardar()` reemplaza el contenido del archivo con la lista completa.

### 5.2 La implementación con Gson

Este es el esqueleto. Completa los `TODO` en tu proyecto, no lo copies sin entenderlo:

```java
package stevens.ej08.repository;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonParseException;

import java.io.IOException;
import java.io.Reader;
import java.io.Writer;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class RepositorioJsonGson<T> implements RepositorioJson<T> {

    private final Path archivo;
    private final Class<T[]> tipoArreglo;
    private final Gson gson;

    public RepositorioJsonGson(Path archivo, Class<T[]> tipoArreglo) {
        this.archivo = archivo;
        this.tipoArreglo = tipoArreglo;
        this.gson = new GsonBuilder().setPrettyPrinting().create();
    }

    @Override
    public List<T> cargar() {
        // TODO 1: si el archivo no existe, devolver new ArrayList<>().
        // TODO 2: abrir el archivo con Files.newBufferedReader dentro de un try-with-resources.
        // TODO 3: T[] datos = gson.fromJson(lector, tipoArreglo);
        // TODO 4: si datos es null (archivo vacío), devolver lista vacía.
        // TODO 5: devolver new ArrayList<>(Arrays.asList(datos)).
        // TODO 6: capturar IOException y JsonParseException y lanzar
        //         new RuntimeException("No se pudo leer ...", e).
        return new ArrayList<>();
    }

    @Override
    public void guardar(List<T> elementos) {
        // TODO 1: crear la carpeta si no existe con Files.createDirectories(...).
        // TODO 2: abrir el archivo con Files.newBufferedWriter dentro de try-with-resources.
        // TODO 3: gson.toJson(elementos, tipoArreglo, escritor);
        // TODO 4: capturar IOException y lanzar RuntimeException con un mensaje claro.
    }
}
```

Puntos que conviene entender:

- `Class<T[]>` permite saber qué arreglo debe construir Gson. Se pasa, por ejemplo,
  `Contacto[].class`. Sin eso, Gson no sabe qué tipo de objeto hay dentro del JSON.
- `setPrettyPrinting()` escribe el JSON con saltos e indentación para que lo puedas
  leer con el Bloc de notas. No cambia los datos.
- `Files.exists(archivo)` evita el error de la primera ejecución.
- Si el archivo está vacío, `fromJson` devuelve `null`; se convierte en lista vacía.
- `try-with-resources` cierra el archivo incluso si ocurre una excepción.
- Se lanza `RuntimeException` para que el controlador la capture y muestre el error
  en la ventana, sin cerrar la aplicación.

### 5.3 Cómo usarlo

```java
private final RepositorioJson<Contacto> repositorio =
        new RepositorioJsonGson<>(Rutas.CONTACTOS, Contacto[].class);
```

---

## 6. Enganchar el controlador

### 6.1 Al iniciar

```java
@FXML
public void initialize() {
    tblContactos.setItems(contactos);   // contactos es la ObservableList

    try {
        contactos.addAll(repositorio.cargar());
    } catch (RuntimeException e) {
        lblMensaje.setText("Error al leer los datos: " + e.getMessage());
    }
}
```

JavaFX llama a `initialize()` automáticamente después de cargar el FXML. Ahí también
configuras las columnas y los listeners.

### 6.2 Al registrar

El orden importa:

```java
Contacto nuevo = new Contacto(...);

List<Contacto> lista = new ArrayList<>(contactos);
lista.add(nuevo);

repositorio.guardar(lista);   // primero el archivo
contactos.add(nuevo);         // después la tabla
```

Si el guardado falla, la tabla no cambia y sigue coincidiendo con el archivo.

### 6.3 Al eliminar

```java
Contacto seleccionado = tblContactos.getSelectionModel().getSelectedItem();
if (seleccionado == null) {
    lblMensaje.setText("Selecciona un contacto para eliminarlo.");
    return;
}

List<Contacto> lista = new ArrayList<>(contactos);
lista.remove(seleccionado);

repositorio.guardar(lista);
contactos.remove(seleccionado);
```

### 6.4 Los mensajes

Usa un `Label` dentro de la ventana. Cada mensaje reemplaza al anterior con
`lblMensaje.setText(...)`. No uses `System.out.println` para los resultados: el
enunciado del vivero lo prohíbe y en la prueba se revisa.

---

## 7. Checklist antes de dar por terminado un ejercicio

- [ ] Si borro los JSON y abro la app, la tabla está vacía y **no** aparece un error.
- [ ] Registro una fila: aparece en la tabla y el JSON se crea o se actualiza.
- [ ] Cierro y vuelvo a abrir: la fila sigue ahí.
- [ ] Elimino una fila: desaparece de la tabla y del JSON.
- [ ] Cierro y vuelvo a abrir: la fila eliminada no vuelve.
- [ ] Si no selecciono nada y presiono Eliminar, aparece un mensaje y no se guarda nada.
- [ ] Si escribo un dato inválido, aparece el mensaje y no se guarda nada.
- [ ] El JSON está en la raíz del proyecto, junto al `pom.xml`.
- [ ] La app no imprime errores en la consola.

---

## 8. Errores comunes

| Error | Causa | Solución |
|---|---|---|
| `module ... does not "opens ..." to module com.google.gson` | Falta el `opens` del paquete `model` | Agrega `com.google.gson` a la línea `opens ...model to ...` |
| `package is empty or does not exist` | El paquete `model` aún no tiene clases | Es un warning; desaparece al crear la entidad |
| El JSON aparece en otra carpeta | El *Working directory* no es la raíz | Revísalo en la configuración de ejecución |
| `fromJson` devuelve `null` | El archivo está vacío | Trátalo como lista vacía |
| Gson no reconstruye el objeto | La entidad no tiene constructor sin parámetros, o los campos son `final` | Agrega el constructor vacío y evita `final` en los campos |
| La tabla y el archivo no coinciden | Guardaste antes de terminar de modificar la lista, o al revés | Guarda la lista completa y luego actualiza la tabla |
| Error al guardar una fecha | Gson no maneja `LocalDate` directamente | Guárdala como `String` con `fecha.toString()` |
| La app se cierra al leer un JSON dañado | No se capturó la excepción | Captura `JsonParseException` y muestra el mensaje en el `Label` |

---

## 9. Plan de práctica hasta el 4 de noviembre

1. **ej08-agenda**: una entidad simple. Hazlo completo sin mirar la guía.
2. **ej06-gastos**: agrega fechas y montos. Practica el formato.
3. **ej02-biblioteca**: si tu modelo tiene `enum`, revisa cómo lo guarda Gson.
4. **ej01-veterinaria**: dos entidades. Decide si usas uno o dos archivos.
5. **Repite uno desde cero en 45 minutos**, como simulacro.

No empieces por el más difícil. La persistencia se aprende repitiendo el mismo
patrón: cargar al iniciar, guardar al cambiar, archivo completo.

---

## 10. Tips para la prueba

- Antes de programar, escribe en papel: entidad, archivo, columnas de la tabla y
  acciones (registrar, eliminar).
- Haz que el proyecto **compile primero** con la configuración y una ventana vacía.
  Después agrega la persistencia. Así separas los errores.
- Prueba el repositorio con una clase `main` pequeña antes de conectarlo a la
  interfaz: guardas dos objetos, los vuelves a cargar y revisas el JSON.
- Usa nombres claros: `RepositorioJson`, `cargar`, `guardar`, `Rutas`. Se evalúa la
  organización en clases separadas.
- Si algo falla, lee la **primera** línea del error de Java: normalmente dice el
  archivo y la línea exacta.
- Guarda una copia de tu proyecto que compile antes de la entrega.
- No uses rutas absolutas ni `System.out.println` para los resultados.
