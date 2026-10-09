package pe.reciclaya.app.domain.repository.user;

import pe.reciclaya.app.domain.model.user.UserRole;

public interface LoginRepository {
    void login(String email, String password, RepositoryCallback<UserRole> callback);
}
