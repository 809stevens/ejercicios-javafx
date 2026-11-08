package stevens.ej02.model;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;

public class Libro {
    private String titulo;
    private String autor;
    private int isbn;
    private BooleanProperty prestado = new SimpleBooleanProperty(false);
    private String anio;
    private String fechaDev;

    public Libro(String titulo, String autor, int isbn, boolean prestado, String anio, String fechaDev) {
        this.titulo = titulo;
        this.autor = autor;
        this.isbn = isbn;
        this.prestado.set(prestado);
        this.anio = anio;
        this.fechaDev = fechaDev;
    }

    public Libro() {
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getAutor() {
        return autor;
    }

    public void setAutor(String autor) {
        this.autor = autor;
    }

    public int getIsbn() {
        return isbn;
    }

    public void setIsbn(int isbn) {
        this.isbn = isbn;
    }

    public BooleanProperty prestadoProperty() {
        return prestado;
    }

    public boolean isPrestado() {
        return prestado.get();
    }

    public void setPrestado(boolean prestado) {
        this.prestado.set(prestado);
    }



    public String getAnio() {
        return anio;
    }

    public void setAnio(String anio) {
        this.anio = anio;
    }

    public String getFechaDev() {
        return fechaDev;
    }

    public void setFechaDev(String fechaDev) {
        this.fechaDev = fechaDev;
    }


    @Override
    public String toString() {
        return "autor: " + autor + '\'' +
                "titulo: " + titulo + '\'' +
                "ISBN: " + isbn +
                "prestado" + (prestado.get() ?"Si":"No") +
                "Fecha devolucion: '" + fechaDev;
    }


}
