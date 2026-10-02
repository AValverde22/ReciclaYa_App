package pe.reciclaya.app.data.model.solicitud.crear.request;

import com.google.gson.annotations.SerializedName;

public class CrearRequest {
    @SerializedName("type") private String tipoResiduo;
    @SerializedName("size") private String tamano;
    @SerializedName("day") private String dia;
    @SerializedName("time") private String hora;
    @SerializedName("address") private String direccion;
    private double latitude;
    private double longitude;
    @SerializedName("created_by") int idUser;

    public CrearRequest() {}

    public CrearRequest(String tipoResiduo, String tamano, String dia, String hora, String direccion, double latitude, double longitude, int idUser) {
        this.tipoResiduo = tipoResiduo;
        this.tamano = tamano;
        this.dia = dia;
        this.hora = hora;
        this.direccion = direccion;
        this.latitude = latitude;
        this.longitude = longitude;
        this.idUser = idUser;
    }

    public String getTipoResiduo() { return tipoResiduo; }
    public String getTamano() { return tamano; }
    public String getDia() { return dia; }
    public String getHora() { return hora; }
    public String getDireccion() { return direccion; }
    public double getLatitude() { return latitude; }
    public double getLongitude() { return longitude; }
    public int getId_user() { return idUser; }

    public void setTipoResiduo(String tipoResiduo) { this.tipoResiduo = tipoResiduo; }
    public void setTamano(String tamano) { this.tamano = tamano; }
    public void setDia(String dia) { this.dia = dia; }
    public void setHora(String hora) { this.hora = hora; }
    public void setDireccion(String direccion) { this.direccion = direccion; }
    public void setLatitud(double latitude) { this.latitude = latitude; }
    public void setLongitud(double longitude) { this.longitude = longitude; }
    public void setIdUser(int idUser) { this.idUser = idUser; }
}
