package pe.reciclaya.app.ui.main;

import pe.reciclaya.app.ui.main.tabs.MainTabsFactory;
import pe.reciclaya.app.ui.main.tabs.RecicladorTabsFactory;

public class MainActivityReciclador extends MainActivity {
    @Override
    protected MainTabsFactory getMainTabFactory() {
        return new RecicladorTabsFactory();
    }

    @Override
    protected void modificarNombresTabLayout() {
        tabLayout.getTabAt(1).setText("Reciclar");
        tabLayout.getTabAt(2).setText("Encargos");
    }
}