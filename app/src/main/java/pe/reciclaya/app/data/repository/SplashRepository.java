package pe.reciclaya.app.data.repository;

import android.content.Context;

import pe.reciclaya.app.data.local.AppPreferencesManager;
import pe.reciclaya.app.domain.singleton.User;

public class SplashRepository {
    private static AppPreferencesManager appPreferencesManager;
    private static User user;

    public SplashRepository(Context context) {
        appPreferencesManager = AppPreferencesManager.getInstance(context);
        user = User.getInstance();
    }

    public String getSavedUser() {
        int id = appPreferencesManager.getInt("id");
        if(id == -1) return "default";

        String fullName = appPreferencesManager.getString("fullName");
        String email = appPreferencesManager.getString("email");
        String role = appPreferencesManager.getString("role");

        user.setID(id);
        user.setFullName(fullName);
        user.setEmail(email);
        user.setRole(role);

        return role;
    }
}
