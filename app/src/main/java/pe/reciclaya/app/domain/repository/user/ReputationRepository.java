package pe.reciclaya.app.domain.repository.user;

import pe.reciclaya.app.domain.model.reputation.Reputation;
import pe.reciclaya.app.domain.repository.RepositoryCallback;

public interface ReputationRepository {
    void get(int userId, RepositoryCallback<Reputation> callback);
    void close();
}
