package pe.reciclaya.app.ui.main.recojo;

import android.os.Handler;
import android.os.Looper;
import android.app.Application;
import android.Manifest;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.SystemClock;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.FragmentActivity;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.LifecycleEventObserver;
import com.google.android.gms.location.*;
import com.google.gson.JsonObject;
import androidx.lifecycle.MutableLiveData;
import java.lang.ref.WeakReference;
import pe.reciclaya.app.data.model.solicitud.response.RecojoActivo;
import pe.reciclaya.app.data.remote.BackendClient;
import pe.reciclaya.app.domain.model.user.User;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/** Activity scoped state, refreshed while at least one visible view observes it. */
public class RecojoViewModel extends AndroidViewModel {
    public RecojoViewModel(Application application) { super(application); }
    private final Handler handler = new Handler(Looper.getMainLooper());
    private Call<RecojoActivo> request;
    private boolean cleared;
    private boolean tracking;
    private WeakReference<Lifecycle> activityLifecycle = new WeakReference<>(null);
    private FusedLocationProviderClient gps;
    private Call<JsonObject> locationRequest;
    public final MutableLiveData<Location> location = new MutableLiveData<>();
    private final LifecycleEventObserver trackerLifecycle = (owner, event) -> {
        if (event == Lifecycle.Event.ON_START || event == Lifecycle.Event.ON_RESUME) startGps();
        if (event == Lifecycle.Event.ON_STOP || event == Lifecycle.Event.ON_DESTROY) stopGps();
    };
    public void attachTracker(FragmentActivity owner) {
        Lifecycle previous = activityLifecycle.get();
        if (previous != owner.getLifecycle()) {
            if (previous != null) previous.removeObserver(trackerLifecycle);
            activityLifecycle = new WeakReference<>(owner.getLifecycle());
            owner.getLifecycle().addObserver(trackerLifecycle);
        }
    }
    private final LocationCallback locationCallback = new LocationCallback() {
        @Override public void onLocationResult(@androidx.annotation.NonNull LocationResult result) {
            Location point = result.getLastLocation();
            if (cleared || !tracking || point == null) return;
            if (SystemClock.elapsedRealtimeNanos() - point.getElapsedRealtimeNanos() > 30000000000L ||
                !point.hasAccuracy() || point.getAccuracy() > 100) { error.setValue("Esperando una ubicación GPS reciente y precisa…"); return; }
            location.setValue(point);
            RecojoActivo current = state.getValue();
            if (current == null || current.solicitud == null || locationRequest != null) return;
            JsonObject body = new JsonObject();
            body.addProperty("latitude",point.getLatitude()); body.addProperty("longitude",point.getLongitude()); body.addProperty("accuracy",point.getAccuracy());
            locationRequest = BackendClient.getSolicitudService().ubicacion(current.solicitud.id, User.getInstance().getID(), body);
            locationRequest.enqueue(new Callback<JsonObject>() {
                @Override public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                    if (locationRequest != call) return;
                    locationRequest = null;
                    if (response.isSuccessful()) refresh();
                    else { error.setValue("No se pudo compartir la ubicación. Comprobando el estado del recojo…"); refresh(); }
                }
                @Override public void onFailure(Call<JsonObject> call, Throwable failure) {
                    if (locationRequest != call) return;
                    locationRequest = null;
                    if (!call.isCanceled()) error.setValue("Sin conexión para compartir ubicación. Reintentando…");
                }
            });
        }
    };
    @android.annotation.SuppressLint("MissingPermission")
    public void startGps() {
        Lifecycle lifecycle = activityLifecycle.get();
        if (cleared || tracking || lifecycle == null || !lifecycle.getCurrentState().isAtLeast(Lifecycle.State.STARTED) ||
            User.getInstance().getRole() == null || !"Reciclador".equals(User.getInstance().getRole().getRole()) ||
            ContextCompat.checkSelfPermission(getApplication(),Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) return;
        if (gps == null) gps = LocationServices.getFusedLocationProviderClient(getApplication());
        try {
            tracking = true;
            gps.requestLocationUpdates(new LocationRequest.Builder(Priority.PRIORITY_HIGH_ACCURACY,5000).setMinUpdateIntervalMillis(5000).build(),locationCallback,Looper.getMainLooper())
                .addOnFailureListener(failure -> { tracking = false; if (!cleared) error.setValue("No se pudo activar la ubicación. Revise el GPS y vuelva a intentarlo."); });
        } catch (SecurityException failure) { tracking = false; }
    }
    private void stopGps() {
        tracking = false;
        if (gps != null) gps.removeLocationUpdates(locationCallback);
        Call<JsonObject> previous = locationRequest; locationRequest = null;
        if (previous != null) previous.cancel();
    }
    public final MutableLiveData<String> error = new MutableLiveData<>();
    public final MutableLiveData<RecojoActivo> state = new MutableLiveData<RecojoActivo>() {
        @Override protected void onActive() { refresh(); }
        @Override protected void onInactive() { handler.removeCallbacks(poll); }
    };
    private final Runnable poll = this::refresh;
    public boolean blocked() { return state.getValue() == null || state.getValue().solicitud != null; }
    public void invalidate() {
        Call<RecojoActivo> previous = request;
        request = null;
        if (previous != null) previous.cancel();
        state.setValue(null);
        refresh();
    }
    public void refresh() {
        handler.removeCallbacks(poll);
        if (cleared || request != null) return;
        request = BackendClient.getSolicitudService().getActiva(User.getInstance().getID());
        request.enqueue(new Callback<RecojoActivo>() {
            @Override public void onResponse(Call<RecojoActivo> call, Response<RecojoActivo> response) {
                if (request != call) return;
                request = null;
                if (cleared) return;
                if (response.isSuccessful() && response.body() != null) state.setValue(response.body());
                else { state.setValue(null); error.setValue("No se pudo consultar el reciclaje activo."); }
                schedule();
            }
            @Override public void onFailure(Call<RecojoActivo> call, Throwable t) {
                if (request != call) return;
                request = null;
                if (cleared) return;
                state.setValue(null);
                error.setValue("Sin conexión. Reintentando consultar el reciclaje activo…");
                schedule();
            }
        });
    }
    private void schedule() { if (state.hasActiveObservers()) handler.postDelayed(poll, 5000); }
    @Override protected void onCleared() {
        cleared = true; handler.removeCallbacks(poll); if (request != null) request.cancel();
        stopGps();
        Lifecycle lifecycle = activityLifecycle.get();
        if (lifecycle != null) lifecycle.removeObserver(trackerLifecycle);
    }
}
