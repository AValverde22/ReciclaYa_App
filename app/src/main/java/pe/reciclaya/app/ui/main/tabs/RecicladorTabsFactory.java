package pe.reciclaya.app.ui.main.tabs;

import androidx.fragment.app.Fragment;

import pe.reciclaya.app.ui.main.perfil.FragmentMainPerfil;
import pe.reciclaya.app.ui.main.reciclador.FragmentMainRecicladorEncargos;
import pe.reciclaya.app.ui.main.reciclador.FragmentMainRecicladorMapa;
import pe.reciclaya.app.ui.main.reciclador.FragmentMainRecicladorReciclar;

public class RecicladorTabsFactory implements MainTabsFactory {
    @Override
    public Fragment crearPrimerFragment() {
        return new FragmentMainRecicladorMapa();
    }

    @Override
    public Fragment crearSegundoFragment() {
        return new FragmentMainRecicladorReciclar();
    }

    @Override
    public Fragment crearTercerFragment() {
        return new FragmentMainRecicladorEncargos();
    }

    @Override
    public Fragment crearCuartoFragment() { return new FragmentMainPerfil(); }
}
