package stevens.ej04.model;

/**
 * Producto perecedero. Si vence en 3 días o menos se aplica 30% de descuento;
 * si vence en 7 días o menos, 15%. En otro caso no hay descuento.
 */
public class ProductoPerecedero extends Producto implements Descontable {

    public static final int DIAS_DESCUENTO_CRITICO = 3;
    public static final int DIAS_DESCUENTO_PROXIMO = 7;
    public static final int DESCUENTO_CRITICO = 30;
    public static final int DESCUENTO_PROXIMO = 15;

    private int diasParaVencer;

    public ProductoPerecedero() {
    }

    public ProductoPerecedero(String nombre, String categoria, int precioBase, int stock, int diasParaVencer) {
        super(nombre, categoria, precioBase, stock);
        setDiasParaVencer(diasParaVencer);
    }

    public int getDiasParaVencer() {
        return diasParaVencer;
    }

    public void setDiasParaVencer(int diasParaVencer) {
        this.diasParaVencer = validarEnteroNoNegativo(diasParaVencer, "Los días para vencer");
    }

    @Override
    public int calcularDescuento() {
        if (diasParaVencer <= DIAS_DESCUENTO_CRITICO) {
            return DESCUENTO_CRITICO;
        }
        if (diasParaVencer <= DIAS_DESCUENTO_PROXIMO) {
            return DESCUENTO_PROXIMO;
        }
        return 0;
    }

    @Override
    public boolean aplicaDescuento() {
        return calcularDescuento() > 0;
    }

    @Override
    public int calcularPrecioFinal() {
        int descuento = calcularDescuento();
        return getPrecioBase() - getPrecioBase() * descuento / 100;
    }

    @Override
    public String getTipo() {
        return "Perecedero";
    }
}
