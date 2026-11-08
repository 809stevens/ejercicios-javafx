package stevens.ej04.model;

/**
 * Contrato de los productos que pueden llevar descuento por proximidad de vencimiento.
 */
public interface Descontable {

    /** Porcentaje de descuento que corresponde (0 si no hay descuento). */
    int calcularDescuento();

    /** Indica si el producto tiene algún descuento aplicable. */
    boolean aplicaDescuento();
}
