package pe.reciclaya.app.ui.view.main;

import pe.reciclaya.app.domain.singleton.User;

import pe.reciclaya.app.ui.view.main.factory_method.FactoryDefault;
import pe.reciclaya.app.ui.view.main.factory_method.FactoryMain;
import pe.reciclaya.app.ui.view.main.factory_method.FactoryReciclador;
import pe.reciclaya.app.ui.view.main.factory_method.FactoryUsuario;

public class MainManager {
    private static final User user = User.getInstance();

    public static Class<?> crearMainFactory() {
        FactoryMain factoryMain;
        switch(user.getRole()) {
            case "Usuario": factoryMain = new FactoryUsuario(); break;
            case "Reciclador": factoryMain = new FactoryReciclador(); break;
            default: factoryMain = new FactoryDefault(); break;
        }

        return factoryMain.crear();
    }
}
