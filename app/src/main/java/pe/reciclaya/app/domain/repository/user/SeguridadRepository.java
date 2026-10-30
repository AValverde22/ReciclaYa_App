package pe.reciclaya.app.domain.repository.user;

import pe.reciclaya.app.domain.repository.RepositoryCallback;

public interface SeguridadRepository {
    String getEmail();
    void getTwoFactor(RepositoryCallback<Boolean> callback);
    void updateTwoFactor(boolean enabled, String password, RepositoryCallback<Boolean> callback);
}
