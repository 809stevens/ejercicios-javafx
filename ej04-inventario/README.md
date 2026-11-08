# Ejercicio 04 — Inventario de supermercado

Proyecto base con el **modelo ya implementado** al estilo de la Evaluación Parcial 2.
Tu trabajo es la capa de persistencia (DAO/Repository), el controlador y la vista.

## Cómo abrirlo en IntelliJ

1. `File → Open` → selecciona la carpeta `ej04-inventario`.
2. Espera a que Maven sincronice.
3. Ejecuta la clase `stevens.ej04.application.Launcher` (o `mvn clean javafx:run`).

## Qué ya está hecho (no lo reescribas)

- `stevens.ej04.model.Producto`: clase **abstracta** con las reglas y validaciones comunes.
- `stevens.ej04.model.ProductoPerecedero` y `stevens.ej04.model.ProductoNoPerecedero`: los dos subtipos.
- Una interfaz de negocio del modelo (como `Despachable` en el vivero).
- `stevens.ej04.dao.ProductoDao`: interfaz DAO de lectura y escritura.
- `stevens.ej04.repository.JsonRepository`: interfaz del repositorio.
- `stevens.ej04.dao.PersistenciaException`, `stevens.ej04.utils.Paths`.
- `stevens.ej04.application.App` y `stevens.ej04.application.Launcher`.
- `pom.xml` con JavaFX y Gson, y `module-info.java` configurado.

## Qué debes programar tú

1. **`dao/GsonProductoDao`** (esqueleto con `TODO`)
   - `cargar()`: si el archivo no existe, devolver una lista vacía.
   - `guardar()`: crear la carpeta si falta y sobrescribir el archivo completo.
   - Envolver `IOException` y `JsonParseException` en `PersistenciaException`.
   - Reemplaza el `UnsupportedOperationException` por la implementación.

2. **`repository/ProductoRepository`** (esqueleto con `TODO`)
   - `cargar()`: cargar los dos archivos y juntar los resultados.
   - `listar()`: devolver una copia de la lista.
   - `agregar()` y `eliminar()`: guardar primero en los DAO sobre una copia y solo después actualizar la lista en memoria.
   - `guardarPorTipo()`: separar con `instanceof` y llamar al DAO correspondiente.

3. **`controllers/MainController`** (esqueleto con `TODO`)
   - Configurar las columnas, enlazar la `ObservableList` y cargar los datos.
   - Validar el formulario y crear el subtipo correcto según el `ComboBox`.
   - Registrar, eliminar y mostrar los mensajes en `lblMensaje`.
   - Usar los valores calculados del modelo; no los recalcules en el controlador.

4. **`resources/stevens/ej04/Main-view.fxml`**
   - Construir la vista con Scene Builder usando los `fx:id` que ya declara el controlador.
   - Debe incluir el formulario, la tabla, los botones y el área de mensajes.

## Reglas de negocio (ya implementadas en el modelo)

- **Producto perecedero:** precio final = precio base. Si vence en **3 días o menos**, se aplica **30% de descuento**; si vence en **7 días o menos**, **15%**. Más de 7 días, sin descuento.
- **Producto no perecedero:** precio final = precio base. Si la garantía es de **12 meses o más**, se aplica un **10% de recargo**.
- **Comunes:** nombre y categoría no vacíos, precio base mayor que cero, stock no negativo.
- El modelo ya calcula `calcularPrecioFinal()`, `valorInventario()`, `estaBajoStock()` y `getTipo()`.
- `Descontable` lo implementa solo `ProductoPerecedero`.

## Columnas de la tabla

Tipo, Nombre, Categoría, Precio final, Stock, Valor inventario y Datos específicos.

## Datos de prueba

| Tipo | Nombre | Categoría | Precio base | Stock | Dato específico | Precio final |
|---|---|---|---|---|---|---|
| Perecedero | Leche | Lácteos | 1200 | 10 | Vence en 2 días | $840 |
| Perecedero | Pan | Panadería | 2000 | 4 | Vence en 5 días | $1.700 |
| No perecedero | Arroz | Abarrotes | 1500 | 20 | Garantía 6 meses | $1.500 |
| No perecedero | Refrigerador | Electrónica | 450000 | 2 | Garantía 24 meses | $495.000 |

El valor total del inventario debe ser **$1.035.200**.

- Si el stock es menor a 5, muestra la fila o la celda en rojo (`setCellFactory`).
- Muestra un `Label` con el valor total del inventario y recalcúlalo después de registrar o eliminar.

## Persistencia

- Los archivos se guardan en la **raíz del proyecto**, junto a `pom.xml`: `Perecederos.json` y `No_perecederos.json`.
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
