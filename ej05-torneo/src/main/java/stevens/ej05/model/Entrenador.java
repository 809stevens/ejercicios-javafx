package stevens.ej05.model;

/**
 * Entrenador de un equipo. Puntaje = 40 puntos base + 8 puntos por año de experiencia.
 */
public class Entrenador extends Participante {

    public static final int PUNTAJE_BASE = 40;
    public static final int PUNTOS_POR_ANIO = 8;
    public static final int EXPERIENCIA_MAXIMA = 60;

    private String especialidad;
    private int aniosExperiencia;

    public Entrenador() {
    }

    public Entrenador(String nombre, String equipo, int edad, String especialidad, int aniosExperiencia) {
        super(nombre, equipo, edad);
        setEspecialidad(especialidad);
        setAniosExperiencia(aniosExperiencia);
    }

    public String getEspecialidad() {
        return especialidad;
    }

    public void setEspecialidad(String especialidad) {
        this.especialidad = validarTexto(especialidad, "La especialidad");
    }

    public int getAniosExperiencia() {
        return aniosExperiencia;
    }

    public void setAniosExperiencia(int aniosExperiencia) {
        if (aniosExperiencia < 0 || aniosExperiencia > EXPERIENCIA_MAXIMA) {
            throw new IllegalArgumentException("La experiencia debe estar entre 0 y "
                    + EXPERIENCIA_MAXIMA + " años.");
        }
        this.aniosExperiencia = aniosExperiencia;
    }

    @Override
    public int calcularPuntaje() {
        return PUNTAJE_BASE + aniosExperiencia * PUNTOS_POR_ANIO;
    }

    @Override
    public String getTipo() {
        return "Entrenador";
    }
}
