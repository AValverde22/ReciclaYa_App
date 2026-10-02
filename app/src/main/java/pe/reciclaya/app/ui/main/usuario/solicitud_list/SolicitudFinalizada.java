package pe.reciclaya.app.ui.main.usuario.solicitud_list;

import pe.reciclaya.app.ui.main.usuario.tipo_residuo_list.TipoResiduo;

public class SolicitudFinalizada extends Solicitud {

    public SolicitudFinalizada(int id,
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

    @Override public String getNombreUsuario() { return nombreUsuario; }
    @Override public String getAntiguedad() { return dia; }
    @Override public boolean isBtnCancelarEnabled() { return false; }
    @Override public boolean isBtnEditarEnabled() { return false; }
    @Override public int getColorFondo() { return 0; }
}
