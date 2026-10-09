package pe.reciclaya.app.ui.view.tyc.factory_method;

import pe.reciclaya.app.R;

public class TerminosDeServicio implements TyC {
    @Override
    public int getTituloID() {
        return R.string.TerminosDeServicio;
    }

    @Override
    public int getMensajeID() {
        return R.string.MensajeTerminosDeServicio;
    }
}
