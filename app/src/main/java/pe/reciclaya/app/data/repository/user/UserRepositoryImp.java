package pe.reciclaya.app.data.repository.user;

import android.content.Context;

import pe.reciclaya.app.data.local.AppPreferencesManager;
import pe.reciclaya.app.domain.model.user.User;
import pe.reciclaya.app.domain.model.user.UserRole;
import pe.reciclaya.app.domain.repository.user.UserRepository;

public class UserRepositoryImp implements UserRepository {
    private static AppPreferencesManager appPreferencesManager;
    private static User user;

    public UserRepositoryImp(Context context) {
        appPreferencesManager = AppPreferencesManager.getInstance(context);
        user = User.getInstance();
    }

    @Override public int getID() {return user.getID(); }
    @Override public String getFullName() { return user.getFullName(); }
    @Override public String getEmail() { return user.getEmail(); }
    @Override public UserRole getRole() { return user.getRole(); }
    @Override public String getProfilePhotoURL() { return user.getProfilePhotoURL(); }
    @Override public float getScore() { return user.getScore(); }
    @Override public void logout() {
        User.clearInstance();
        appPreferencesManager.cleanAll();
    }
}

