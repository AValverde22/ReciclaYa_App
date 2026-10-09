package pe.reciclaya.app.ui.main.tipo_residuo_list;

import pe.reciclaya.app.R;

public class Papel implements TipoResiduoItem {
    @Override
    public String getNombre() {
        return "Papel";
    }

    @Override
    public int getIcono() {
        return R.drawable.papel;
    }
}
