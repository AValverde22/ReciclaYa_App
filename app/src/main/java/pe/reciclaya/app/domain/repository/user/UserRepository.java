package pe.reciclaya.app.domain.repository.user;

import pe.reciclaya.app.domain.model.user.UserRole;

public interface UserRepository {
    String getFullName();
    String getEmail();
    UserRole getRole();
    String getProfilePhotoURL();
    float getScore();
    void logout();
}
