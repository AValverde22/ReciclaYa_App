package pe.reciclaya.app.ui.main.perfil.mi_actividad_list;

import pe.reciclaya.app.R;

public final class CalificacionesResenas implements MiActividad {
    @Override public String getActividad() { return "Calificaciones y reseñas"; }
    @Override public String getMensajeActividad() { return "Consulta tu reputación"; }
    @Override public int getIconoActividad() { return R.drawable.estrella; }
}
