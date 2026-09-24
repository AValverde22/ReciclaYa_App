package pe.reciclaya.app.ui.view.main.abstract_factory;

import androidx.fragment.app.Fragment;

import pe.reciclaya.app.ui.view.main.fragments.perfil.factory_method.FactoryFragmentPerfil;
import pe.reciclaya.app.ui.view.main.fragments.perfil.factory_method.FactoryFragmentPerfilReciclador;
import pe.reciclaya.app.ui.view.main.fragments.perfil.factory_method.FragmentMainPerfil;
import pe.reciclaya.app.ui.view.main.fragments.reciclador.FragmentMainEncargosReciclador;
import pe.reciclaya.app.ui.view.main.fragments.reciclador.FragmentMainMapaReciclador;
import pe.reciclaya.app.ui.view.main.fragments.reciclador.FragmentMainReciclar;

public class RecicladorTabsFactory implements MainTabsFactory {
    @Override
    public Fragment crearPrimerFragment() {
        return new FragmentMainMapaReciclador();
    }

    @Override
    public Fragment crearSegundoFragment() {
        return new FragmentMainReciclar();
    }

    @Override
    public Fragment crearTercerFragment() {
        return new FragmentMainEncargosReciclador();
    }

    @Override
    public FragmentMainPerfil crearCuartoFragment() {
        FactoryFragmentPerfil factoryFragmentPerfil = new FactoryFragmentPerfilReciclador();
        return factoryFragmentPerfil.crear();
    }
}
