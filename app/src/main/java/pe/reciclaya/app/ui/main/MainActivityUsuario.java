package pe.reciclaya.app.ui.main;

import pe.reciclaya.app.ui.main.tabs.MainTabsAbstractFactory;
import pe.reciclaya.app.ui.main.tabs.UsuarioTabsAbstractFactory;

public class MainActivityUsuario extends MainActivity {
    @Override
    protected MainTabsAbstractFactory getMainTabFactory() {
        return new UsuarioTabsAbstractFactory();
    }
}