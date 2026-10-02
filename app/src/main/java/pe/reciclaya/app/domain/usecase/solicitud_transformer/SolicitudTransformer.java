package pe.reciclaya.app.domain.usecase.solicitud_transformer;

import java.util.ArrayList;
import java.util.List;

import pe.reciclaya.app.data.model.solicitud.get.response.GetResponse;
import pe.reciclaya.app.domain.model.Solicitud;

import pe.reciclaya.app.ui.main.usuario.tipo_residuo_list.FactoryPapel;
import pe.reciclaya.app.ui.main.usuario.tipo_residuo_list.FactoryPlastico;
import pe.reciclaya.app.ui.main.usuario.tipo_residuo_list.FactoryTipoResiduo;
import pe.reciclaya.app.ui.main.usuario.tipo_residuo_list.FactoryVidrio;

public class SolicitudTransformer {
    public static List<Solicitud> createSolicitudesList(List<GetResponse> responses) {
        List<Solicitud> solicitudes = new ArrayList<>();

        for(GetResponse response : responses){
            FactorySolicitud factorySolicitud;
            FactoryTipoResiduo factoryTipoResiduo;

            switch(response.getEstado()) {
                case "Disponible": factorySolicitud = new FactoryDisponible(); break;
                case "Cancelada": factorySolicitud = new FactoryCancelada(); break;
                default: factorySolicitud = new FactoryFinalizada(); break;
            }

            switch(response.getTipoResiduo()) {
                case "Plástico": factoryTipoResiduo = new FactoryPlastico(); break;
                case "Vidrio": factoryTipoResiduo = new FactoryVidrio(); break;
                default: factoryTipoResiduo = new FactoryPapel(); break;
            }

            solicitudes.add(factorySolicitud.crear(
                    response.getSolicitudID(),
                    factoryTipoResiduo.crear(),
                    response.getTamano(),
                    response.getDia(),
                    response.getHora(),
                    response.getDireccion(),
                    response.getLatitudeSolicitud(),
                    response.getLongitudeSolicitud(),
                    response.getUserID(),

                    response.getNombreCompleto(),
                    response.getUrlFotoPerfil(),
                    response.getPuntuacion()
            ));
        }

        return solicitudes;
    }
}
