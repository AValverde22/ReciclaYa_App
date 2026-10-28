package pe.reciclaya.app.ui.main.recojo;

import java.util.ArrayList;
import java.util.List;
import pe.reciclaya.app.domain.model.recojo.GeoPoint;

public final class PolylineDecoder {
    private PolylineDecoder() { }
    public static List<GeoPoint> decode(String encoded) {
        List<GeoPoint> points=new ArrayList<>(); int index=0,lat=0,lng=0;
        while (index<encoded.length()) {
            int[] delta=new int[2];
            for (int axis=0;axis<2;axis++) {
                int value=0,shift=0,b;
                do {
                    if (index>=encoded.length() || shift>=35) throw new IllegalArgumentException("Polilínea inválida");
                    b=encoded.charAt(index++)-63;
                    if (b<0 || b>63) throw new IllegalArgumentException("Polilínea inválida");
                    value|=(b&31)<<shift; shift+=5;
                } while (b>=32);
                delta[axis]=(value&1)!=0?~(value>>1):value>>1;
            }
            lat+=delta[0]; lng+=delta[1]; points.add(new GeoPoint(lat/1E5,lng/1E5));
        }
        return points;
    }
}
