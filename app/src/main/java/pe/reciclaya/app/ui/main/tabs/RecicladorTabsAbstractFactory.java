package pe.reciclaya.app.ui.main.tabs;

import androidx.fragment.app.Fragment;

import pe.reciclaya.app.R;
import pe.reciclaya.app.ui.main.perfil.FragmentMainPerfil;
import pe.reciclaya.app.ui.main.reciclador.FragmentMainRecicladorEncargos;
import pe.reciclaya.app.ui.main.reciclador.FragmentMainRecicladorMapa;
import pe.reciclaya.app.ui.main.reciclador.FragmentMainRecicladorReciclar;

public class RecicladorTabsAbstractFactory implements MainTabsAbstractFactory {
    private final TabData[] tabs;

    public RecicladorTabsAbstractFactory() {
        tabs = new TabData[] {
                new TabData(FragmentMainRecicladorMapa::new, "Mapa", R.drawable.mapa),
                new TabData(FragmentMainRecicladorReciclar::new, "Reciclar", R.drawable.solicitar),
                new TabData(FragmentMainRecicladorEncargos::new, "Encargos", R.drawable.historial),
                new TabData(FragmentMainPerfil::new, "Perfil", R.drawable.perfil)
        };
    }

    @Override
    public Fragment crearFragment(int pos) {
        return tabs[pos].getFragmentCreator().get();
    }

    @Override
    public String getTabName(int pos) {
        return tabs[pos].getTabName();
    }

    @Override
    public int getTabIcon(int pos) {
        return tabs[pos].getTabIcon();
    }

    @Override
    public int getTabCount() {
        return tabs.length;
    }
}
