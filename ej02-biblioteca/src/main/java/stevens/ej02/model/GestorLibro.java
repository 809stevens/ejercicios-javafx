package stevens.ej02.model;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import java.util.HashMap;
import java.util.Map;

public class GestorLibro {
    private static final GestorLibro INSTANCE = new GestorLibro();
    private final ObservableList<Libro> libros;
    private final Map<String, Libro> librosPorIsbn;

    private GestorLibro() {
        libros = FXCollections.observableArrayList();
        librosPorIsbn = new HashMap<>();
    }

    public static GestorLibro getInstance() {
        return INSTANCE;
    }

    public ObservableList<Libro> getLibros() {
        return libros;
    }

    public boolean guardar(Libro libro) {
        String isbn = String.valueOf(libro.getIsbn());
        if (librosPorIsbn.containsKey(isbn)) {
            return false;
        }
        libros.add(libro);
        librosPorIsbn.put(isbn, libro);
        return true;
    }

    public void actualizarLibro(Libro libro) {
        librosPorIsbn.put(String.valueOf(libro.getIsbn()), libro);
    }

    public void eliminarLibro(Libro libro) {
        libros.remove(libro);
        librosPorIsbn.remove(String.valueOf(libro.getIsbn()));
    }

    public Libro buscarPorIsbn(String isbn) {
        return librosPorIsbn.get(isbn);
    }

    public boolean existeIsbn(String isbn) {
        return librosPorIsbn.containsKey(isbn);
    }

    public boolean prestar(Libro libro) {
        if (libro.isPrestado()) {
            return false;
        }
        libro.setPrestado(true);
        return true;
    }
}
