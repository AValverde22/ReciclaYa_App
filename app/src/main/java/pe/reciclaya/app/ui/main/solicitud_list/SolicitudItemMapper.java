package pe.reciclaya.app.ui.main.solicitud_list;

import java.util.ArrayList;
import java.util.List;

import pe.reciclaya.app.domain.model.solicitud.Solicitud;
import pe.reciclaya.app.domain.model.solicitud.SolicitudActualizada;
import pe.reciclaya.app.domain.model.solicitud.Tamano;
import pe.reciclaya.app.domain.model.solicitud.TipoResiduo;
import pe.reciclaya.app.domain.model.user.UserSolicitud;
import pe.reciclaya.app.ui.main.tipo_residuo_list.FactoryTipoResiduo;
import pe.reciclaya.app.ui.main.tipo_residuo_list.TipoResiduoMapper;

public class SolicitudItemMapper {
    private SolicitudItemMapper() {}

    public static SolicitudItem toUI(Solicitud solicitud){
        FactorySolicitud factorySolicitud;

        switch(solicitud.getEstado()) {
            case CANCELADA: factorySolicitud = new FactoryCancelada(); break;
            case DISPONIBLE: factorySolicitud = new FactoryDisponible(); break;
            default: factorySolicitud = new FactoryFinalizada(); break;
        }

        FactoryTipoResiduo factoryTipoResiduo = TipoResiduoMapper.mapTipoResiduo(solicitud.getTipoResiduo());
        UserSolicitud user = solicitud.getUser();

        return factorySolicitud.crear(
                solicitud.getID(),
                factoryTipoResiduo.crear(),
                solicitud.getTamanoString(),
                solicitud.getFecha(),
                solicitud.getHora(),
                solicitud.getDireccion(),
                solicitud.getLatitude(),
                solicitud.getLongitude(),

                (user == null) ? -1 : user.getID(),
                (user == null) ? "Pendiente..." : user.getFullName(),
                (user == null) ? "" : user.getProfilePhotoURL(),
                (user == null) ? 0f : user.getScore()
        );
    }

    public static List<SolicitudItem> allToUI(List<Solicitud> solicitudes) {
        List<SolicitudItem> solicitudItemList = new ArrayList<>();
        for(Solicitud solicitud : solicitudes)
            solicitudItemList.add(toUI(solicitud));

        return solicitudItemList;
    }

    public static SolicitudActualizada toDomain(SolicitudItem solicitudItem) {
        return new SolicitudActualizada(
                solicitudItem.getSolicitudID(),
                TipoResiduo.fromString(solicitudItem.getTipoResiduoString()),
                Tamano.fromString(solicitudItem.getTamano()),
                solicitudItem.getDia(),
                solicitudItem.getHora(),
                solicitudItem.getDireccion(),
                solicitudItem.getLatitude(),
                solicitudItem.getLongitude()
        );
    }
}
