package pe.reciclaya.app.ui.main.usuario;
import pe.reciclaya.app.ui.main.recojo.FragmentRecojoMapa;
import pe.reciclaya.app.ui.main.recojo.RecojoMapPresentation;
import pe.reciclaya.app.ui.main.recojo.UserMapPresentation;
public class FragmentMainUsuarioMapa extends FragmentRecojoMapa {
    @Override protected RecojoMapPresentation presentation() { return new UserMapPresentation(); }
    public static FragmentMainUsuarioMapa newInstance(String param1, String param2) { return new FragmentMainUsuarioMapa(); }
}
