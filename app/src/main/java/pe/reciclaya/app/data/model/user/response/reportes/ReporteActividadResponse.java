package pe.reciclaya.app.data.model.user.response.reportes;

import com.google.gson.annotations.SerializedName;

import pe.reciclaya.app.data.model.user.response.LoginAccessResponse;

public class ReporteActividadResponse {

    @SerializedName("id")
    private int id;

    @SerializedName("logAccesos_id")
    private int logAccesosId;

    @SerializedName("user_id")
    private int userId;

    @SerializedName("descripcion")
    private String descripcion;

    @SerializedName("fecha_reporte")
    private String fechaReporte;

    @SerializedName("estado")
    private String estado;

    @SerializedName("log_acceso")
    private LoginAccessResponse logAcceso;

    public int getId() {
        return id;
    }

    public int getLogAccesosId() {
        return logAccesosId;
    }

    public int getUserId() {
        return userId;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public String getFechaReporte() {
        return fechaReporte;
    }

    public String getEstado() {
        return estado;
    }

    public LoginAccessResponse getLogAcceso() {
        return logAcceso;
    }
}