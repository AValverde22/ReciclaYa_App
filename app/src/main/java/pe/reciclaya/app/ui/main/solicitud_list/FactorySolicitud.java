package pe.reciclaya.app.ui.main.solicitud_list;

import pe.reciclaya.app.ui.main.tipo_residuo_list.TipoResiduoItem;

public interface FactorySolicitud {
    SolicitudItem crear(int id,
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
                    float puntuacion);
}
