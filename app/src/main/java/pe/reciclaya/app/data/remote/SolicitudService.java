package pe.reciclaya.app.data.remote;

import java.util.List;

import pe.reciclaya.app.data.model.solicitud.request.SolicitudRequest;
import pe.reciclaya.app.data.model.solicitud.response.SolicitudResponse;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface SolicitudService {
    @POST("solicitud")
    Call<Void> createSolicitud(@Body SolicitudRequest requestModel);

    @GET("solicitud")
    Call<List<SolicitudResponse>> getSolicitudes(@Query("user_id") Integer userID);
}
