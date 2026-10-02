package pe.reciclaya.app.data.repository.main;

import androidx.annotation.NonNull;

import com.google.android.gms.maps.model.LatLng;

import pe.reciclaya.app.data.model.solicitud.crear.request.CrearRequest;
import pe.reciclaya.app.data.remote.BackendClient;
import pe.reciclaya.app.data.remote.SolicitudService;
import pe.reciclaya.app.domain.User;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SolicitudRepository {
    private final SolicitudService apiService;
    private static User user;

    public SolicitudRepository() {
        apiService = BackendClient.getSolicitudService();
        user = User.getInstance();
    }

    public void crearSolicitud(String tipo, String tamano, String dia, String hora, String direccion, LatLng geolocalizacion, SolicitudCallback callback) {
        CrearRequest body = new CrearRequest(tipo, tamano, dia, hora, direccion, geolocalizacion.latitude, geolocalizacion.longitude, user.getID());

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

    public interface SolicitudCallback {
        void onSuccess();
        void onError(String errorMessage);
    }
}
