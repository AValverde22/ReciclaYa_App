package pe.reciclaya.app.ui.main.perfil.mi_actividad_list;

import pe.reciclaya.app.R;

public class HistorialSolicitudes implements MiActividad {
    @Override
    public String getActividad() {
        return "Historial de Solicitudes";
    }

    @Override
    public String getMensajeActividad() {
        return "Ver tus solicitudes pasadas";
    }

    @Override
    public int getIconoActividad() {
        return R.drawable.recientes;
    }
}
