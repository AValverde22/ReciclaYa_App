package pe.reciclaya.app.ui.main.usuario.solicitud_list;

import pe.reciclaya.app.R;
import pe.reciclaya.app.domain.model.solicitud.Estado;
import pe.reciclaya.app.ui.main.usuario.tipo_residuo_list.TipoResiduoItem;

public class SolicitudFinalizada extends SolicitudItem {

    public SolicitudFinalizada(int id,
                              TipoResiduoItem tipoResiduo,
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

    @Override public String getEstado() { return Estado.FINALIZADA.getEstado(); }
    @Override public String getAntiguedad() { return dia + " " + horaString; }
    @Override public boolean isBtnCancelarEnabled() { return false; }
    @Override public boolean isBtnEditarEnabled() { return false; }
    @Override public int getColorFondo() { return R.color.azul_transparente; }
    @Override public int getColor() { return R.color.azul; }
}
