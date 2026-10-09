package pe.reciclaya.app.ui.main.navigation;

import pe.reciclaya.app.ui.main.MainActivityReciclador;

public class FactoryRecicladorActivity implements FactoryMainActivity {
    @Override
    public Class<?> crear() { return MainActivityReciclador.class; }
}
