package pe.reciclaya.app.ui.main.usuario.solicitud_list;

import java.util.Calendar;

import pe.reciclaya.app.R;
import pe.reciclaya.app.ui.main.usuario.tipo_residuo_list.TipoResiduo;

public class SolicitudDisponible extends Solicitud {

    public SolicitudDisponible(int id,
                              TipoResiduo tipoResiduo,
                              String tamano,
                              String dia,
                              String hora,
                              String direccion,
                              double latitude,
                              double longitude,

                              int userID,
                              String nombreCompleto,
                              String urlFotoPerfil,
                              float puntuacion
    ) {
        super(id, tipoResiduo, tamano, dia, hora, direccion, latitude, longitude, userID, nombreCompleto, urlFotoPerfil, puntuacion);
    }

    @Override
    public String getNombreUsuario() { return "Pendiente..."; }

    @Override
    public String getAntiguedad() {
        String[] splitDay = dia.split("/");
        String[] splitTime = hora.split(":");

        Calendar horaActual = Calendar.getInstance();
        horaActual.set(Calendar.SECOND, 0);
        horaActual.set(Calendar.MILLISECOND, 0);

        Calendar horaAcordada = Calendar.getInstance();
        horaAcordada.set(
                Integer.parseInt(splitDay[2]),
                Integer.parseInt(splitDay[1]) - 1,
                Integer.parseInt(splitDay[0]),
                Integer.parseInt(splitTime[0]),
                Integer.parseInt(splitTime[1])
        );

        long lHActual = horaActual.getTimeInMillis();
        long lHAcordada = horaAcordada.getTimeInMillis();

        long diferencia = lHAcordada - lHActual;
        float antiguedad = diferencia * 1.0f / (60 * 60 * 1000);

        int hora = (int) antiguedad;
        int minutes = Math.round((antiguedad - hora) * 60);

        // Hace X h Y min
        return "En " + ((hora > 0) ? hora + " h " : "") + ((minutes > 0) ? minutes + " min" : "");
    }

    @Override public boolean isBtnCancelarEnabled() { return true; }
    @Override public boolean isBtnEditarEnabled() { return true; }
    @Override public int getColorFondo() { return R.color.verde_transparente; }
    @Override public int getColor() { return R.color.verde; }
}
