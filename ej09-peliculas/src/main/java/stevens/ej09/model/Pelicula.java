package stevens.ej09.model;

/**
 * Entidad del ejercicio: una pelicula del catalogo.
 *
 * Esta clase ya esta COMPLETA. No necesitas modificarla para el ejercicio,
 * salvo que quieras agregar mas campos.
 *
 * Gson necesita un constructor vacio y getters/setters para reconstruir el
 * objeto desde el archivo JSON.
 */
public class Pelicula {

    private String titulo;
    private String director;
    private String genero;
    private int anio;
    private int duracionMin;
    private boolean vista;

    /** Constructor vacio requerido por Gson. */
    public Pelicula() {
    }

    public Pelicula(String titulo, String director, String genero, int anio, int duracionMin, boolean vista) {
        this.titulo = titulo;
        this.director = director;
        this.genero = genero;
        this.anio = anio;
        this.duracionMin = duracionMin;
        this.vista = vista;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getDirector() {
        return director;
    }

    public void setDirector(String director) {
        this.director = director;
    }

    public String getGenero() {
        return genero;
    }

    public void setGenero(String genero) {
        this.genero = genero;
    }

    public int getAnio() {
        return anio;
    }

    public void setAnio(int anio) {
        this.anio = anio;
    }

    public int getDuracionMin() {
        return duracionMin;
    }

    public void setDuracionMin(int duracionMin) {
        this.duracionMin = duracionMin;
    }

    public boolean isVista() {
        return vista;
    }

    public void setVista(boolean vista) {
        this.vista = vista;
    }

    @Override
    public String toString() {
        return titulo + " (" + anio + ")";
    }
}
