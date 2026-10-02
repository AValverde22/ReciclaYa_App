package pe.reciclaya.app.ui.main.usuario.solicitud_list;

import pe.reciclaya.app.ui.main.usuario.tipo_residuo_list.TipoResiduo;

public class FactoryFinalizada implements FactorySolicitud {
    @Override
    public Solicitud crear(int id,
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
        return new SolicitudFinalizada(
                id,
                tipoResiduo,
                tamano,
                dia,
                hora,
                direccion,
                latitude,
                longitude,

                userID,
                nombreCompleto,
                urlFotoPerfil,
                puntuacion
        );
    }
}
