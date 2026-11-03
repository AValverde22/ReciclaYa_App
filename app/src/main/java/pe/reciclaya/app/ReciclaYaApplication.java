package pe.reciclaya.app;

import android.app.Application;

import pe.reciclaya.app.data.local.AppPreferencesManager;
import pe.reciclaya.app.data.local.SaveUser;
import pe.reciclaya.app.domain.model.user.User;

public class ReciclaYaApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        // Android puede restaurar cualquier actividad sin pasar por Splash.
        SaveUser.loadData(AppPreferencesManager.getInstance(this), User.getInstance());
    }
}
