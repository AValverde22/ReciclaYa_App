package pe.reciclaya.app.ui.main.usuario.solicitud_list;

import pe.reciclaya.app.ui.main.usuario.tipo_residuo_list.TipoResiduo;

public interface FactorySolicitud {
    Solicitud crear(int id,
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
                    float puntuacion);
}
