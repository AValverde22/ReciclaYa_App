package pe.reciclaya.app.domain.repository.solicitud;

import java.util.List;

import pe.reciclaya.app.domain.model.solicitud.Solicitud;
import pe.reciclaya.app.domain.model.solicitud.SolicitudActualizada;
import pe.reciclaya.app.domain.model.solicitud.SolicitudFiltros;
import pe.reciclaya.app.domain.model.solicitud.SolicitudNueva;
import pe.reciclaya.app.domain.repository.RepositoryCallback;

public interface SolicitudRepository {
    void createSolicitud(SolicitudNueva nuevaSolicitud,
                         RepositoryCallback<Void> callback);

    void getSolicitudesCreadas(int id,
                               RepositoryCallback<List<Solicitud>> callback);

    void getSolicitudesAceptadas(int id,
                                 RepositoryCallback<List<Solicitud>> callback);

    void getSolicitudesDisponibles(SolicitudFiltros solicitudFiltros,
                                   RepositoryCallback<List<Solicitud>> callback);

    void updateSolicitud(SolicitudActualizada solicitudActualizada,
                         RepositoryCallback<Void> callback);

    void cancelSolicitud(int solicitudID,
                         RepositoryCallback<Void> callback);

    void acceptSolicitud(int solicitudID,
                         int recicladorID,
                         RepositoryCallback<Void> callback);
}
