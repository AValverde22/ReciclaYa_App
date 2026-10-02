package pe.reciclaya.app.domain.usecase.solicitud_transformer;

import pe.reciclaya.app.domain.model.Solicitud;
import pe.reciclaya.app.ui.main.usuario.tipo_residuo_list.TipoResiduo;

public class FactoryCancelada implements FactorySolicitud {
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
        return new SolicitudCancelada(
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
