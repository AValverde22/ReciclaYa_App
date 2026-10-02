package pe.reciclaya.app.domain.repository;

import pe.reciclaya.app.data.remote.BackendClient;
import pe.reciclaya.app.data.remote.SolicitudService;
import pe.reciclaya.app.domain.model.Solicitud;
import pe.reciclaya.app.domain.model.User;

public abstract class SolicitudRepository {
    protected final SolicitudService apiService;
    protected static User user;

    public SolicitudRepository() {
        apiService = BackendClient.getSolicitudService();
        user = User.getInstance();
    }

    public abstract void manageSolicitud(Solicitud solicitud, SolicitudCallback callback);

    public interface SolicitudCallback {
        void onSuccess();
        void onError(String errorMessage);
    }
}
