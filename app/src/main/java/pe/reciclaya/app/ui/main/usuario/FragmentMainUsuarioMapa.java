package pe.reciclaya.app.ui.main.usuario;
import pe.reciclaya.app.ui.main.recojo.FragmentRecojoMapa;
public class FragmentMainUsuarioMapa extends FragmentRecojoMapa {
    @Override protected boolean isRecycler() { return false; }
    public static FragmentMainUsuarioMapa newInstance(String param1, String param2) { return new FragmentMainUsuarioMapa(); }
}
