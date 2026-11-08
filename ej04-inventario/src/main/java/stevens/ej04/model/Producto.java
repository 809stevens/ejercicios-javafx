package stevens.ej04.model;

/**
 * Producto del inventario. Clase abstracta: todo producto es perecedero o no perecedero.
 *
 * Reglas comunes: nombre y categoría no vacíos, precio base mayor que cero
 * y stock no negativo.
 */
public abstract class Producto {

    public static final int STOCK_BAJO = 5;

    private String nombre;
    private String categoria;
    private int precioBase;
    private int stock;

    protected Producto() {
    }

    protected Producto(String nombre, String categoria, int precioBase, int stock) {
        setNombre(nombre);
        setCategoria(categoria);
        setPrecioBase(precioBase);
        setStock(stock);
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = validarTexto(nombre, "El nombre");
    }

    public String getCategoria() {
        return categoria;
    }

    public void setCategoria(String categoria) {
        this.categoria = validarTexto(categoria, "La categoría");
    }

    public int getPrecioBase() {
        return precioBase;
    }

    public void setPrecioBase(int precioBase) {
        if (precioBase <= 0) {
            throw new IllegalArgumentException("El precio base debe ser mayor que cero.");
        }
        this.precioBase = precioBase;
    }

    public int getStock() {
        return stock;
    }

    public void setStock(int stock) {
        if (stock < 0) {
            throw new IllegalArgumentException("El stock no puede ser negativo.");
        }
        this.stock = stock;
    }

    /** Precio final después de aplicar la regla del subtipo. */
    public abstract int calcularPrecioFinal();

    /** "Perecedero" o "No perecedero". */
    public abstract String getTipo();

    /** Valor total de este producto en el inventario. */
    public int valorInventario() {
        return calcularPrecioFinal() * stock;
    }

    public boolean estaBajoStock() {
        return stock < STOCK_BAJO;
    }

    protected static String validarTexto(String valor, String campo) {
        if (valor == null || valor.isBlank()) {
            throw new IllegalArgumentException(campo + " no puede estar vacío.");
        }
        return valor.trim();
    }

    protected static int validarEnteroNoNegativo(int valor, String campo) {
        if (valor < 0) {
            throw new IllegalArgumentException(campo + " no puede ser negativo.");
        }
        return valor;
    }

    /** Formato de pesos chilenos: $1.200. */
    public static String pesos(int monto) {
        return String.format("$%,d", monto).replace(',', '.');
    }

    @Override
    public String toString() {
        return getTipo() + " | " + nombre + " | " + categoria + " | "
                + pesos(calcularPrecioFinal()) + " | stock " + stock;
    }
}
