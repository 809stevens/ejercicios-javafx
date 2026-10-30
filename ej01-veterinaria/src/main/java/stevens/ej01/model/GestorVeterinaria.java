package stevens.ej01.model;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class GestorVeterinaria {
    private ObservableList<Mascota> mascotas;
    private ObservableList<Duenio> duenios;

    public GestorVeterinaria() {
        mascotas = FXCollections.observableArrayList();
        duenios = FXCollections.observableArrayList();
    }

    public ObservableList<Mascota> getMascotas() {
        return mascotas;
    }

    public void setMascotas(ObservableList<Mascota> mascotas) {
        this.mascotas = mascotas;
    }

    public ObservableList<Duenio> getDuenios() {
        return duenios;
    }

    public void setDuenios(ObservableList<Duenio> duenios) {
        this.duenios = duenios;
    }

    public void guardarDuenio(Duenio duenio) {
        duenios.add(duenio);
    }

    public void guardarMascota(Mascota mascota) {
        mascotas.add(mascota);
    }

    public void eliminarMascota(Mascota mascota) {
        mascotas.remove(mascota);
    }
}
