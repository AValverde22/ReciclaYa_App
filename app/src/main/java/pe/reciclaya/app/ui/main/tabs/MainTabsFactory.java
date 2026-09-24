package pe.reciclaya.app.ui.main.tabs;

import androidx.fragment.app.Fragment;

import pe.reciclaya.app.ui.main.perfil.FragmentMainPerfil;

public interface MainTabsFactory {
    Fragment crearPrimerFragment();
    Fragment crearSegundoFragment();
    Fragment crearTercerFragment();
    Fragment crearCuartoFragment();
}
