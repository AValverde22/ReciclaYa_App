package pe.reciclaya.app.ui.view.main.abstract_factory;

import androidx.fragment.app.Fragment;

import pe.reciclaya.app.ui.view.main.factory_method.fragment_perfil.FactoryFragmentPerfil;
import pe.reciclaya.app.ui.view.main.factory_method.fragment_perfil.FactoryFragmentPerfilReciclador;
import pe.reciclaya.app.ui.view.main.factory_method.fragment_perfil.FragmentMainPerfil;
import pe.reciclaya.app.ui.view.main.fragments.reciclador.FragmentMainEncargosReciclador;
import pe.reciclaya.app.ui.view.main.fragments.reciclador.FragmentMainMapaReciclador;
import pe.reciclaya.app.ui.view.main.factory_method.fragment_perfil.FragmentMainPerfilReciclador;
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
