package stevens.ej06.dao;

public class PersistenciaException extends Exception {


    public PersistenciaException(String message, Throwable causa) {
        super(message,causa);
    }
}
