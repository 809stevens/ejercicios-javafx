package stevens.ej06.model;

/**
 * Gasto variable. Cada cuota adicional a la primera agrega un 3% de recargo.
 */
public class GastoVariable extends Gasto {

    public static final int RECARGO_POR_CUOTA = 3;
    public static final int CUOTAS_MINIMAS = 1;
    public static final int CUOTAS_MAXIMAS = 48;

    private String medioPago;
    private int cuotas;

    public GastoVariable() {
    }

    public GastoVariable(String concepto, String categoria, int montoBase, String fecha,
                         String medioPago, int cuotas) {
        super(concepto, categoria, montoBase, fecha);
        setMedioPago(medioPago);
        setCuotas(cuotas);
    }

    public String getMedioPago() {
        return medioPago;
    }

    public void setMedioPago(String medioPago) {
        this.medioPago = validarTexto(medioPago, "El medio de pago");
    }

    public int getCuotas() {
        return cuotas;
    }

    public void setCuotas(int cuotas) {
        if (cuotas < CUOTAS_MINIMAS || cuotas > CUOTAS_MAXIMAS) {
            throw new IllegalArgumentException("Las cuotas deben estar entre " + CUOTAS_MINIMAS
                    + " y " + CUOTAS_MAXIMAS + ".");
        }
        this.cuotas = cuotas;
    }

    @Override
    public int calcularMontoFinal() {
        int base = getMontoBase();
        int recargo = base * RECARGO_POR_CUOTA * (cuotas - 1) / 100;
        return base + recargo;
    }

    @Override
    public String getTipo() {
        return "Variable";
    }
}
