package pe.reciclaya.app.domain.repository.user;

import pe.reciclaya.app.domain.model.user.Profile;
import pe.reciclaya.app.domain.repository.RepositoryCallback;

public interface ProfileRepository {
    Profile local();
    void load(RepositoryCallback<Profile> callback);
    void update(String fullName, String email, String profilePhotoUrl, RepositoryCallback<Profile> callback);
    void close();
}
