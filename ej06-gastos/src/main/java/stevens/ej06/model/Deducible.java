package stevens.ej06.model;

/**
 * Contrato de los gastos que pueden rebajar impuestos.
 */
public interface Deducible {

    /** Monto que se puede deducir (0 si no aplica). */
    int calcularMontoDeducible();

    /** Indica si el gasto cumple las condiciones para ser deducible. */
    boolean esDeducible();
}
