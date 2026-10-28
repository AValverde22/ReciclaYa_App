package pe.reciclaya.app.ui.main.solicitud_list;

import java.util.List;
import java.util.Locale;
import pe.reciclaya.app.domain.model.recojo.*;
import pe.reciclaya.app.domain.model.solicitud.Solicitud;
import pe.reciclaya.app.domain.service.GeoDistance;

/** Builds list presentation values before they reach the adapter. */
public final class SolicitudDistanceMapper {
    private SolicitudDistanceMapper() { }
    public static List<SolicitudItem> toUI(List<Solicitud> source,LocationFix location,long now) {
        List<SolicitudItem> items=SolicitudItemMapper.allToUI(source);
        if (location!=null && location.isRecent(now,120000)) {
            for (SolicitudItem item:items) {
                double distance=GeoDistance.kilometers(location.point,new GeoPoint(item.getLatitude(),item.getLongitude()));
                item.setDistanceText(String.format(Locale.getDefault(),"%.1f km",distance));
            }
        }
        return items;
    }
}
