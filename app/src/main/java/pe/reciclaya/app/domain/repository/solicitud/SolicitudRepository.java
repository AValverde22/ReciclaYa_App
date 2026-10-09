package pe.reciclaya.app.domain.repository.solicitud;

import java.util.List;

import pe.reciclaya.app.domain.model.solicitud.Solicitud;
import pe.reciclaya.app.domain.model.solicitud.SolicitudNueva;
import pe.reciclaya.app.domain.repository.RepositoryCallback;

public interface SolicitudRepository {
    void createSolicitud(SolicitudNueva nuevaSolicitud,
                         RepositoryCallback<Void> callback);

    void getSolicitudes(int id, RepositoryCallback<List<Solicitud>> callback);

}
