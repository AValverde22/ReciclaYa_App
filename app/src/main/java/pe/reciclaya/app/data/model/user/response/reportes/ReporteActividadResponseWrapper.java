package pe.reciclaya.app.data.model.user.response.reportes;

import com.google.gson.annotations.SerializedName;
import java.util.List;

public class ReporteActividadResponseWrapper {

    @SerializedName("success")
    private boolean success;

    @SerializedName("message")
    private String message;

    @SerializedName("data")
    private List<ReporteActividadResponse> data;

    public boolean isSuccess() {
        return success;
    }

    public String getMessage() {
        return message;
    }

    public List<ReporteActividadResponse> getData() {
        return data;
    }
}