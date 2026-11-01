package pe.reciclaya.app.data.model.user.request.login;

public class LoginAccessRequest {

    private String app_version;
    private String dispositivo;
    private String tipo_actividad;
    private String estado;
    private Double latitud;
    private Double longitud;
    private String ciudad;
    private String pais;

    public LoginAccessRequest(
            String appVersion,
            String dispositivo,
            Double latitud,
            Double longitud,
            String ciudad,
            String pais
    ) {
        this.app_version = appVersion;
        this.dispositivo = dispositivo;
        this.tipo_actividad = "Inicio de sesión";
        this.estado = "Autorizado";
        this.latitud = latitud;
        this.longitud = longitud;
        this.ciudad = ciudad;
        this.pais = pais;
    }

    public String getApp_version() {
        return app_version;
    }

    public String getDispositivo() {
        return dispositivo;
    }

    public String getTipo_actividad() {
        return tipo_actividad;
    }

    public String getEstado() {
        return estado;
    }

    public Double getLatitud() {
        return latitud;
    }

    public Double getLongitud() {
        return longitud;
    }

    public String getCiudad() {
        return ciudad;
    }

    public String getPais() {
        return pais;
    }
}