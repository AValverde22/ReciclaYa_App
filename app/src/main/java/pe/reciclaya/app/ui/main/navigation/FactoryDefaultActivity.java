package pe.reciclaya.app.ui.main.navigation;

import pe.reciclaya.app.ui.auth.login.LoginActivity;

public class FactoryDefaultActivity implements FactoryMainActivity {
    @Override
    public Class<?> crear() { return LoginActivity.class; }
}
