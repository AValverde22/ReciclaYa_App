package pe.reciclaya.app.domain.repository.user;

import pe.reciclaya.app.domain.model.user.UserRole;
import pe.reciclaya.app.domain.repository.RepositoryCallback;

public interface LoginRepository {
    void login(String email, String password, RepositoryCallback<UserRole> callback);
}
