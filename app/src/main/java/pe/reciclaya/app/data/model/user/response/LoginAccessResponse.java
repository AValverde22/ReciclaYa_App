package pe.reciclaya.app.data.model.user.response;

import com.google.gson.annotations.SerializedName;
public class LoginAccessResponse {
    @SerializedName("id")
    private int id;

    @SerializedName("user_id")
    private int userId;

    @SerializedName("direccion_ip")
    private String direccionIp;

    @SerializedName("app_version")
    private String appVersion;

    @SerializedName("ciudad")
    private String ciudad;

    @SerializedName("pais")
    private String pais;

    @SerializedName("latitud")
    private Double latitud;

    @SerializedName("longitud")
    private Double longitud;

    @SerializedName("estado")
    private String estado;

    @SerializedName("tipo_actividad")
    private String tipoActividad;

    @SerializedName("dispositivo")
    private String dispositivo;

    @SerializedName("fecha_login")
    private String fechaLogin;

    public int getId() {
        return id;
    }

    public int getUserId() {
        return userId;
    }

    public String getDireccionIp() {
        return direccionIp;
    }

    public String getAppVersion() {
        return appVersion;
    }

    public String getCiudad() {
        return ciudad;
    }

    public String getPais() {
        return pais;
    }

    public Double getLatitud() {
        return latitud;
    }

    public Double getLongitud() {
        return longitud;
    }

    public String getEstado() {
        return estado;
    }

    public String getTipoActividad() {
        return tipoActividad;
    }

    public String getDispositivo() {
        return dispositivo;
    }

    public String getFechaLogin() {
        return fechaLogin;
    }
}
