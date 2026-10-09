package pe.reciclaya.app.data.repository.main;

import android.content.Context;

import pe.reciclaya.app.data.local.AppPreferencesManager;
import pe.reciclaya.app.domain.singleton.User;

public class PerfilRepository {
    private static AppPreferencesManager appPreferencesManager;
    private static User user;

    public PerfilRepository(Context context) {
        appPreferencesManager = AppPreferencesManager.getInstance(context);
        user = User.getInstance();
    }

    public String getFullName() { return user.getFullName(); }
    public String getEmail() { return user.getEmail(); }
    public String getRole() { return user.getRole(); }

    public void cerrarSesion() {
        appPreferencesManager.putInt("id", -1);
        appPreferencesManager.putString("fullName", null);
        appPreferencesManager.putString("email", null);
        appPreferencesManager.putString("role", null);
    }
}
