package pe.reciclaya.app.data.location;

import android.content.Context;
import android.location.Address;
import android.location.Geocoder;
import android.os.Build;

import java.io.IOException;
import java.util.List;
import java.util.Locale;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class LocationGeocoder {

    public interface GeocoderCallback {
        void onResult(String ciudad, String pais);
    }

    private final Context context;
    private final ExecutorService executor = Executors.newSingleThreadExecutor();

    public LocationGeocoder(Context context) {
        this.context = context.getApplicationContext();
    }

    public void obtenerCiudadPais(
            Double latitud,
            Double longitud,
            GeocoderCallback callback
    ) {
        if (latitud == null || longitud == null || !Geocoder.isPresent()) {
            callback.onResult(null, null);
            return;
        }

        executor.execute(() -> {
            String ciudad = null;
            String pais = null;

            try {
                Geocoder geocoder = new Geocoder(
                        context,
                        new Locale("es", "PE")
                );

                List<Address> resultados = geocoder.getFromLocation(
                        latitud,
                        longitud,
                        1
                );

                if (resultados != null && !resultados.isEmpty()) {
                    Address address = resultados.get(0);

                    ciudad = address.getLocality();

                    if (ciudad == null) {
                        ciudad = address.getSubAdminArea();
                    }

                    pais = address.getCountryName();
                }

            } catch (IOException | IllegalArgumentException error) {
                android.util.Log.e(
                        "LocationGeocoder",
                        "No se pudo obtener la ciudad y el país",
                        error
                );
            }

            String ciudadFinal = ciudad;
            String paisFinal = pais;

            android.os.Handler mainHandler =
                    new android.os.Handler(context.getMainLooper());

            mainHandler.post(() ->
                    callback.onResult(ciudadFinal, paisFinal)
            );
        });
    }
}