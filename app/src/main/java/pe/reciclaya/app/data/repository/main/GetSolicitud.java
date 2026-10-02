package pe.reciclaya.app.data.repository.main;

import static pe.reciclaya.app.domain.usecase.solicitud_transformer.SolicitudTransformer.createSolicitudesList;

import androidx.annotation.NonNull;

import java.util.List;

import pe.reciclaya.app.data.model.solicitud.get.response.GetResponse;
import pe.reciclaya.app.data.remote.BackendClient;
import pe.reciclaya.app.data.remote.SolicitudService;
import pe.reciclaya.app.domain.model.Solicitud;
import pe.reciclaya.app.domain.model.User;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class GetSolicitud {
    private final SolicitudService apiService;
    private static User user;

    public GetSolicitud() {
        apiService = BackendClient.getSolicitudService();
        user = User.getInstance();
    }

    public void getSolicitudes(SolicitudCallback callback) {
        int id = user.getID();

        apiService.getSolicitudes(id).enqueue(new Callback<>() {
            @Override
            public void onResponse(@NonNull Call<List<GetResponse>> call, @NonNull Response<List<GetResponse>> response) {
                if(response.isSuccessful() && response.body() != null) {
                    callback.onSuccess(createSolicitudesList(response.body()));
                } else callback.onError("Error en el servidor, vuelva a intentarlo más tarde.");
            }

            @Override
            public void onFailure(@NonNull Call<List<GetResponse>> call, @NonNull Throwable t) {
                callback.onError("Error en la red, compruebe su conexión");
            }
        });
    }

    public interface SolicitudCallback {
        void onSuccess(List<Solicitud> solicitudes);
        void onError(String errorMessage);
    }
}
