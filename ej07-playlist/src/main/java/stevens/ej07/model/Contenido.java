package stevens.ej07.model;

/**
 * Contenido de una playlist. Clase abstracta: puede ser canción o podcast.
 *
 * Reglas comunes: título, autor y género no vacíos; duración entre 1 y 36000 segundos.
 */
public abstract class Contenido {

    public static final int DURACION_MINIMA = 1;
    public static final int DURACION_MAXIMA = 36_000;
    public static final int SEGUNDOS_CONTENIDO_LARGO = 600;

    private String titulo;
    private String autor;
    private String genero;
    private int duracionSegundos;

    protected Contenido() {
    }

    protected Contenido(String titulo, String autor, String genero, int duracionSegundos) {
        setTitulo(titulo);
        setAutor(autor);
        setGenero(genero);
        setDuracionSegundos(duracionSegundos);
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = validarTexto(titulo, "El título");
    }

    public String getAutor() {
        return autor;
    }

    public void setAutor(String autor) {
        this.autor = validarTexto(autor, "El autor");
    }

    public String getGenero() {
        return genero;
    }

    public void setGenero(String genero) {
        this.genero = validarTexto(genero, "El género");
    }

    public int getDuracionSegundos() {
        return duracionSegundos;
    }

    public void setDuracionSegundos(int duracionSegundos) {
        if (duracionSegundos < DURACION_MINIMA || duracionSegundos > DURACION_MAXIMA) {
            throw new IllegalArgumentException("La duración debe estar entre " + DURACION_MINIMA
                    + " y " + DURACION_MAXIMA + " segundos.");
        }
        this.duracionSegundos = duracionSegundos;
    }

    /** Peso estimado del archivo, en megabytes, según la calidad de cada subtipo. */
    public abstract double calcularPesoMB();

    /** "Canción" o "Podcast". */
    public abstract String getTipo();

    public boolean esContenidoLargo() {
        return duracionSegundos > SEGUNDOS_CONTENIDO_LARGO;
    }

    /** Duración en formato mm:ss. */
    public String getDuracionFormateada() {
        return String.format("%02d:%02d", duracionSegundos / 60, duracionSegundos % 60);
    }

    protected static String validarTexto(String valor, String campo) {
        if (valor == null || valor.isBlank()) {
            throw new IllegalArgumentException(campo + " no puede estar vacío.");
        }
        return valor.trim();
    }

    @Override
    public String toString() {
        return getTipo() + " | " + titulo + " | " + autor + " | " + getDuracionFormateada();
    }
}
