package pe.reciclaya.app.ui.main.navigation;

import pe.reciclaya.app.ui.main.MainActivityUsuario;

public class FactoryUsuarioActivity implements FactoryMainActivity {
    @Override
    public Class<?> crear() {
        return MainActivityUsuario.class;
    }
}
