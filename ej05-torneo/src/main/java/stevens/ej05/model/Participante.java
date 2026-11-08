package stevens.ej05.model;

/**
 * Participante de un torneo de videojuegos. Clase abstracta: puede ser jugador o entrenador.
 *
 * Reglas comunes: nombre y equipo no vacíos; edad entre 16 y 80 años.
 */
public abstract class Participante {

    public static final int EDAD_MINIMA = 16;
    public static final int EDAD_MAXIMA = 80;
    public static final int PUNTAJE_DESTACADO = 100;

    private String nombre;
    private String equipo;
    private int edad;

    protected Participante() {
    }

    protected Participante(String nombre, String equipo, int edad) {
        setNombre(nombre);
        setEquipo(equipo);
        setEdad(edad);
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = validarTexto(nombre, "El nombre");
    }

    public String getEquipo() {
        return equipo;
    }

    public void setEquipo(String equipo) {
        this.equipo = validarTexto(equipo, "El equipo");
    }

    public int getEdad() {
        return edad;
    }

    public void setEdad(int edad) {
        if (edad < EDAD_MINIMA || edad > EDAD_MAXIMA) {
            throw new IllegalArgumentException("La edad debe estar entre " + EDAD_MINIMA
                    + " y " + EDAD_MAXIMA + " años.");
        }
        this.edad = edad;
    }

    /** Puntaje que aporta el participante al torneo. */
    public abstract int calcularPuntaje();

    /** "Jugador" o "Entrenador". */
    public abstract String getTipo();

    public boolean esDestacado() {
        return calcularPuntaje() >= PUNTAJE_DESTACADO;
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

    @Override
    public String toString() {
        return getTipo() + " | " + nombre + " | " + equipo + " | " + edad + " años | "
                + calcularPuntaje() + " pts";
    }
}
