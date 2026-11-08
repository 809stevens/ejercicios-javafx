package stevens.ej07.model;

/**
 * Podcast. Se estima un peso de 128 kbps y puede marcarse como descargado.
 */
public class Podcast extends Contenido implements Descargable {

    public static final int CALIDAD_KBPS = 128;

    private int numeroEpisodio;
    private String plataforma;
    private boolean descargado;

    public Podcast() {
    }

    public Podcast(String titulo, String autor, String genero, int duracionSegundos,
                   int numeroEpisodio, String plataforma) {
        super(titulo, autor, genero, duracionSegundos);
        setNumeroEpisodio(numeroEpisodio);
        setPlataforma(plataforma);
        this.descargado = false;
    }

    public int getNumeroEpisodio() {
        return numeroEpisodio;
    }

    public void setNumeroEpisodio(int numeroEpisodio) {
        if (numeroEpisodio < 1) {
            throw new IllegalArgumentException("El número de episodio debe ser mayor o igual a 1.");
        }
        this.numeroEpisodio = numeroEpisodio;
    }

    public String getPlataforma() {
        return plataforma;
    }

    public void setPlataforma(String plataforma) {
        this.plataforma = validarTexto(plataforma, "La plataforma");
    }

    @Override
    public boolean estaDescargado() {
        return descargado;
    }

    @Override
    public void descargar() {
        this.descargado = true;
    }

    @Override
    public double calcularPesoMB() {
        return getDuracionSegundos() * CALIDAD_KBPS / 8.0 / 1024.0;
    }

    @Override
    public String getTipo() {
        return "Podcast";
    }
}
