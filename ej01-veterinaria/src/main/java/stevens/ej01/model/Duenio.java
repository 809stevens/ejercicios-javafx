package stevens.ej01.model;

public class Duenio {
    private String nombre;
    private String telefono;
    private String mail;

    public Duenio(String nombre, String telefono, String mail) {
        this.nombre = nombre;
        this.telefono = telefono;
        this.mail = mail;
    }

    public Duenio() {
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombreD) {
        this.nombre = nombreD;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public String getMail() {
        return mail;
    }

    public void setMail(String mail) {
        this.mail = mail;
    }

    @Override
    public String toString() {
        return "nombre: " + nombre + " telefono: " + telefono ;
    }
}
