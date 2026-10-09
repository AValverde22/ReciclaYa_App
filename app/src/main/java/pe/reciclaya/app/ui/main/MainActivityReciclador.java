package pe.reciclaya.app.ui.main;

import pe.reciclaya.app.ui.main.tabs.MainTabsAbstractFactory;
import pe.reciclaya.app.ui.main.tabs.RecicladorTabsAbstractFactory;

public class MainActivityReciclador extends MainActivity {
    @Override
    protected MainTabsAbstractFactory getMainTabFactory() {
        return new RecicladorTabsAbstractFactory();
    }
}