package stevens.ej03.utils;

import java.nio.file.Path;

public class Paths {
    public final static Path DOCTOR = Path.of("doctors.json").toAbsolutePath();
    public final static  Path PACIENTES = Path.of("pacientes.json").toAbsolutePath();
    public final static Path TURNOS = Path.of("turnos.json").toAbsolutePath();


    public static final String MAIN_VIEW = "/stevens/ej03/Main.fxml";
    public static final String DOCTOR_VIEW = "/stevens/ej03/Doctor-view.fxml";
    public static final String PACIENTE_VIEW = "/stevens/ej03/Paciente-view.fxml";

    private Paths(){

    }
}
