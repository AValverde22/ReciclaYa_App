package pe.reciclaya.app.ui.main.navigation;

import androidx.annotation.NonNull;

import pe.reciclaya.app.domain.model.user.UserRole;

public class MainManager {
    public static Class<?> crearMainFactory(@NonNull UserRole role) {
        FactoryMainActivity factoryMainActivity;
        switch(role) {
            case USUARIO: factoryMainActivity = new FactoryUsuarioActivity(); break;
            case RECICLADOR: factoryMainActivity = new FactoryRecicladorActivity(); break;
            default: factoryMainActivity = new FactoryDefaultActivity(); break;
        }

        return factoryMainActivity.crear();
    }
}
