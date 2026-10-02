package pe.reciclaya.app.data.remote;

import java.util.List;

import pe.reciclaya.app.data.model.solicitud.crear.request.CrearRequest;
import pe.reciclaya.app.data.model.solicitud.get.response.GetResponse;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface SolicitudService {
    @POST("solicitud")
    Call<Void> createSolicitud(@Body CrearRequest requestModel);

    @GET("solicitud")
    Call<List<GetResponse>> getSolicitudes(@Query("created_by") int userID);
}
