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
    @GET("solicitud/activa")
    Call<pe.reciclaya.app.data.model.solicitud.response.RecojoActivo> getActiva(@Query("user_id") int userID);

    @PATCH("solicitud/{id}/ubicacion")
    Call<com.google.gson.JsonObject> ubicacion(@Path("id") int id, @Query("user_id") int userID, @Body com.google.gson.JsonObject body);

    @POST("solicitud/{id}/confirmar")
    Call<com.google.gson.JsonObject> confirmar(@Path("id") int id, @Query("user_id") int userID);

    @POST("solicitud/{id}/cancelar-recojo")
    Call<com.google.gson.JsonObject> cancelarRecojo(@Path("id") int id, @Query("user_id") int userID);

    @POST("solicitud/{id}/calificacion")
    Call<com.google.gson.JsonObject> calificar(@Path("id") int id, @Body com.google.gson.JsonObject body);

    @GET("solicitud/{id}/ruta")
    Call<pe.reciclaya.app.data.model.solicitud.response.RecojoActivo.Ruta> ruta(@Path("id") int id, @Query("user_id") int userID);
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
    Call<Void> updateSolicitud(@Path("id") int solicitudID, @Query("user_id") int userID, @Body UpdateRequest requestModel);

    @PATCH("solicitud/{id}/cancel")
    Call<Void> cancelSolicitud(@Path("id") int solicitudID, @Query("user_id") int userID);

    @PATCH("solicitud/{id}/accept")
    Call<Boolean> acceptSolicitud(@Path("id") int solicitudID,
                                  @Query("user_id") int userID);
}
