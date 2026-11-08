package stevens.ej05.model;

/**
 * Contrato de los participantes que pueden clasificar directamente al torneo.
 */
public interface Competitivo {

    /** Indica si el participante alcanza el puntaje mínimo para clasificar. */
    boolean clasifica();
}
