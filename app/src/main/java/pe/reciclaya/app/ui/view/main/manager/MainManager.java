package pe.reciclaya.app.ui.view.main.manager;

import androidx.annotation.NonNull;

import pe.reciclaya.app.domain.singleton.User;

import pe.reciclaya.app.ui.view.main.factory_method.main.FactoryDefaultActivity;
import pe.reciclaya.app.ui.view.main.factory_method.main.FactoryMainActivity;
import pe.reciclaya.app.ui.view.main.factory_method.main.FactoryRecicladorActivity;
import pe.reciclaya.app.ui.view.main.factory_method.main.FactoryUsuarioActivity;

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
