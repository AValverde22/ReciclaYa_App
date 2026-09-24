package pe.reciclaya.app.ui.main.navigation;

import androidx.annotation.NonNull;

public class MainManager {
    public static Class<?> crearMainFactory(@NonNull String role) {
        FactoryMainActivity factoryMainActivity;
        switch(role) {
            case "Usuario": factoryMainActivity = new FactoryUsuarioActivity(); break;
            case "Reciclador": factoryMainActivity = new FactoryRecicladorActivity(); break;
            default: factoryMainActivity = new FactoryDefaultActivity(); break;
        }

        return factoryMainActivity.crear();
    }
}
