package pe.reciclaya.app.ui.view.main.abstract_factory;

import androidx.fragment.app.Fragment;

import pe.reciclaya.app.ui.view.main.factory_method.fragment_perfil.FactoryFragmentPerfil;
import pe.reciclaya.app.ui.view.main.factory_method.fragment_perfil.FactoryFragmentPerfilReciclador;
import pe.reciclaya.app.ui.view.main.factory_method.fragment_perfil.FactoryFragmentPerfilUsuario;
import pe.reciclaya.app.ui.view.main.factory_method.fragment_perfil.FragmentMainPerfil;
import pe.reciclaya.app.ui.view.main.fragments.usuario.FragmentMainHistorialUsuario;
import pe.reciclaya.app.ui.view.main.fragments.usuario.FragmentMainMapaUsuario;
import pe.reciclaya.app.ui.view.main.factory_method.fragment_perfil.FragmentMainPerfilUsuario;
import pe.reciclaya.app.ui.view.main.fragments.usuario.FragmentMainSolicitar;

public class UsuarioTabsFactory implements MainTabsFactory {
    @Override
    public Fragment crearPrimerFragment() {
        return new FragmentMainMapaUsuario();
    }

    @Override
    public Fragment crearSegundoFragment() {
        return new FragmentMainSolicitar();
    }

    @Override
    public Fragment crearTercerFragment() {
        return new FragmentMainHistorialUsuario();
    }

    @Override
    public FragmentMainPerfil crearCuartoFragment() {
        FactoryFragmentPerfil factoryFragmentPerfil = new FactoryFragmentPerfilUsuario();
        return factoryFragmentPerfil.crear();
    }
}
