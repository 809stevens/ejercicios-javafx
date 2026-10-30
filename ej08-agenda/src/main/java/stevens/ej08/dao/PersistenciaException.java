package stevens.ej08.dao;

public class PersistenciaException extends Exception {

    public PersistenciaException(String mensaje, Throwable causa){
        super(mensaje,causa);
    }
}
