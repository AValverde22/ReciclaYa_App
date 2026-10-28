package pe.reciclaya.app.domain.service;

import pe.reciclaya.app.domain.model.recojo.GeoPoint;

public final class GeoDistance {
    private GeoDistance() { }
    public static double kilometers(GeoPoint from,GeoPoint to) {
        double a=Math.toRadians(from.latitude),b=Math.toRadians(to.latitude);
        double deltaLat=b-a,deltaLon=Math.toRadians(to.longitude-from.longitude);
        double h=Math.pow(Math.sin(deltaLat/2),2)+Math.cos(a)*Math.cos(b)*Math.pow(Math.sin(deltaLon/2),2);
        return 6371.0*2*Math.asin(Math.sqrt(Math.max(0,Math.min(1,h))));
    }
}
