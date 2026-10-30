package pe.reciclaya.app.ui.main.perfil.mi_actividad_list;

import pe.reciclaya.app.R;

public class SeguridadCuenta implements MiActividad {
    @Override
    public String getActividad() {
        return "Seguridad de la cuenta";
    }

    @Override
    public String getMensajeActividad() {
        return "Autenticación de dos factores";
    }

    @Override
    public int getIconoActividad() {
        return R.drawable.candado;
    }
}
