package pe.reciclaya.app.data.model.solicitud.response;

import com.google.gson.annotations.SerializedName;

import pe.reciclaya.app.data.model.user.response.UserSolicitudResponse;

public class SolicitudResponse {
    @SerializedName("id") private int solicitudID;
    @SerializedName("type") private String tipoResiduo;
    @SerializedName("size") private String tamano;
    @SerializedName("day") private String fecha;
    @SerializedName("time") private String hora;
    @SerializedName("address") private String direccion;
    private double latitude;
    private double longitude;
    @SerializedName("status") private String estado;
    private UserSolicitudResponse user;

    public SolicitudResponse() {}

    public SolicitudResponse(int solicitudID,
                             String tipoResiduo,
                             String tamano,
                             String fecha,
                             String hora,
                             String direccion,
                             double latitude,
                             double longitude,
                             String estado,
                             UserSolicitudResponse user) {
        this.solicitudID = solicitudID;
        this.tipoResiduo = tipoResiduo;
        this.tamano = tamano;
        this.fecha = fecha;
        this.hora = hora;
        this.direccion = direccion;
        this.latitude = latitude;
        this.longitude = longitude;
        this.estado = estado;
        this.user = user;
    }

    public int getSolicitudID() { return solicitudID; }
    public String getTipoResiduo() { return tipoResiduo; }
    public String getTamano() { return tamano; }
    public String getFecha() { return fecha; }
    public String getHora() { return hora; }
    public String getDireccion() { return direccion; }
    public double getLatitude() { return latitude; }
    public double getLongitude() { return longitude; }
    public String getEstado() { return estado; }
    public UserSolicitudResponse getUser() { return user; }

    public void setSolicitudID(int solicitudID) { this.solicitudID = solicitudID; }
    public void setTipoResiduo(String tipoResiduo) { this.tipoResiduo = tipoResiduo; }
    public void setTamano(String tamano) { this.tamano = tamano; }
    public void setFecha(String fecha) { this.fecha = fecha; }
    public void setHora(String hora) { this.hora = hora; }
    public void setDireccion(String direccion) { this.direccion = direccion; }
    public void setLatitude(double latitude) { this.latitude = latitude; }
    public void setLongitude(double longitude) { this.longitude = longitude; }
    public void setEstado(String estado) { this.estado = estado; }
    public void setUser(UserSolicitudResponse user) { this.user = user; }
}
