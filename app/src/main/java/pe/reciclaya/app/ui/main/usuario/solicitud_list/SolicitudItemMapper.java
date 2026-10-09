package pe.reciclaya.app.ui.main.usuario.solicitud_list;

import java.util.ArrayList;
import java.util.List;

import pe.reciclaya.app.domain.model.solicitud.Solicitud;
import pe.reciclaya.app.domain.model.user.UserSolicitud;
import pe.reciclaya.app.ui.main.usuario.tipo_residuo_list.FactoryTipoResiduo;
import pe.reciclaya.app.ui.main.usuario.tipo_residuo_list.TipoResiduoMapper;

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

    public static List<SolicitudItem> mapSolicitudesItem(List<Solicitud> solicitudes) {
        List<SolicitudItem> solicitudItemList = new ArrayList<>();
        for(Solicitud solicitud : solicitudes)
            solicitudItemList.add(toUI(solicitud));

        return solicitudItemList;
    }
}
