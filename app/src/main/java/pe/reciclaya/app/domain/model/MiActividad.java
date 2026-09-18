package pe.reciclaya.app.domain.model;

public class MiActividad {
    private final String actividad;
    private final String mensajeActividad;
    private final int logoActividad;

    public MiActividad(String actividad, String mensajeActividad, int logoActividad) {
        this.actividad = actividad;
        this.mensajeActividad = mensajeActividad;
        this.logoActividad = logoActividad;
    }

    public String getActividad() { return actividad; }
    public String getMensajeActividad() { return mensajeActividad; }
    public int getLogoActividad() { return logoActividad; }
}
