package pe.reciclaya.app.data.repository.solicitud;

import androidx.annotation.NonNull;

import java.util.List;

import pe.reciclaya.app.data.model.solicitud.request.SolicitudRequest;
import pe.reciclaya.app.data.model.solicitud.response.SolicitudResponse;
import pe.reciclaya.app.data.remote.BackendClient;
import pe.reciclaya.app.data.remote.SolicitudService;
import pe.reciclaya.app.domain.model.solicitud.Solicitud;
import pe.reciclaya.app.data.mapper.SolicitudMapper;
import pe.reciclaya.app.domain.model.solicitud.SolicitudNueva;
import pe.reciclaya.app.domain.repository.RepositoryCallback;
import pe.reciclaya.app.domain.repository.solicitud.SolicitudRepository;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SolicitudRepositoryImp implements SolicitudRepository {
    private static SolicitudService apiService;

    public SolicitudRepositoryImp() {
        apiService = BackendClient.getSolicitudService();
    }

    @Override
    public void createSolicitud(SolicitudNueva nuevaSolicitud, RepositoryCallback<Void> callback) {
        SolicitudRequest body = SolicitudMapper.toRequest(nuevaSolicitud);

        apiService.createSolicitud(body).enqueue(new Callback<>() {
            @Override
            public void onResponse(@NonNull Call<Void> call, @NonNull Response<Void> response) {
                if(response.isSuccessful()) callback.onSuccess(null);
                else callback.onError("Error en el servidor, vuelva a intentarlo más tarde.");
            }

            @Override
            public void onFailure(@NonNull Call<Void> call, @NonNull  Throwable t) {
                callback.onError("Error en la red, compruebe su conexión");
            }
        });
    }

    @Override
    public void getSolicitudes(int id, RepositoryCallback<List<Solicitud>> callback) {
        apiService.getSolicitudes(id).enqueue(new Callback<>() {
            @Override
            public void onResponse(@NonNull Call<List<SolicitudResponse>> call, @NonNull Response<List<SolicitudResponse>> response) {
                if(response.isSuccessful() && response.body() != null) {
                    callback.onSuccess(SolicitudMapper.allToDomain(response.body()));
                } else callback.onError("Error en el servidor, vuelva a intentarlo más tarde.");
            }

            @Override
            public void onFailure(@NonNull Call<List<SolicitudResponse>> call, @NonNull Throwable t) {
                callback.onError("Error en la red, compruebe su conexión");
            }
        });
    }


}
