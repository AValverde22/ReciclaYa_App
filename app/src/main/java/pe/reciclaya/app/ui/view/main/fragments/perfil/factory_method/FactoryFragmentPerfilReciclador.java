package pe.reciclaya.app.ui.view.main.fragments.perfil.factory_method;

public class FactoryFragmentPerfilReciclador implements FactoryFragmentPerfil {
    @Override
    public FragmentMainPerfil crear() {
        return new FragmentMainPerfilReciclador();
    }
}
