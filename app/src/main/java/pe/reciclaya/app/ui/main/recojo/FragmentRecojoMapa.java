package pe.reciclaya.app.ui.main.recojo;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.os.SystemClock;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.*;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.core.content.ContextCompat;
import androidx.core.content.res.ResourcesCompat;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import com.google.android.gms.maps.*;
import com.google.android.gms.maps.model.*;
import com.google.gson.JsonObject;
import java.util.ArrayList;
import java.util.List;
import pe.reciclaya.app.R;
import pe.reciclaya.app.ui.common.util.FechaHora;
import pe.reciclaya.app.data.model.solicitud.response.RecojoActivo;
import pe.reciclaya.app.data.remote.BackendClient;
import pe.reciclaya.app.domain.model.user.User;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/** Shared map and completion flow for both participants. GPS is shared only in foreground. */
public abstract class FragmentRecojoMapa extends Fragment {
    protected abstract boolean isRecycler();
    private RecojoViewModel vm;
    private GoogleMap map;
    private Marker destination, recycler;
    private Polyline routeLine;
    private TextView detail, status;
    private Button finish, rate, navigate, permission, cancel, center;
    private boolean busy, requestingRoute;
    private long lastRoute;
    private int displayedId = -1;
    private int confirmedRequestId = -1, ratedRequestId = -1;
    private AlertDialog dialog;
    private final List<Call<?>> calls = new ArrayList<>();
    private final ActivityResultLauncher<String[]> permissions = registerForActivityResult(
        new ActivityResultContracts.RequestMultiplePermissions(), result -> {
            if (getView() == null) return;
            updatePermissionVisibility();
            if (hasFineLocation()) startGps();
            else status.setText("Permita la ubicación precisa en Ajustes y active el GPS para compartir el recorrido.");
        });
    @Nullable @Override public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_recojo_activo, container, false);
    }
    @Override public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        vm = new ViewModelProvider(requireActivity()).get(RecojoViewModel.class);
        detail = view.findViewById(R.id.detalleRecojo); status = view.findViewById(R.id.estadoRecojo);
        finish = view.findViewById(R.id.terminarRecojo); rate = view.findViewById(R.id.calificarRecojo);
        navigate = view.findViewById(R.id.navegarRecojo); permission = view.findViewById(R.id.ubicacionRecojo);
        cancel = view.findViewById(R.id.cancelarRecojo); center = view.findViewById(R.id.centrarRecojo);
        center.setText(isRecycler() ? "Mi ubicación" : "Punto de recojo");
        center.setOnClickListener(v -> centerMap());
        cancel.setOnClickListener(v -> cancelActive());
        updatePermissionVisibility();
        permission.setOnClickListener(v -> {
            if (hasFineLocation()) startGps();
            else permissions.launch(new String[]{Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION});
        });
        if (isRecycler()) vm.attachTracker(requireActivity());
        SupportMapFragment fragment = (SupportMapFragment) getChildFragmentManager().findFragmentByTag("recojoMap");
        if (fragment == null) {
            fragment = SupportMapFragment.newInstance();
            getChildFragmentManager().beginTransaction().replace(R.id.mapaRecojo, fragment, "recojoMap").commitNow();
        }
        fragment.getMapAsync(googleMap -> {
            if (getView() == null) return;
            map = googleMap;
            map.moveCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(-12.0464, -77.0428), 12));
            render(vm.state.getValue());
        });
        vm.state.observe(getViewLifecycleOwner(), this::render);
        vm.location.observe(getViewLifecycleOwner(), point -> {
            if (!isRecycler() || map == null || point == null) return;
            LatLng position = new LatLng(point.getLatitude(), point.getLongitude());
            updateRecyclerMarker(position);
        });
        vm.error.observe(getViewLifecycleOwner(), message -> status.setText(message));
        finish.setOnClickListener(v -> confirm());
        rate.setOnClickListener(v -> showRating());
        navigate.setOnClickListener(v -> {
            RecojoActivo state = vm.state.getValue();
            if (state == null || state.solicitud == null) return;
            String url = "https://www.google.com/maps/dir/?api=1&destination=" + state.solicitud.latitude + "," + state.solicitud.longitude + "&travelmode=driving";
            try { startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(url))); }
            catch (android.content.ActivityNotFoundException e) { status.setText("No hay una aplicación de navegación disponible."); }
        });
    }
    private int userId() { return User.getInstance().getID(); }
    private boolean hasFineLocation() {
        return isAdded() && ContextCompat.checkSelfPermission(requireContext(), Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED;
    }
    @Override public void onStart() { super.onStart(); if (vm != null) vm.refresh(); if (isRecycler() && hasFineLocation()) startGps(); }
    @Override public void onResume() {
        super.onResume();
        if (getView() != null) {
            updatePermissionVisibility();
            if (isRecycler() && hasFineLocation()) startGps();
        }
    }
    private void updatePermissionVisibility() {
        if (permission != null) permission.setVisibility(isRecycler() && !hasFineLocation() ? View.VISIBLE : View.GONE);
    }
    private void startGps() {
        if (isRecycler()) { vm.attachTracker(requireActivity()); vm.startGps(); }
    }
    private void updateRecyclerMarker(LatLng point) {
        if (map == null) return;
        String label = isRecycler() ? "Tú" : "Reciclador";
        if (recycler == null) {
            recycler = map.addMarker(new MarkerOptions().position(point).title(label)
                .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_AZURE)));
            if (recycler != null) recycler.showInfoWindow();
        } else { recycler.setPosition(point); recycler.setTitle(label); }
    }
    private void centerMap() {
        if (map == null) return;
        if (isRecycler()) {
            if (!hasFineLocation()) {
                permissions.launch(new String[]{Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION});
                return;
            }
            android.location.Location point = vm.location.getValue();
            if (point == null || SystemClock.elapsedRealtimeNanos() - point.getElapsedRealtimeNanos() > 120000000000L) {
                startGps(); Toast.makeText(requireContext(), "Esperando tu ubicación actual. Comprueba que el GPS esté activo.", Toast.LENGTH_LONG).show(); return;
            }
            LatLng position = new LatLng(point.getLatitude(), point.getLongitude());
            updateRecyclerMarker(position);
            map.animateCamera(CameraUpdateFactory.newLatLngZoom(position, 17));
            if (recycler != null) recycler.showInfoWindow();
        } else {
            RecojoActivo state = vm.state.getValue();
            if (state == null || state.solicitud == null) return;
            map.animateCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(state.solicitud.latitude, state.solicitud.longitude), 17));
            if (destination != null) destination.showInfoWindow();
        }
    }
    private void render(RecojoActivo state) {
        if (getView() == null) return;
        updatePermissionVisibility();
        finish.setVisibility(View.GONE); finish.setEnabled(false);
        rate.setVisibility(View.GONE); navigate.setVisibility(View.GONE);
        cancel.setVisibility(View.GONE);
        center.setVisibility(isRecycler() ? View.VISIBLE : View.GONE);
        center.setEnabled(map != null);
        if (state == null) { detail.setText("Consultando reciclaje activo…"); return; }
        if (state.solicitud == null) {
            detail.setText("No tienes un reciclaje activo."); status.setText("Cuando se acepte una solicitud, podrás seguir el recojo aquí.");
            if (displayedId != -1 && map != null) { map.clear(); destination = null; recycler = null; routeLine = null; }
            displayedId = -1; confirmedRequestId = -1; ratedRequestId = -1; return;
        }
        RecojoActivo.Solicitud s = state.solicitud;
        boolean changed = displayedId != s.id;
        if (changed) { if (map != null) map.clear(); destination = null; recycler = null; routeLine = null; lastRoute = 0; displayedId = s.id; }
        detail.setText("Solicitud #" + s.id + " · " + s.type + " · " + s.size + "\n" + s.address + "\n" + s.day + " · " + s.time +
            (state.counterpart == null ? "" : "\n" + (isRecycler() ? "Usuario: " : "Reciclador: ") + state.counterpart.full_name));
        boolean mine = (isRecycler() ? s.recycler_confirmed : s.user_confirmed) || confirmedRequestId == s.id;
        boolean both = s.user_confirmed && s.recycler_confirmed;
        boolean hasRated = state.has_rated || ratedRequestId == s.id;
        status.setText(both ? (hasRated ? "Calificación guardada. Esperando la calificación de la otra persona." : "Ambos confirmaron el fin. Califica a la otra persona para completar el reciclaje.") :
            mine ? "Confirmaste el fin. Podrás calificar cuando la otra persona también confirme." : state.arrived ? "El reciclador llegó. Pueden confirmar el fin del reciclaje." :
            s.recycler_latitude == null ? "Esperando ubicación del reciclador. Mantenga su aplicación abierta y el GPS activo." : locationStatus(s.location_updated_at));
        finish.setVisibility(mine ? View.GONE : View.VISIBLE);
        finish.setEnabled(!busy && !mine && state.arrived);
        finish.setAlpha(finish.isEnabled() ? 1f : 0.5f);
        rate.setVisibility(mine && !hasRated ? View.VISIBLE : View.GONE);
        rate.setEnabled(!busy && both); rate.setAlpha(rate.isEnabled() ? 1f : 0.5f);
        cancel.setVisibility(!both && !hasRated ? View.VISIBLE : View.GONE);
        cancel.setEnabled(!busy); cancel.setAlpha(cancel.isEnabled() ? 1f : 0.5f);
        center.setVisibility(View.VISIBLE);
        navigate.setVisibility(isRecycler() ? View.VISIBLE : View.GONE);
        if (map == null) return;
        boolean frame = destination == null;
        LatLng end = new LatLng(s.latitude, s.longitude);
        if (destination == null) destination = map.addMarker(new MarkerOptions().position(end).title("Punto de recojo"));
        if (s.recycler_latitude != null && s.recycler_longitude != null) {
            LatLng current = new LatLng(s.recycler_latitude, s.recycler_longitude);
            boolean firstRecycler = recycler == null;
            updateRecyclerMarker(current);
            if (frame || firstRecycler) {
                View mapView = requireView().findViewById(R.id.mapaRecojo);
                mapView.post(() -> {
                    if (map != null && getView() != null && displayedId == s.id && mapView.getWidth() > 0 && mapView.getHeight() > 0)
                        map.moveCamera(CameraUpdateFactory.newLatLngBounds(new LatLngBounds.Builder().include(current).include(end).build(), mapView.getWidth(), mapView.getHeight(), 60));
                });
            }
            if (!requestingRoute && SystemClock.elapsedRealtime() - lastRoute > 30000) requestRoute(s.id);
        } else if (frame) map.moveCamera(CameraUpdateFactory.newLatLngZoom(end, 16));
    }
    private String locationStatus(String iso) {
        String formatted = FechaHora.formatearLima(iso);
        return formatted == null ? getString(R.string.mapa_actualizacion_no_disponible) : getString(R.string.mapa_ultima_actualizacion, formatted);
    }
    private void cancelActive() {
        RecojoActivo state = vm.state.getValue();
        if (state == null || state.solicitud == null || busy) return;
        final int id = state.solicitud.id;
        dialog = new AlertDialog.Builder(requireContext()).setTitle(isRecycler() ? "¿Cancelar este recojo?" : "¿Cancelar la solicitud?")
            .setMessage(isRecycler() ? "Dejarás de atender este recojo y la solicitud volverá a estar disponible para otro reciclador." :
                "La solicitud quedará cancelada definitivamente y el reciclador dejará de atenderla.")
            .setNegativeButton("Volver", null).setPositiveButton("Sí, cancelar", (d, which) -> {
                busy = true; render(vm.state.getValue());
                send(BackendClient.getSolicitudService().cancelarRecojo(id, userId()), () -> busy = false, () -> {
                    confirmedRequestId = -1; ratedRequestId = -1;
                    Toast.makeText(requireContext(), isRecycler() ? "La solicitud vuelve a estar disponible." : "Solicitud cancelada.", Toast.LENGTH_LONG).show();
                });
            }).show();
        styleDialog(dialog);
    }
    private void confirm() {
        RecojoActivo state = vm.state.getValue(); if (state == null || state.solicitud == null || busy) return;
        final int id = state.solicitud.id;
        dialog = new AlertDialog.Builder(requireContext()).setTitle("¿Terminar reciclaje?")
            .setMessage("Confirma cuando hayan entregado y recibido los residuos. Ambos deberán confirmar y calificarse.")
            .setNegativeButton("No", null).setPositiveButton("Sí", (d, which) -> {
                busy = true; render(vm.state.getValue());
                send(BackendClient.getSolicitudService().confirmar(id, userId()), () -> busy = false, () -> confirmedRequestId = id);
            }).show();
        styleDialog(dialog);
    }
    private void showRating() {
        RecojoActivo state = vm.state.getValue(); if (state == null || state.solicitud == null || busy) return;
        final int id = state.solicitud.id;
        LinearLayout content = new LinearLayout(requireContext()); content.setOrientation(LinearLayout.VERTICAL); content.setPadding(dp(24), dp(12), dp(24), dp(12));
        RatingBar stars = new RatingBar(requireContext()); stars.setNumStars(5); stars.setStepSize(1); stars.setRating(0);
        content.addView(stars, new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT));
        EditText comment = new EditText(requireContext()); comment.setHint("Reseña opcional"); comment.setMaxLines(3);
        comment.setFilters(new android.text.InputFilter[]{new android.text.InputFilter.LengthFilter(1000)}); content.addView(comment);
        dialog = new AlertDialog.Builder(requireContext()).setTitle("Califica a la otra persona").setView(content)
            .setNegativeButton("Más tarde", null).setPositiveButton("Guardar", null).create();
        dialog.setOnShowListener(d -> {
            styleDialog(dialog);
            dialog.getButton(AlertDialog.BUTTON_POSITIVE).setOnClickListener(v -> {
            if (stars.getRating() < 1) { Toast.makeText(requireContext(), "Selecciona de 1 a 5 estrellas.", Toast.LENGTH_SHORT).show(); return; }
            JsonObject body = new JsonObject(); body.addProperty("evaluador_id", userId()); body.addProperty("puntuacion", (int) stars.getRating()); body.addProperty("comentario", comment.getText().toString().trim());
            busy = true; dialog.dismiss(); render(vm.state.getValue());
            send(BackendClient.getSolicitudService().calificar(id, body), () -> busy = false, () -> ratedRequestId = id);
        }); }); dialog.show();
    }
    private int dp(int value) { return Math.round(value * getResources().getDisplayMetrics().density); }
    private void applyFont(View view) {
        if (view instanceof TextView) ((TextView) view).setTypeface(ResourcesCompat.getFont(requireContext(), R.font.gotham_medium));
        if (view instanceof ViewGroup) {
            ViewGroup group = (ViewGroup) view;
            for (int i = 0; i < group.getChildCount(); i++) applyFont(group.getChildAt(i));
        }
    }
    private void styleDialog(AlertDialog alert) {
        if (alert.getWindow() != null) applyFont(alert.getWindow().getDecorView());
        TextView title = alert.findViewById(androidx.appcompat.R.id.alertTitle);
        if (title != null) title.setTypeface(ResourcesCompat.getFont(requireContext(), R.font.gotham_bold));
        for (int which : new int[]{AlertDialog.BUTTON_POSITIVE, AlertDialog.BUTTON_NEGATIVE}) {
            Button button = alert.getButton(which);
            if (button == null) continue;
            boolean primary = which == AlertDialog.BUTTON_POSITIVE;
            androidx.core.view.ViewCompat.setBackgroundTintList(button, null);
            button.setBackgroundTintList(null);
            button.setBackgroundResource(primary ? R.drawable.button_final_green_background : R.drawable.button_final_green_border);
            button.setTextColor(ContextCompat.getColor(requireContext(), primary ? R.color.white : R.color.verde));
            button.setTypeface(ResourcesCompat.getFont(requireContext(), R.font.gotham_bold));
            button.setAllCaps(false); button.setTextSize(14);
            button.setMinHeight(dp(48)); button.setPadding(dp(16), dp(12), dp(16), dp(12));
            ViewGroup.LayoutParams params = button.getLayoutParams();
            if (params instanceof ViewGroup.MarginLayoutParams) {
                ((ViewGroup.MarginLayoutParams) params).setMarginStart(dp(8)); button.setLayoutParams(params);
            }
        }
    }
    private void send(Call<JsonObject> call, Runnable done, Runnable onSuccess) {
        calls.add(call);
        call.enqueue(new Callback<JsonObject>() {
            @Override public void onResponse(Call<JsonObject> c, Response<JsonObject> response) {
                calls.remove(c); done.run(); if (getView() == null) return;
                if (!response.isSuccessful()) {
                    String message = "No se pudo completar la operación.";
                    try { if (response.errorBody() != null) message = new org.json.JSONObject(response.errorBody().string()).optString("message", message); } catch (Exception ignored) { }
                    Toast.makeText(requireContext(), message, Toast.LENGTH_LONG).show();
                }
                if (response.isSuccessful()) { onSuccess.run(); vm.invalidate(); }
                else vm.refresh();
                render(vm.state.getValue());
            }
            @Override public void onFailure(Call<JsonObject> c, Throwable t) {
                calls.remove(c); done.run(); if (getView() == null || c.isCanceled()) return;
                status.setText("Sin conexión. Vuelve a intentarlo."); render(vm.state.getValue());
            }
        });
    }
    private void requestRoute(int id) {
        requestingRoute = true; lastRoute = SystemClock.elapsedRealtime();
        Call<RecojoActivo.Ruta> call = BackendClient.getSolicitudService().ruta(id, userId()); calls.add(call);
        call.enqueue(new Callback<RecojoActivo.Ruta>() {
            @Override public void onResponse(Call<RecojoActivo.Ruta> c, Response<RecojoActivo.Ruta> response) {
                calls.remove(c); requestingRoute = false;
                if (getView() == null || map == null || displayedId != id) return;
                RecojoActivo.Ruta route = response.body();
                if (response.isSuccessful() && route != null && route.polyline != null) {
                    try {
                        List<LatLng> points = decode(route.polyline);
                        if (routeLine != null) routeLine.remove();
                        routeLine = map.addPolyline(new PolylineOptions().addAll(points).color(Color.rgb(30, 115, 70)).width(12));
                    } catch (RuntimeException ignored) { status.append("\nRuta no disponible."); }
                } else status.append("\nRuta vial no disponible. " + (isRecycler() ? "Puede abrir la navegación externa." : "Se muestran las ubicaciones."));
            }
            @Override public void onFailure(Call<RecojoActivo.Ruta> c, Throwable t) { calls.remove(c); requestingRoute = false; }
        });
    }
    private static List<LatLng> decode(String encoded) {
        List<LatLng> points = new ArrayList<>(); int index = 0, lat = 0, lng = 0;
        while (index < encoded.length()) {
            int[] delta = new int[2];
            for (int axis = 0; axis < 2; axis++) {
                int b, shift = 0, value = 0;
                do { b = encoded.charAt(index++) - 63; value |= (b & 31) << shift; shift += 5; } while (b >= 32 && shift < 35);
                delta[axis] = (value & 1) != 0 ? ~(value >> 1) : value >> 1;
            }
            lat += delta[0]; lng += delta[1]; points.add(new LatLng(lat / 1E5, lng / 1E5));
        } return points;
    }
    @Override public void onStop() {
        if (dialog != null) dialog.dismiss();
        super.onStop();
    }
    @Override public void onDestroyView() {
        for (Call<?> call : new ArrayList<>(calls)) call.cancel(); calls.clear();
        map = null; destination = null; recycler = null; routeLine = null; displayedId = -1;
        busy = false; requestingRoute = false;
        super.onDestroyView();
    }
}
