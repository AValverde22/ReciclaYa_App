package pe.reciclaya.app.data.model.user.response.reportes;

import com.google.gson.annotations.SerializedName;

public class ReporteActividadDetalleWrapper {

    @SerializedName("success")
    private boolean success;

    @SerializedName("message")
    private String message;

    @SerializedName("data")
    private ReporteActividadResponse data;

    public boolean isSuccess() {
        return success;
    }

    public String getMessage() {
        return message;
    }

    public ReporteActividadResponse getData() {
        return data;
    }
}