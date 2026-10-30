package pe.reciclaya.app.domain.repository.user;

import pe.reciclaya.app.domain.model.user.LoginResult;
import pe.reciclaya.app.domain.model.user.UserRole;
import pe.reciclaya.app.domain.repository.RepositoryCallback;

public interface LoginRepository {
    void login(String email, String password, RepositoryCallback<LoginResult> callback);
    void verifyCode(String email, String challenge, String code, RepositoryCallback<UserRole> callback);
    void resendCode(String email, String challenge, RepositoryCallback<Void> callback);
}
