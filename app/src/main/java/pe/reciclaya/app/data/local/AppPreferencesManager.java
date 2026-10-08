package pe.reciclaya.app.data.local;

import android.content.Context;
import android.content.SharedPreferences;

import androidx.annotation.NonNull;

import pe.reciclaya.app.domain.model.user.User;

public class AppPreferencesManager {
    private static AppPreferencesManager appPreferencesManager;
    private SharedPreferences sharedPreferences;

    private AppPreferencesManager() {}
    private AppPreferencesManager(Context context) {
        sharedPreferences = context.getSharedPreferences(context.getPackageName(), Context.MODE_PRIVATE);
    }

    public static synchronized AppPreferencesManager getInstance(@NonNull Context context) {
        if(appPreferencesManager == null)
            appPreferencesManager = new AppPreferencesManager(context);

        return appPreferencesManager;
    }

    public int getInt(String key) { return sharedPreferences.getInt(key, -1); }
    public float getFloat(String key) { return sharedPreferences.getFloat(key, -1);}
    public String getString(String key) { return sharedPreferences.getString(key, null); }

    public void putInt(String key, int value) { sharedPreferences.edit().putInt(key, value).apply(); }
    public void putFloat(String key, float value) { sharedPreferences.edit().putFloat(key, value).apply(); }
    public void putString(String key, String value) { sharedPreferences.edit().putString(key, value).apply(); }

    public void cleanAll() { sharedPreferences.edit().clear().apply(); }
}
