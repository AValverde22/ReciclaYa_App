package pe.reciclaya.app.domain.repository.recojo;

import pe.reciclaya.app.domain.repository.RepositoryCallback;
import pe.reciclaya.app.domain.model.recojo.*;

public interface RecojoRepository {
    RequestHandle getActive(int userId, RepositoryCallback<Recojo> callback);
    RequestHandle shareLocation(int requestId, int userId, LocationFix location, RepositoryCallback<Void> callback);
    RequestHandle confirm(int requestId, int userId, RepositoryCallback<Void> callback);
    RequestHandle cancel(int requestId, int userId, RepositoryCallback<Void> callback);
    RequestHandle rate(int requestId, int userId, int score, String comment, RepositoryCallback<Void> callback);
    RequestHandle getRoute(int requestId, int userId, RepositoryCallback<RutaRecojo> callback);
    void close();
}
