package pe.reciclaya.app.ui.view.main.factory_method.main;

import pe.reciclaya.app.ui.view.main.abstract_factory.MainTabsFactory;
import pe.reciclaya.app.ui.view.main.abstract_factory.RecicladorTabsFactory;

public class RecicladorMainActivity extends MainActivity {
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