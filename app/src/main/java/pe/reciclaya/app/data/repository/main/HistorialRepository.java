package pe.reciclaya.app.data.repository.main;

import androidx.annotation.NonNull;

import java.util.ArrayList;
import java.util.List;

import pe.reciclaya.app.data.model.solicitud.get.response.GetResponse;
import pe.reciclaya.app.data.remote.BackendClient;
import pe.reciclaya.app.data.remote.SolicitudService;
import pe.reciclaya.app.domain.User;
import pe.reciclaya.app.ui.main.usuario.solicitud_list.FactoryCancelada;
import pe.reciclaya.app.ui.main.usuario.solicitud_list.FactoryDisponible;
import pe.reciclaya.app.ui.main.usuario.solicitud_list.FactoryFinalizada;
import pe.reciclaya.app.ui.main.usuario.solicitud_list.FactorySolicitud;
import pe.reciclaya.app.ui.main.usuario.solicitud_list.Solicitud;
import pe.reciclaya.app.ui.main.usuario.tipo_residuo_list.FactoryPapel;
import pe.reciclaya.app.ui.main.usuario.tipo_residuo_list.FactoryPlastico;
import pe.reciclaya.app.ui.main.usuario.tipo_residuo_list.FactoryTipoResiduo;
import pe.reciclaya.app.ui.main.usuario.tipo_residuo_list.FactoryVidrio;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class HistorialRepository {
    private final SolicitudService apiService;
    private static User user;

    public HistorialRepository() {
        apiService = BackendClient.getSolicitudService();
        user = User.getInstance();
    }

    public void getSolicitudes(HistorialCallback callback) {
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

    private List<Solicitud> createSolicitudesList(List<GetResponse> responses) {
        List<Solicitud> solicitudes = new ArrayList<>();

        for(GetResponse response : responses){
            FactorySolicitud factorySolicitud;
            FactoryTipoResiduo factoryTipoResiduo;

            switch(response.getEstado()) {
                case "Disponible": factorySolicitud = new FactoryDisponible(); break;
                case "Cancelada": factorySolicitud = new FactoryCancelada(); break;
                default: factorySolicitud = new FactoryFinalizada(); break;
            }

            switch(response.getTipoResiduo()) {
                case "Plástico": factoryTipoResiduo = new FactoryPlastico(); break;
                case "Vidrio": factoryTipoResiduo = new FactoryVidrio(); break;
                default: factoryTipoResiduo = new FactoryPapel(); break;
            }

            solicitudes.add(factorySolicitud.crear(
                    response.getSolicitudID(),
                    factoryTipoResiduo.crear(),
                    response.getTamano(),
                    response.getDia(),
                    response.getHora(),
                    response.getDireccion(),
                    response.getLatitudeSolicitud(),
                    response.getLongitudeSolicitud(),
                    response.getUserID(),

                    response.getNombreCompleto(),
                    response.getUrlFotoPerfil(),
                    response.getPuntuacion()
            ));
        }

        return solicitudes;
    }

    public interface HistorialCallback {
        void onSuccess(List<Solicitud> solicitudes);
        void onError(String errorMessage);
    }
}
