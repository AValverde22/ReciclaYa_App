package pe.reciclaya.app.domain.repository.user;

import pe.reciclaya.app.domain.model.user.UserRole;

public interface SplashRepository {
    UserRole getSavedUser();
}