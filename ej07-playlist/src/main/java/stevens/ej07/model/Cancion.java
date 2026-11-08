package stevens.ej07.model;

/**
 * Canción. Se estima un peso de 320 kbps.
 */
public class Cancion extends Contenido {

    public static final int CALIDAD_KBPS = 320;

    private String album;
    private boolean explicita;

    public Cancion() {
    }

    public Cancion(String titulo, String autor, String genero, int duracionSegundos,
                   String album, boolean explicita) {
        super(titulo, autor, genero, duracionSegundos);
        setAlbum(album);
        setExplicita(explicita);
    }

    public String getAlbum() {
        return album;
    }

    public void setAlbum(String album) {
        this.album = validarTexto(album, "El álbum");
    }

    public boolean isExplicita() {
        return explicita;
    }

    public void setExplicita(boolean explicita) {
        this.explicita = explicita;
    }

    @Override
    public double calcularPesoMB() {
        return getDuracionSegundos() * CALIDAD_KBPS / 8.0 / 1024.0;
    }

    @Override
    public String getTipo() {
        return "Canción";
    }
}
