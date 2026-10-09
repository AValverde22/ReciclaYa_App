package pe.reciclaya.app.domain.repository.user;

import pe.reciclaya.app.domain.model.user.UserRole;

public interface RegisterRepository {
    void validateEmail(String email, RepositoryCallback<Void> callback);
    void registerUser(String fullName, String email, String password, UserRole role, RepositoryCallback<UserRole> callback);
}
