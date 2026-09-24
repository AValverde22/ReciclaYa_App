package pe.reciclaya.app.ui.main;

import pe.reciclaya.app.ui.main.tabs.MainTabsFactory;
import pe.reciclaya.app.ui.main.tabs.UsuarioTabsFactory;

public class MainActivityUsuario extends MainActivity {
    @Override
    protected MainTabsFactory getMainTabFactory() {
        return new UsuarioTabsFactory();
    }

    @Override
    protected void modificarNombresTabLayout() {
        tabLayout.getTabAt(1).setText("Solicitar");
        tabLayout.getTabAt(2).setText("Historial");
    }
}