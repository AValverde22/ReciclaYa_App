package pe.reciclaya.app.data.repository.main;

import androidx.annotation.NonNull;

import pe.reciclaya.app.data.model.solicitud.update.request.UpdateRequest;
import pe.reciclaya.app.domain.model.Solicitud;
import pe.reciclaya.app.domain.repository.SolicitudRepository;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class UpdateSolicitud extends SolicitudRepository {
    @Override
    public void manageSolicitud(Solicitud solicitud, SolicitudCallback callback) {
        UpdateRequest body = new UpdateRequest(solicitud);

        apiService.updateSolicitud(solicitud.getSolicitudID(), body).enqueue(new Callback<>() {
            @Override
            public void onResponse(@NonNull Call<Void> call, @NonNull Response<Void> response) {
                //if(response.isSuccessful())
            }

            @Override
            public void onFailure(@NonNull Call<Void> call, @NonNull Throwable t) {

            }
        });
    }
}
