package pe.reciclaya.app.ui.main.usuario.tipo_residuo_list;

import pe.reciclaya.app.R;

public class Plastico implements TipoResiduoItem {
    @Override
    public String getNombre() {
        return "Plástico";
    }

    @Override
    public int getIcono() {
        return R.drawable.plastico;
    }
}
