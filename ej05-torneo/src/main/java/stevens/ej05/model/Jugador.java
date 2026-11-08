package stevens.ej05.model;

/**
 * Jugador de un equipo. Puntaje = 12 puntos por partida ganada, más 30 si su rol
 * es DPS o 20 para los demás roles.
 */
public class Jugador extends Participante implements Competitivo {

    public static final int PUNTOS_POR_PARTIDA = 12;
    public static final int BONO_ROL_DPS = 30;
    public static final int BONO_ROL_BASE = 20;

    private String nickname;
    private String rol;
    private int partidasGanadas;

    public Jugador() {
    }

    public Jugador(String nombre, String equipo, int edad, String nickname, String rol, int partidasGanadas) {
        super(nombre, equipo, edad);
        setNickname(nickname);
        setRol(rol);
        setPartidasGanadas(partidasGanadas);
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = validarTexto(nickname, "El nickname");
    }

    public String getRol() {
        return rol;
    }

    public void setRol(String rol) {
        this.rol = validarTexto(rol, "El rol");
    }

    public int getPartidasGanadas() {
        return partidasGanadas;
    }

    public void setPartidasGanadas(int partidasGanadas) {
        this.partidasGanadas = validarEnteroNoNegativo(partidasGanadas, "Las partidas ganadas");
    }

    @Override
    public int calcularPuntaje() {
        int bono = "DPS".equalsIgnoreCase(rol) ? BONO_ROL_DPS : BONO_ROL_BASE;
        return partidasGanadas * PUNTOS_POR_PARTIDA + bono;
    }

    @Override
    public boolean clasifica() {
        return calcularPuntaje() >= PUNTAJE_DESTACADO;
    }

    @Override
    public String getTipo() {
        return "Jugador";
    }
}
