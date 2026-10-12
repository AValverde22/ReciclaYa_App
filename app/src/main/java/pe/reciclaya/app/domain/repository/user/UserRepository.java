package pe.reciclaya.app.domain.repository.user;

import pe.reciclaya.app.domain.model.user.User;
import pe.reciclaya.app.domain.model.user.UserRole;

public interface UserRepository {
    int getID();
    String getFullName();
    String getEmail();
    UserRole getRole();
    String getProfilePhotoURL();
    float getScore();
    void logout();
}
