package pe.reciclaya.app.ui.main.tipo_residuo_list;

import pe.reciclaya.app.R;

public class Vidrio implements TipoResiduoItem {
    @Override
    public String getNombre() {
        return "Vidrio";
    }

    @Override
    public int getIcono() {
        return R.drawable.vidrio;
    }
}
