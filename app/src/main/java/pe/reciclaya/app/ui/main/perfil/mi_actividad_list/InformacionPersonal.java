package pe.reciclaya.app.ui.main.perfil.mi_actividad_list;

import pe.reciclaya.app.R;

public class InformacionPersonal implements MiActividad {
    @Override
    public String getActividad() {
        return "Información Personal";
    }

    @Override
    public String getMensajeActividad() {
        return "Actualizar datos y foto";
    }

    @Override
    public int getIconoActividad() {
        return R.drawable.perfil;
    }
}
