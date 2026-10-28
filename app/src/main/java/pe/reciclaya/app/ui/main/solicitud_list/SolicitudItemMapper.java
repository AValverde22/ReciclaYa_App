package pe.reciclaya.app.ui.main.solicitud_list;

import java.util.ArrayList;
import java.util.List;
import java.util.Comparator;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.LocalDateTime;
import java.time.OffsetDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.time.format.ResolverStyle;

import pe.reciclaya.app.domain.model.solicitud.Solicitud;
import pe.reciclaya.app.domain.model.solicitud.Estado;
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
            case PENDIENTE: factorySolicitud = new FactoryPendiente(); break;
            case DISPONIBLE: factorySolicitud = new FactoryDisponible(); break;
            default: factorySolicitud = new FactoryFinalizada(); break;
        }

        FactoryTipoResiduo factoryTipoResiduo = TipoResiduoMapper.mapTipoResiduo(solicitud.getTipoResiduo());
        UserSolicitud user = solicitud.getUser();

        SolicitudItem item = factorySolicitud.crear(
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
        item.setClosedAt(solicitud.getClosedAt());
        return item;
    }

    public static List<SolicitudItem> allToUI(List<Solicitud> solicitudes) {
        List<SolicitudItem> solicitudItemList = new ArrayList<>();
        for(Solicitud solicitud : solicitudes)
            solicitudItemList.add(toUI(solicitud));

        return solicitudItemList;
    }

    public static List<SolicitudItem> allToUIRecentFirst(List<Solicitud> solicitudes) {
        List<Solicitud> sorted = new ArrayList<>(solicitudes);
        sorted.sort(Comparator.comparing(SolicitudItemMapper::scheduledAt).reversed()
            .thenComparing(Comparator.comparingInt(Solicitud::getID).reversed()));
        return allToUI(sorted);
    }

    private static LocalDateTime scheduledAt(Solicitud solicitud) {
        try {
            if (solicitud.getEstado() == Estado.FINALIZADA || solicitud.getEstado() == Estado.CANCELADA) {
                if (solicitud.getClosedAt() == null) return LocalDateTime.MIN;
                return OffsetDateTime.parse(solicitud.getClosedAt()).atZoneSameInstant(ZoneId.of("America/Lima")).toLocalDateTime();
            }
            LocalDate day = LocalDate.parse(solicitud.getFecha(), DateTimeFormatter.ofPattern("dd/MM/uuuu").withResolverStyle(ResolverStyle.STRICT));
            return LocalDateTime.of(day, LocalTime.parse(solicitud.getHora()));
        } catch (DateTimeParseException | NullPointerException error) {
            return LocalDateTime.MIN;
        }
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
