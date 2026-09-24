package pe.reciclaya.app.ui.view.main.manager;

import androidx.annotation.NonNull;

import pe.reciclaya.app.ui.view.main.factory_method.FactoryDefaultActivity;
import pe.reciclaya.app.ui.view.main.factory_method.FactoryMainActivity;
import pe.reciclaya.app.ui.view.main.factory_method.FactoryRecicladorActivity;
import pe.reciclaya.app.ui.view.main.factory_method.FactoryUsuarioActivity;

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
