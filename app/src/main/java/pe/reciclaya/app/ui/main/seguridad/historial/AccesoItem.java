package pe.reciclaya.app.ui.main.seguridad.historial;

public class AccesoItem {

    private final int id;
    private final String fechaLogin;
    private final String ciudad;
    private final String pais;
    private final String estado;
    private final String tipoActividad;

    public AccesoItem(
            int id,
            String fechaLogin,
            String ciudad,
            String pais,
            String estado,
            String tipoActividad
    ) {
        this.id = id;
        this.fechaLogin = fechaLogin;
        this.ciudad = ciudad;
        this.pais = pais;
        this.estado = estado;
        this.tipoActividad = tipoActividad;
    }

    public int getId() {
        return id;
    }

    public String getFechaLogin() {
        return fechaLogin;
    }

    public String getCiudad() {
        return ciudad;
    }

    public String getPais() {
        return pais;
    }

    public String getEstado() {
        return estado;
    }

    public String getTipoActividad() {
        return tipoActividad;
    }

    public String getUbicacion() {
        if (ciudad == null || ciudad.isBlank()) {
            return pais == null || pais.isBlank()
                    ? "Ubicación desconocida"
                    : pais;
        }

        if (pais == null || pais.isBlank()) {
            return ciudad;
        }

        return ciudad + ", " + pais;
    }

    public boolean isAutorizado() {
        return "Autorizado".equalsIgnoreCase(estado);
    }

    public boolean isRechazado() {
        return "Rechazado".equalsIgnoreCase(estado);
    }

    public boolean isVerificacionMultifactor() {
        return "Verificación multifactor".equalsIgnoreCase(tipoActividad);
    }
}