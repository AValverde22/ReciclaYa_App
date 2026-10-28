package pe.reciclaya.app.ui.main.recojo;

import android.graphics.Color;
import android.view.View;
import com.google.android.gms.maps.*;
import com.google.android.gms.maps.model.*;
import pe.reciclaya.app.domain.model.recojo.GeoPoint;
import pe.reciclaya.app.domain.model.recojo.Recojo;

/** Google Maps rendering only; no requests, lifecycle or completion rules. */
public final class RecojoMapRenderer {
    private final GoogleMap map;
    private final View container;
    private final RecojoMapPresentation presentation;
    private Marker destination,recycler;
    private Polyline route;
    private String drawnRoute;
    private int displayedId=-1;
    private boolean closed;
    public RecojoMapRenderer(GoogleMap map,View container,RecojoMapPresentation presentation) {
        this.map=map; this.container=container; this.presentation=presentation;
        map.clear();
        map.moveCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(-12.0464,-77.0428),12));
    }
    private LatLng latLng(GeoPoint point) { return new LatLng(point.latitude,point.longitude); }
    private void clear() { map.clear(); destination=null; recycler=null; route=null; drawnRoute=null; }
    private void recycler(GeoPoint point) {
        if (point==null) return;
        if (recycler==null) {
            recycler=map.addMarker(new MarkerOptions().position(latLng(point)).title(presentation.markerTitle()).icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_AZURE)));
            if (recycler!=null) recycler.showInfoWindow();
        } else recycler.setPosition(latLng(point));
    }
    public void render(RecojoUiState state) {
        if (closed) return;
        Recojo pickup=state.pickup;
        if (pickup==null) {
            if (!state.loading && displayedId!=-1) { clear(); displayedId=-1; }
            if (presentation.centersOnDevice() && state.location!=null) recycler(state.location.point);
            return;
        }
        if (displayedId!=pickup.id) { clear(); displayedId=pickup.id; }
        boolean firstDestination=destination==null, firstRecycler=recycler==null;
        if (firstDestination) destination=map.addMarker(new MarkerOptions().position(latLng(pickup.destination)).title("Punto de recojo"));
        GeoPoint position=presentation.centersOnDevice() && state.location!=null?state.location.point:pickup.recyclerLocation;
        recycler(position);
        if (position!=null && (firstDestination || firstRecycler)) {
            final int id=pickup.id;
            container.post(() -> {
                if (!closed && displayedId==id && container.getWidth()>0 && container.getHeight()>0)
                    map.moveCamera(CameraUpdateFactory.newLatLngBounds(new LatLngBounds.Builder().include(latLng(position)).include(latLng(pickup.destination)).build(),container.getWidth(),container.getHeight(),60));
            });
        } else if (firstDestination) map.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng(pickup.destination),16));
        if (!java.util.Objects.equals(drawnRoute,state.routePolyline)) {
            if (route!=null) { route.remove(); route=null; }
            drawnRoute=state.routePolyline;
            if (drawnRoute!=null) {
                try {
                    PolylineOptions options=new PolylineOptions().color(Color.rgb(30,115,70)).width(12);
                    for (GeoPoint point:PolylineDecoder.decode(drawnRoute)) options.add(latLng(point));
                    route=map.addPolyline(options);
                } catch (IllegalArgumentException ignored) { drawnRoute=null; }
            }
        }
    }
    public void center(GeoPoint point) {
        if (closed || point==null) return;
        map.animateCamera(CameraUpdateFactory.newLatLngZoom(latLng(point),17));
        Marker marker=presentation.centersOnDevice()?recycler:destination;
        if (marker!=null) marker.showInfoWindow();
    }
    public void close() { closed=true; }
}
