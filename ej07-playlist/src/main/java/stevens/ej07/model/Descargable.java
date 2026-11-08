package stevens.ej07.model;

/**
 * Contrato de los contenidos que se pueden descargar para escuchar sin conexión.
 */
public interface Descargable {

    /** Indica si el contenido ya fue descargado. */
    boolean estaDescargado();

    /** Marca el contenido como descargado. */
    void descargar();
}
