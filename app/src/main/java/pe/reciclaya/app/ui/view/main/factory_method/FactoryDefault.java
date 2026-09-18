package pe.reciclaya.app.ui.view.main.factory_method;

import pe.reciclaya.app.ui.view.login.LoginActivity;

public class FactoryDefault implements FactoryMain {
    @Override
    public Class<?> crear() { return LoginActivity.class; }
}
