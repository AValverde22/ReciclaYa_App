package pe.reciclaya.app.data.mapper;

import java.util.ArrayList;
import java.util.List;

import pe.reciclaya.app.data.model.solicitud.request.SolicitudRequest;
import pe.reciclaya.app.data.model.solicitud.request.UpdateRequest;
import pe.reciclaya.app.data.model.solicitud.response.SolicitudResponse;
import pe.reciclaya.app.domain.model.solicitud.Estado;
import pe.reciclaya.app.domain.model.solicitud.Solicitud;
import pe.reciclaya.app.domain.model.solicitud.SolicitudActualizada;
import pe.reciclaya.app.domain.model.solicitud.SolicitudNueva;
import pe.reciclaya.app.domain.model.solicitud.Tamano;
import pe.reciclaya.app.domain.model.solicitud.TipoResiduo;

public class SolicitudMapper {
    private SolicitudMapper() {}

    public static Solicitud toDomain(SolicitudResponse solicitud) {
        return new Solicitud(
                solicitud.getSolicitudID(),
                TipoResiduo.fromString(solicitud.getTipoResiduo()),
                Tamano.fromString(solicitud.getTamano()),
                Estado.fromString(solicitud.getEstado()),
                solicitud.getFecha(),
                solicitud.getHora(),
                solicitud.getDireccion(),
                solicitud.getLatitude(),
                solicitud.getLongitude(),
                UserSolicitudMapper.toDomain(solicitud.getUser())
        );
    }

    public static List<Solicitud> allToDomain(List<SolicitudResponse> solicitudes) {
        List<Solicitud> solicitudList = new ArrayList<>();
        for(SolicitudResponse solicitud : solicitudes)
            solicitudList.add(toDomain(solicitud));

        return solicitudList;
    }

    public static SolicitudRequest toRequest(SolicitudNueva nuevaSolicitud) {
        return new SolicitudRequest(
                nuevaSolicitud.getTipoResiduoString(),
                nuevaSolicitud.getTamanoString(),
                nuevaSolicitud.getFecha(),
                nuevaSolicitud.getHora(),
                nuevaSolicitud.getDireccion(),
                nuevaSolicitud.getLatitude(),
                nuevaSolicitud.getLongitude(),
                nuevaSolicitud.getUserID()
        );
    }

    public static UpdateRequest toRequest(SolicitudActualizada solicitudActualizada) {
        return new UpdateRequest(
                solicitudActualizada.getTipoResiduoString(),
                solicitudActualizada.getTamanoString(),
                solicitudActualizada.getFecha(),
                solicitudActualizada.getHora(),
                solicitudActualizada.getDireccion(),
                solicitudActualizada.getLatitude(),
                solicitudActualizada.getLongitude()
        );
    }
}
