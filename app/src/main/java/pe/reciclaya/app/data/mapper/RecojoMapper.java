package pe.reciclaya.app.data.mapper;

import pe.reciclaya.app.data.model.solicitud.response.RecojoActivo;
import pe.reciclaya.app.domain.model.recojo.*;

public final class RecojoMapper {
    private RecojoMapper() { }
    public static Recojo toDomain(RecojoActivo response) {
        if (response.solicitud == null) return null;
        RecojoActivo.Solicitud s = response.solicitud;
        GeoPoint recycler = s.recycler_latitude == null || s.recycler_longitude == null ? null : new GeoPoint(s.recycler_latitude,s.recycler_longitude);
        return new Recojo(s.id,s.created_by,s.accepted_by,s.type,s.size,s.address,s.day,s.time,
            response.counterpart == null ? null : response.counterpart.full_name,s.location_updated_at,
            new GeoPoint(s.latitude,s.longitude),recycler,s.user_confirmed,s.recycler_confirmed,response.arrived,response.has_rated);
    }
}
