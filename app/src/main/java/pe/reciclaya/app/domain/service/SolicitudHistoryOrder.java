package pe.reciclaya.app.domain.service;

import java.util.*;
import java.time.*;
import java.time.format.*;
import pe.reciclaya.app.domain.model.solicitud.*;

/** History chronology uses actual closure time for terminal requests. */
public final class SolicitudHistoryOrder {
    private static final DateTimeFormatter DATE=DateTimeFormatter.ofPattern("dd/MM/uuuu").withResolverStyle(ResolverStyle.STRICT);
    private SolicitudHistoryOrder() { }
    public static List<Solicitud> recentFirst(List<Solicitud> source) {
        List<Solicitud> sorted=new ArrayList<>(source);
        sorted.sort(Comparator.comparing(SolicitudHistoryOrder::eventTime).reversed()
            .thenComparing(Comparator.comparingInt(Solicitud::getID).reversed()));
        return sorted;
    }
    private static LocalDateTime eventTime(Solicitud request) {
        try {
            if (request.getEstado()==Estado.FINALIZADA || request.getEstado()==Estado.CANCELADA) {
                if (request.getClosedAt()==null) return LocalDateTime.MIN;
                return OffsetDateTime.parse(request.getClosedAt()).atZoneSameInstant(ZoneId.of("America/Lima")).toLocalDateTime();
            }
            return LocalDateTime.of(LocalDate.parse(request.getFecha(),DATE),LocalTime.parse(request.getHora()));
        } catch (DateTimeParseException | NullPointerException error) { return LocalDateTime.MIN; }
    }
}
