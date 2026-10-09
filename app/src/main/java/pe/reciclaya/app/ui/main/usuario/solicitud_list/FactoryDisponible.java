package pe.reciclaya.app.ui.main.usuario.solicitud_list;

import pe.reciclaya.app.ui.main.usuario.tipo_residuo_list.TipoResiduoItem;

public class FactoryDisponible implements FactorySolicitud {
    @Override
    public SolicitudItem crear(int id,
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
        return new SolicitudDisponible(
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
