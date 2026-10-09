package pe.reciclaya.app.data.repository.solicitud;

import androidx.annotation.NonNull;

import java.util.List;

import pe.reciclaya.app.data.model.solicitud.request.SolicitudRequest;
import pe.reciclaya.app.data.model.solicitud.response.SolicitudResponse;
import pe.reciclaya.app.data.remote.BackendClient;
import pe.reciclaya.app.data.remote.SolicitudService;
import pe.reciclaya.app.domain.model.solicitud.Solicitud;
import pe.reciclaya.app.data.mapper.SolicitudMapper;
import pe.reciclaya.app.domain.model.solicitud.SolicitudActualizada;
import pe.reciclaya.app.domain.model.solicitud.SolicitudFiltros;
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
    public void getSolicitudesCreadas(int id, RepositoryCallback<List<Solicitud>> callback) {
        apiService.getSolicitudesCreadas(id).enqueue(new Callback<>() {
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

    @Override
    public void getSolicitudesAceptadas(int id, RepositoryCallback<List<Solicitud>> callback) {
        apiService.getSolicitudesAceptadas(id).enqueue(new Callback<>() {
            @Override
            public void onResponse(@NonNull  Call<List<SolicitudResponse>> call, @NonNull  Response<List<SolicitudResponse>> response) {
                if(response.isSuccessful() && response.body() != null) {
                    callback.onSuccess(SolicitudMapper.allToDomain(response.body()));
                } else callback.onError("Error en el servidor, vuelva a intentarlo más tarde.");
            }

            @Override
            public void onFailure(@NonNull  Call<List<SolicitudResponse>> call, @NonNull Throwable t) {
                callback.onError("Error en la red, compruebe su conexión");
            }
        });
    }

    @Override
    public void getSolicitudesDisponibles(SolicitudFiltros solicitudFiltros, RepositoryCallback<List<Solicitud>> callback) {
        apiService.getSolicitudesDisponibles(
                solicitudFiltros.getTipoResiduoString(),
                solicitudFiltros.getFecha(),
                solicitudFiltros.getHora(),
                solicitudFiltros.getScore()
        ).enqueue(new Callback<>() {
            @Override
            public void onResponse(@NonNull  Call<List<SolicitudResponse>> call, @NonNull  Response<List<SolicitudResponse>> response) {
                if(response.isSuccessful() && response.body() != null) {
                    callback.onSuccess(SolicitudMapper.allToDomain(response.body()));
                } else callback.onError("Error en el servidor, vuelva a intentarlo más tarde.");
            }

            @Override
            public void onFailure(@NonNull  Call<List<SolicitudResponse>> call, @NonNull Throwable t) {
                callback.onError("Error en la red, compruebe su conexión");
            }
        });
    }

    @Override
    public void updateSolicitud(SolicitudActualizada solicitudActualizada, RepositoryCallback<Void> callback) {
        apiService.updateSolicitud(
                solicitudActualizada.getID(),
                SolicitudMapper.toRequest(solicitudActualizada)
        ).enqueue(new Callback<>() {
            @Override
            public void onResponse(@NonNull Call<Void> call, @NonNull Response<Void> response) {
                if(response.isSuccessful()) callback.onSuccess(null);
                else callback.onError("Error en el servidor, vuelva a intentarlo más tarde.");
            }

            @Override
            public void onFailure(@NonNull Call<Void> call, @NonNull Throwable t) {
                callback.onError("Error en la red, compruebe su conexión");
            }
        });
    }

    @Override
    public void cancelSolicitud(int solicitudID, RepositoryCallback<Void> callback) {
        apiService.cancelSolicitud(solicitudID).enqueue(new Callback<>() {
            @Override
            public void onResponse(@NonNull Call<Void> call, @NonNull Response<Void> response) {
                if(response.isSuccessful()) callback.onSuccess(null);
                else callback.onError("Error en el servidor, vuelva a intentarlo más tarde.");
            }

            @Override
            public void onFailure(@NonNull Call<Void> call, @NonNull Throwable t) {
                callback.onError("Error en la red, compruebe su conexión");
            }
        });
    }

    @Override
    public void acceptSolicitud(int solicitudID, int recicladorID, RepositoryCallback<Void> callback) {
        apiService.acceptSolicitud(solicitudID, recicladorID).enqueue(new Callback<>() {
            @Override
            public void onResponse(@NonNull Call<Boolean> call, @NonNull Response<Boolean> response) {
                if(response.isSuccessful() && response.body() != null) {
                    boolean aceptado = response.body();
                    if(aceptado) callback.onSuccess(null);
                    else callback.onError("La solicitud ya no se encuentra disponible.");
                } else callback.onError("Error en el servidor, vuelva a intentarlo más tarde.");
            }

            @Override
            public void onFailure(@NonNull Call<Boolean> call, @NonNull Throwable t) {
                callback.onError("Error en la red, compruebe su conexión");
            }
        });
    }
}
