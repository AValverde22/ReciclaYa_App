package pe.reciclaya.app.ui.main.tabs;

import androidx.fragment.app.Fragment;

import pe.reciclaya.app.R;
import pe.reciclaya.app.ui.main.perfil.FragmentMainPerfil;
import pe.reciclaya.app.ui.main.usuario.FragmentMainUsuarioHistorial;
import pe.reciclaya.app.ui.main.usuario.FragmentMainUsuarioMapa;
import pe.reciclaya.app.ui.main.usuario.FragmentMainUsuarioSolicitar;

public class UsuarioTabsAbstractFactory implements MainTabsAbstractFactory {
    private final TabData[] tabs;

    public UsuarioTabsAbstractFactory() {
        tabs = new TabData[] {
                new TabData(FragmentMainUsuarioMapa::new, "Mapa", R.drawable.mapa),
                new TabData(FragmentMainUsuarioSolicitar::new, "Solicitar", R.drawable.solicitar),
                new TabData(FragmentMainUsuarioHistorial::new, "Historial", R.drawable.historial),
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
