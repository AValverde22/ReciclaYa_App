package pe.reciclaya.app.data.remote;

import pe.reciclaya.app.data.model.solicitud.crear.request.CrearRequest;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface SolicitudService {
    @POST("solicitud")
    Call<Void> createSolicitud(@Body CrearRequest requestModel);
}
