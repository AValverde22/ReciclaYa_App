package pe.reciclaya.app.data.factory_method.tyc;

import pe.reciclaya.app.R;

public class PoliticaDePrivacidad implements TyC {
    @Override
    public int getTituloID() {
        return R.string.PoliticaDePrivacidad;
    }

    @Override
    public int getMensajeID() {
        return R.string.MensajePoliticaDePrivacidad;
    }
}
