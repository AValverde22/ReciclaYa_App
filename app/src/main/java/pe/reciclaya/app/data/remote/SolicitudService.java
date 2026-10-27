package pe.reciclaya.app.data.remote;

import java.util.List;

import pe.reciclaya.app.data.model.solicitud.request.SolicitudRequest;
import pe.reciclaya.app.data.model.solicitud.request.UpdateRequest;
import pe.reciclaya.app.data.model.solicitud.response.SolicitudResponse;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.PATCH;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface SolicitudService {
    @POST("solicitud")
    Call<Void> createSolicitud(@Body SolicitudRequest requestModel);

    @GET("solicitud")
    Call<List<SolicitudResponse>> getSolicitudesCreadas(@Query("user_id") Integer userID);

    @GET("solicitud/reciclador")
    Call<List<SolicitudResponse>> getSolicitudesAceptadas(@Query("user_id") Integer userID);

    @GET("solicitud/disponibles")
    Call<List<SolicitudResponse>> getSolicitudesDisponibles(
            @Query("type") String tipoResiduo,
            @Query("day") String fecha,
            @Query("time") String hora,
            @Query("score") String score
    );

    @PATCH("solicitud/{id}")
    Call<Void> updateSolicitud(@Path("id") int solicitudID, @Body UpdateRequest requestModel);

    @PATCH("solicitud/{id}/cancel")
    Call<Void> cancelSolicitud(@Path("id") int solicitudID);

    @PATCH("solicitud/{id}/accept")
    Call<Boolean> acceptSolicitud(@Path("id") int solicitudID,
                                  @Query("user_id") int userID);
}
