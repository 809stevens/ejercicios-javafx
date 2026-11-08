# Ejercicio 06 — Gastos personales

Proyecto base con el **modelo ya implementado** al estilo de la Evaluación Parcial 2.
Tu trabajo es la capa de persistencia (DAO/Repository), el controlador y la vista.

## Cómo abrirlo en IntelliJ

1. `File → Open` → selecciona la carpeta `ej06-gastos`.
2. Espera a que Maven sincronice.
3. Ejecuta la clase `stevens.ej06.application.Launcher` (o `mvn clean javafx:run`).

## Qué ya está hecho (no lo reescribas)

- `stevens.ej06.model.Gasto`: clase **abstracta** con las reglas y validaciones comunes.
- `stevens.ej06.model.GastoFijo` y `stevens.ej06.model.GastoVariable`: los dos subtipos.
- Una interfaz de negocio del modelo (como `Despachable` en el vivero).


## Qué debes programar tú

1. **`dao/GsonGastoDao`** (esqueleto con `TODO`)
   - `cargar()`: si el archivo no existe, devolver una lista vacía.
   - `guardar()`: crear la carpeta si falta y sobrescribir el archivo completo.
   - Envolver `IOException` y `JsonParseException` en `PersistenciaException`.
   - Reemplaza el `UnsupportedOperationException` por la implementación.

2. **`repository/GastoRepository`** (esqueleto con `TODO`)
   - `cargar()`: cargar los dos archivos y juntar los resultados.
   - `listar()`: devolver una copia de la lista.
   - `agregar()` y `eliminar()`: guardar primero en los DAO sobre una copia y solo después actualizar la lista en memoria.
   - `guardarPorTipo()`: separar con `instanceof` y llamar al DAO correspondiente.

3. **`controllers/MainController`** (esqueleto con `TODO`)
   - Configurar las columnas, enlazar la `ObservableList` y cargar los datos.
   - Validar el formulario y crear el subtipo correcto según el `ComboBox`.
   - Registrar, eliminar y mostrar los mensajes en `lblMensaje`.
   - Usar los valores calculados del modelo; no los recalcules en el controlador.

4. **`resources/stevens/ej06/Main-view.fxml`**
   - Construir la vista con Scene Builder usando los `fx:id` que ya declara el controlador.
   - Debe incluir el formulario, la tabla, los botones y el área de mensajes.

## Reglas de negocio (ya implementadas en el modelo)

- **Gasto fijo:** monto final = monto base. Si el contrato es de **12 meses o más**, se aplica **10% de descuento**.
- **Gasto variable:** cada cuota adicional a la primera agrega **3% de recargo**. Ejemplo: 6 cuotas = 15% de recargo.
- **Comunes:** concepto y categoría no vacíos, monto base mayor que cero y fecha con formato **dd-MM-yyyy**.
- `Deducible` lo implementa solo `GastoFijo`: si el contrato es de **6 meses o más**, se puede deducir el **20%** del monto final.

## Columnas de la tabla

Tipo, Concepto, Categoría, Fecha, Monto final y Datos específicos.

## Datos de prueba

| Tipo | Concepto | Categoría | Fecha | Monto base | Dato específico | Monto final |
|---|---|---|---|---|---|---|
| Fijo | Arriendo | Vivienda | 01-11-2026 | 450000 | Mensual, 12 meses | $405.000 |
| Fijo | Internet | Servicios | 05-11-2026 | 30000 | Mensual, 6 meses | $30.000 |
| Variable | Supermercado | Alimentación | 03-11-2026 | 80000 | Débito, 1 cuota | $80.000 |
| Variable | Notebook | Tecnología | 10-11-2026 | 600000 | Crédito, 6 cuotas | $690.000 |

El total de los gastos debe ser **$1.205.000**.

- Muestra un `Label` con la suma de los montos finales.
- En la columna de datos específicos muestra la periodicidad/contrato o el medio de pago/cuotas, y si el gasto es deducible.

## Persistencia

- Los archivos se guardan en la **raíz del proyecto**, junto a `pom.xml`: `gastos_fijos.json` y `gastos_variables.json`.
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
