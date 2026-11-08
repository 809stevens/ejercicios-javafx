package stevens.ej06.model;

/**
 * Gasto fijo. Si el contrato es de 12 meses o más se aplica un 10% de descuento.
 * Es deducible cuando el contrato es de 6 meses o más.
 */
public class GastoFijo extends Gasto implements Deducible {

    public static final int MESES_DESCUENTO = 12;
    public static final int DESCUENTO_CONTRATO = 10;
    public static final int MESES_PARA_DEDUCIR = 6;
    public static final int PORCENTAJE_DEDUCIBLE = 20;

    private String periodicidad;
    private int mesesContrato;

    public GastoFijo() {
    }

    public GastoFijo(String concepto, String categoria, int montoBase, String fecha,
                     String periodicidad, int mesesContrato) {
        super(concepto, categoria, montoBase, fecha);
        setPeriodicidad(periodicidad);
        setMesesContrato(mesesContrato);
    }

    public String getPeriodicidad() {
        return periodicidad;
    }

    public void setPeriodicidad(String periodicidad) {
        if (periodicidad == null || periodicidad.isBlank()) {
            throw new IllegalArgumentException("La periodicidad no puede estar vacía.");
        }
        String valor = periodicidad.trim();
        if (!valor.equalsIgnoreCase("Mensual") && !valor.equalsIgnoreCase("Anual")) {
            throw new IllegalArgumentException("La periodicidad debe ser Mensual o Anual.");
        }
        this.periodicidad = valor.substring(0, 1).toUpperCase() + valor.substring(1).toLowerCase();
    }

    public int getMesesContrato() {
        return mesesContrato;
    }

    public void setMesesContrato(int mesesContrato) {
        if (mesesContrato < 0) {
            throw new IllegalArgumentException("Los meses de contrato no pueden ser negativos.");
        }
        this.mesesContrato = mesesContrato;
    }

    @Override
    public int calcularMontoFinal() {
        int base = getMontoBase();
        if (mesesContrato >= MESES_DESCUENTO) {
            return base - base * DESCUENTO_CONTRATO / 100;
        }
        return base;
    }

    @Override
    public int calcularMontoDeducible() {
        if (!esDeducible()) {
            return 0;
        }
        return calcularMontoFinal() * PORCENTAJE_DEDUCIBLE / 100;
    }

    @Override
    public boolean esDeducible() {
        return mesesContrato >= MESES_PARA_DEDUCIR;
    }

    @Override
    public String getTipo() {
        return "Fijo";
    }
}
