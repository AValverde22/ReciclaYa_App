package pe.reciclaya.app.ui.main.reciclador;
import pe.reciclaya.app.ui.main.recojo.FragmentRecojoMapa;
public class FragmentMainRecicladorMapa extends FragmentRecojoMapa {
    @Override protected boolean isRecycler() { return true; }
    public static FragmentMainRecicladorMapa newInstance(String param1, String param2) { return new FragmentMainRecicladorMapa(); }
}
