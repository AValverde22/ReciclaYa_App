package pe.reciclaya.app.data.location;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.pm.PackageManager;

import androidx.core.content.ContextCompat;

import com.google.android.gms.location.CurrentLocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.location.Priority;

public class CurrentLocationProvider {

    public interface LocationCallback {
        void onLocationReceived(Double latitude, Double longitude);
    }

    private final Context context;

    public CurrentLocationProvider(Context context) {
        this.context = context.getApplicationContext();
    }

    public void getCurrentLocation(LocationCallback callback) {

        boolean finePermission = ContextCompat.checkSelfPermission(
                context,
                Manifest.permission.ACCESS_FINE_LOCATION
        ) == PackageManager.PERMISSION_GRANTED;

        boolean coarsePermission = ContextCompat.checkSelfPermission(
                context,
                Manifest.permission.ACCESS_COARSE_LOCATION
        ) == PackageManager.PERMISSION_GRANTED;

        if (!finePermission && !coarsePermission) {
            callback.onLocationReceived(null, null);
            return;
        }

        CurrentLocationRequest request =
                new CurrentLocationRequest.Builder()
                        .setPriority(
                                finePermission
                                        ? Priority.PRIORITY_HIGH_ACCURACY
                                        : Priority.PRIORITY_BALANCED_POWER_ACCURACY
                        )
                        .setMaxUpdateAgeMillis(60_000)
                        .build();

        @SuppressLint("MissingPermission")
        var client = LocationServices.getFusedLocationProviderClient(context);

        client.getCurrentLocation(request, null)
                .addOnSuccessListener(location -> {
                    if (location != null) {
                        callback.onLocationReceived(
                                location.getLatitude(),
                                location.getLongitude()
                        );
                    } else {
                        callback.onLocationReceived(null, null);
                    }
                })
                .addOnFailureListener(error ->
                        callback.onLocationReceived(null, null)
                );
    }
}