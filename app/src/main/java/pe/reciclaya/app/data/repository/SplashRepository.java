package pe.reciclaya.app.data.repository;

import android.content.Context;

import pe.reciclaya.app.data.local.AppPreferencesManager;

public class SplashRepository {
    private final Context context;

    public SplashRepository(Context context) {
        this.context = context;
    }

    public String getSavedRole() {
        int id = AppPreferencesManager.getInt("id", context);

        if(id == -1) return null;
        else return AppPreferencesManager.getString("role", context);
    }
}
