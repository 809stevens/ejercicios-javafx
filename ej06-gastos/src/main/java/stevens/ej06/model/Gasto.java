package stevens.ej06.model;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

/**
 * Gasto personal. Clase abstracta: puede ser fijo o variable.
 *
 * Reglas comunes: concepto y categoría no vacíos, monto base mayor que cero
 * y fecha con formato dd-MM-yyyy.
 */
public abstract class Gasto {

    public static final DateTimeFormatter FORMATO_FECHA = DateTimeFormatter.ofPattern("dd-MM-yyyy");

    private String concepto;
    private String categoria;
    private int montoBase;
    private String fecha;

    protected Gasto() {
    }

    protected Gasto(String concepto, String categoria, int montoBase, String fecha) {
        setConcepto(concepto);
        setCategoria(categoria);
        setMontoBase(montoBase);
        setFecha(fecha);
    }

    public String getConcepto() {
        return concepto;
    }

    public void setConcepto(String concepto) {
        this.concepto = validarTexto(concepto, "El concepto");
    }

    public String getCategoria() {
        return categoria;
    }

    public void setCategoria(String categoria) {
        this.categoria = validarTexto(categoria, "La categoría");
    }

    public int getMontoBase() {
        return montoBase;
    }

    public void setMontoBase(int montoBase) {
        if (montoBase <= 0) {
            throw new IllegalArgumentException("El monto base debe ser mayor que cero.");
        }
        this.montoBase = montoBase;
    }

    public String getFecha() {
        return fecha;
    }

    public void setFecha(String fecha) {
        if (fecha == null || fecha.isBlank()) {
            throw new IllegalArgumentException("La fecha no puede estar vacía.");
        }
        try {
            LocalDate.parse(fecha.trim(), FORMATO_FECHA);
        } catch (DateTimeParseException e) {
            throw new IllegalArgumentException("La fecha debe tener el formato dd-MM-yyyy.");
        }
        this.fecha = fecha.trim();
    }

    /** Monto final después de aplicar la regla del subtipo. */
    public abstract int calcularMontoFinal();

    /** "Fijo" o "Variable". */
    public abstract String getTipo();

    public boolean esMontoAlto() {
        return calcularMontoFinal() >= 100_000;
    }

    protected static String validarTexto(String valor, String campo) {
        if (valor == null || valor.isBlank()) {
            throw new IllegalArgumentException(campo + " no puede estar vacío.");
        }
        return valor.trim();
    }

    /** Formato de pesos chilenos: $1.200. */
    public static String pesos(int monto) {
        return String.format("$%,d", monto).replace(',', '.');
    }

    @Override
    public String toString() {
        return getTipo() + " | " + concepto + " | " + categoria + " | " + fecha + " | "
                + pesos(calcularMontoFinal());
    }
}
