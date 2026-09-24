package pe.reciclaya.app.ui.main.tabs;

import androidx.fragment.app.Fragment;

import pe.reciclaya.app.ui.main.perfil.FragmentMainPerfil;
import pe.reciclaya.app.ui.main.usuario.FragmentMainUsuarioHistorial;
import pe.reciclaya.app.ui.main.usuario.FragmentMainUsuarioMapa;
import pe.reciclaya.app.ui.main.usuario.FragmentMainUsuarioSolicitar;

public class UsuarioTabsFactory implements MainTabsFactory {
    @Override
    public Fragment crearPrimerFragment() {
        return new FragmentMainUsuarioMapa();
    }

    @Override
    public Fragment crearSegundoFragment() {
        return new FragmentMainUsuarioSolicitar();
    }

    @Override
    public Fragment crearTercerFragment() {
        return new FragmentMainUsuarioHistorial();
    }

    @Override
    public Fragment crearCuartoFragment() { return new FragmentMainPerfil(); }
}
