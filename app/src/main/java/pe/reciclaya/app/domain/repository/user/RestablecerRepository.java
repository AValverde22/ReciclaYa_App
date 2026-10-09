package pe.reciclaya.app.domain.repository.user;

import pe.reciclaya.app.domain.model.user.UserRole;
import pe.reciclaya.app.domain.repository.RepositoryCallback;

public interface RestablecerRepository {
    void sendEmail(String email, RepositoryCallback<Void> callback);
    void compareCode(String email, int codigo, RepositoryCallback<Void> callback);
    void resetUser(String email, String password, RepositoryCallback<UserRole> callback);
}
