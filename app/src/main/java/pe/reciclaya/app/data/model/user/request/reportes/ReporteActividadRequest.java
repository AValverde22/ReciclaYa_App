package pe.reciclaya.app.data.model.user.request.reportes;

import com.google.gson.annotations.SerializedName;

public class ReporteActividadRequest {

    @SerializedName("logAccesos_id")
    private int logAccesosId;

    @SerializedName("descripcion")
    private String descripcion;

    public ReporteActividadRequest(int logAccesosId, String descripcion) {
        this.logAccesosId = logAccesosId;
        this.descripcion = descripcion;
    }
}