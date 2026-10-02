package pe.reciclaya.app.data.repository.main;

import androidx.annotation.NonNull;

import pe.reciclaya.app.data.model.solicitud.crear.request.CrearRequest;
import pe.reciclaya.app.domain.model.Solicitud;
import pe.reciclaya.app.domain.repository.SolicitudRepository;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CreateSolicitud extends SolicitudRepository {
    @Override
    public void manageSolicitud(Solicitud solicitud, SolicitudCallback callback) {
        CrearRequest body = new CrearRequest(solicitud);

        apiService.createSolicitud(body).enqueue(new Callback<>() {
            @Override
            public void onResponse(@NonNull Call<Void> call, @NonNull Response<Void> response) {
                if(response.isSuccessful()) callback.onSuccess();
                else callback.onError("Error en el servidor, vuelva a intentarlo más tarde.");
            }

            @Override
            public void onFailure(@NonNull Call<Void> call, @NonNull  Throwable t) {
                callback.onError("Error en la red, compruebe su conexión");
            }
        });
    }
}
