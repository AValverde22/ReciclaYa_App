package pe.reciclaya.app.data.location;

import android.content.Context;
import android.os.Looper;
import com.google.android.gms.location.*;
import pe.reciclaya.app.domain.model.recojo.GeoPoint;
import pe.reciclaya.app.domain.model.recojo.LocationFix;
import pe.reciclaya.app.domain.repository.recojo.LocationProvider;

/** Android GPS implementation. The ViewModel only knows LocationProvider. */
public final class FusedLocationProvider implements LocationProvider {
    private final FusedLocationProviderClient client;
    private boolean running;
    private Listener listener;
    private int generation;
    public FusedLocationProvider(Context context) { client=LocationServices.getFusedLocationProviderClient(context.getApplicationContext()); }
    private final LocationCallback callback=new LocationCallback() {
        @Override public void onLocationResult(LocationResult result) {
            android.location.Location location=result.getLastLocation();
            if (!running || listener==null || location==null) return;
            listener.onLocation(new LocationFix(new GeoPoint(location.getLatitude(),location.getLongitude()),
                location.hasAccuracy()?location.getAccuracy():Float.POSITIVE_INFINITY,location.getElapsedRealtimeNanos()/1000000L));
        }
    };
    @android.annotation.SuppressLint("MissingPermission")
    @Override public void start(Listener listener) {
        this.listener=listener;
        if (running) return;
        running=true; final int token=++generation;
        try {
            client.requestLocationUpdates(new LocationRequest.Builder(Priority.PRIORITY_HIGH_ACCURACY,5000).setMinUpdateIntervalMillis(5000).build(),callback,Looper.getMainLooper())
                .addOnFailureListener(error -> { if (token!=generation) return; running=false; listener.onError("No se pudo activar la ubicación. Comprueba el GPS y los permisos."); });
        } catch (SecurityException error) { running=false; listener.onError("Permite la ubicación precisa para compartir el recorrido."); }
    }
    @Override public void stop() { generation++; running=false; client.removeLocationUpdates(callback); listener=null; }
}
