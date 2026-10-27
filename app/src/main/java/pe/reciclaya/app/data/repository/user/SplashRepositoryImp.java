package pe.reciclaya.app.data.repository.user;

import android.content.Context;

import pe.reciclaya.app.data.local.AppPreferencesManager;
import pe.reciclaya.app.data.local.SaveUser;
import pe.reciclaya.app.domain.model.user.User;
import pe.reciclaya.app.domain.model.user.UserRole;
import pe.reciclaya.app.domain.repository.user.SplashRepository;

public class SplashRepositoryImp implements SplashRepository {
    private static AppPreferencesManager appPreferencesManager;
    private static User user;

    public SplashRepositoryImp(Context context) {
        appPreferencesManager = AppPreferencesManager.getInstance(context);
        user = User.getInstance();
    }

    @Override
    public UserRole getSavedUser() {
        SaveUser.loadData(appPreferencesManager, user);
        return (user.getRole() != null) ? user.getRole() : UserRole.DEFAULT;
    }
}
