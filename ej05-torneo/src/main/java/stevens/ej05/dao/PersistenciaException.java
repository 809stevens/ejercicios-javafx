package stevens.ej05.dao;

/**
 * Error controlado de la capa de persistencia.
 * El controlador lo captura y muestra el mensaje en la ventana.
 */
public class PersistenciaException extends Exception {

    public PersistenciaException(String mensaje, Throwable causa) {
        super(mensaje, causa);
    }
}
