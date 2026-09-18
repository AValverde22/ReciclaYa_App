package pe.reciclaya.app.ui.view.main.abstract_factory;

import androidx.fragment.app.Fragment;

import pe.reciclaya.app.ui.view.main.factory_method.fragment_perfil.FragmentMainPerfil;

public interface MainTabsFactory {
    Fragment crearPrimerFragment();
    Fragment crearSegundoFragment();
    Fragment crearTercerFragment();
    FragmentMainPerfil crearCuartoFragment();
}
