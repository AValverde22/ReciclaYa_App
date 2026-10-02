package pe.reciclaya.app.data.model.solicitud.get.response;

import com.google.gson.annotations.SerializedName;

public class GetResponse {
    @SerializedName("id") private int solicitudID;
    @SerializedName("type") private String tipoResiduo;
    @SerializedName("size") private String tamano;
    @SerializedName("day") private String dia;
    @SerializedName("time") private String hora;
    @SerializedName("address") private String direccion;
    @SerializedName("latitude") private double latitudeSolicitud;
    @SerializedName("longitude") private double longitudeSolicitud;
    @SerializedName("status") private String estado;

    @SerializedName("user_id") private int userID;
    @SerializedName("full_name") private String nombreCompleto;
    @SerializedName("profile_photo_url") private String urlFotoPerfil;
    @SerializedName("score") private float puntuacion;

    public GetResponse(){};

    public GetResponse(int solicitudID, String tipoResiduo, String tamano, String dia, String hora, String direccion, double latitudeSolicitud, double longitudeSolicitud, String estado, int userID, String nombreCompleto, String urlFotoPerfil, float puntuacion) {
        this.solicitudID = solicitudID;
        this.tipoResiduo = tipoResiduo;
        this.tamano = tamano;
        this.dia = dia;
        this.hora = hora;
        this.direccion = direccion;
        this.latitudeSolicitud = latitudeSolicitud;
        this.longitudeSolicitud = longitudeSolicitud;
        this.estado = estado;

        this.userID = userID;
        this.nombreCompleto = nombreCompleto;
        this.urlFotoPerfil = urlFotoPerfil;
        this.puntuacion = puntuacion;
    }

    public int getSolicitudID() { return solicitudID; }
    public String getTipoResiduo() { return tipoResiduo; }
    public String getTamano() { return tamano; }
    public String getDia() { return dia; }
    public String getHora() { return hora; }
    public String getDireccion() { return direccion; }
    public double getLatitudeSolicitud() { return latitudeSolicitud; }
    public double getLongitudeSolicitud() { return longitudeSolicitud; }
    public String getEstado() { return estado; }

    public int getUserID() { return userID; }
    public String getNombreCompleto() { return nombreCompleto; }
    public String getUrlFotoPerfil() { return urlFotoPerfil; }
    public float getPuntuacion() { return puntuacion; }

    public void setSolicitudID(int solicitudID) { this.solicitudID = solicitudID; }
    public void setTipoResiduo(String tipoResiduo) { this.tipoResiduo = tipoResiduo; }
    public void setTamano(String tamano) { this.tamano = tamano; }
    public void setDia(String dia) { this.dia = dia; }
    public void setHora(String hora) { this.hora = hora; }
    public void setDireccion(String direccion) { this.direccion = direccion; }
    public void setLatitudeSolicitud(double latitudeSolicitud) { this.latitudeSolicitud = latitudeSolicitud; }
    public void setLongitudeSolicitud(double longitudeSolicitud) { this.longitudeSolicitud = longitudeSolicitud; }
    public void setEstado(String estado) { this.estado = estado; }

    public void setUserID(int userID) { this.userID = userID; }
    public void setNombreCompleto(String nombreCompleto) { this.nombreCompleto = nombreCompleto; }
    public void setUrlFotoPerfil(String urlFotoPerfil) { this.urlFotoPerfil = urlFotoPerfil; }
    public void setPuntuacion(float puntuacion) { this.puntuacion = puntuacion; }
}
