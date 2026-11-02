package stevens.ej03.model;

public class Paciente {
    private String nombre;
    private String telefono;

    public Paciente(String nombre, String telefono) {
        this.nombre = nombre;
        this.telefono = telefono;
    }

    public Paciente() {
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    @Override
    public String toString() {
        return "nombre: " + nombre  +
                "\ntelefono: " + telefono ;
    }
}
