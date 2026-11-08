package stevens.ej04.model;

/**
 * Producto no perecedero. Si la garantía es de 12 meses o más, el precio sube un 10%.
 */
public class ProductoNoPerecedero extends Producto {

    public static final int MESES_GARANTIA_EXTENDIDA = 12;
    public static final int RECARGO_GARANTIA = 10;

    private int mesesGarantia;

    public ProductoNoPerecedero() {
    }

    public ProductoNoPerecedero(String nombre, String categoria, int precioBase, int stock, int mesesGarantia) {
        super(nombre, categoria, precioBase, stock);
        setMesesGarantia(mesesGarantia);
    }

    public int getMesesGarantia() {
        return mesesGarantia;
    }

    public void setMesesGarantia(int mesesGarantia) {
        this.mesesGarantia = validarEnteroNoNegativo(mesesGarantia, "Los meses de garantía");
    }

    @Override
    public int calcularPrecioFinal() {
        int base = getPrecioBase();
        if (mesesGarantia >= MESES_GARANTIA_EXTENDIDA) {
            return base + base * RECARGO_GARANTIA / 100;
        }
        return base;
    }

    @Override
    public String getTipo() {
        return "No perecedero";
    }
}
