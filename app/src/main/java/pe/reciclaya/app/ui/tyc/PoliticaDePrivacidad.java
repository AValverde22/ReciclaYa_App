package pe.reciclaya.app.ui.tyc;

import pe.reciclaya.app.R;

public class PoliticaDePrivacidad extends TyCActivity {
    @Override
    protected void inicializarContenido() {
        TVTitulo.setText(R.string.PoliticaDePrivacidad);
        TVMensaje.setText(R.string.MensajePoliticaDePrivacidad);
    }
}
