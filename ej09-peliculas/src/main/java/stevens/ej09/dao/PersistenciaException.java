package stevens.ej09.dao;


public class PersistenciaException extends Exception {

    public PersistenciaException(String mensaje, Throwable cuase){
        super(mensaje, cuase);
    }


}
