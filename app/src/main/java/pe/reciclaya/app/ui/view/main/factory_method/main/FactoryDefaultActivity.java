package pe.reciclaya.app.ui.view.main.factory_method.main;

import pe.reciclaya.app.ui.view.login.LoginActivity;

public class FactoryDefaultActivity implements FactoryMainActivity {
    @Override
    public Class<?> crear() { return LoginActivity.class; }
}
