package pe.reciclaya.app.ui.view.main.factory_method.fragment_perfil;

public class FactoryFragmentPerfilReciclador implements FactoryFragmentPerfil {
    @Override
    public FragmentMainPerfil crear() {
        return new FragmentMainPerfilReciclador();
    }
}
