package pe.reciclaya.app.ui.main.reciclador;
import pe.reciclaya.app.ui.main.recojo.FragmentRecojoMapa;
import pe.reciclaya.app.ui.main.recojo.RecojoMapPresentation;
import pe.reciclaya.app.ui.main.recojo.RecyclerMapPresentation;
public class FragmentMainRecicladorMapa extends FragmentRecojoMapa {
    @Override protected RecojoMapPresentation presentation() { return new RecyclerMapPresentation(); }
    public static FragmentMainRecicladorMapa newInstance(String param1, String param2) { return new FragmentMainRecicladorMapa(); }
}
