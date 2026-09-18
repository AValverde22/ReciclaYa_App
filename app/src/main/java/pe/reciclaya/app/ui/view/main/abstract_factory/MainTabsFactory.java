package pe.reciclaya.app.ui.view.main.abstract_factory;

import androidx.fragment.app.Fragment;

public interface MainTabsFactory {
    Fragment crearPrimerFragment();
    Fragment crearSegundoFragment();
    Fragment crearTercerFragment();
    Fragment crearCuartoFragment();
}
