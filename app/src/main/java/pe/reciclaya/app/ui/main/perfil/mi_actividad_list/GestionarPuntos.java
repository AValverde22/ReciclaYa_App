package pe.reciclaya.app.ui.main.perfil.mi_actividad_list;

import pe.reciclaya.app.R;

public class GestionarPuntos implements MiActividad {
    @Override
    public String getActividad() {
        return "Gestionar Puntos";
    }

    @Override
    public String getMensajeActividad() {
        return "Gestionar recompensas";
    }

    @Override
    public int getIconoActividad() {
        return R.drawable.billetera;
    }
}
