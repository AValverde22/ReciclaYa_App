package pe.reciclaya.app.ui.main.usuario;

import android.Manifest;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.io.IOException;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

import pe.reciclaya.app.R;
import pe.reciclaya.app.ui.common.event.EventObserver;
import pe.reciclaya.app.ui.main.usuario.tipo_residuo_list.Papel;
import pe.reciclaya.app.ui.main.usuario.tipo_residuo_list.Plastico;
import pe.reciclaya.app.ui.main.usuario.tipo_residuo_list.TipoResiduo;
import pe.reciclaya.app.ui.main.usuario.tipo_residuo_list.TipoResiduoRVA;
import pe.reciclaya.app.ui.main.usuario.tipo_residuo_list.Vidrio;

public class FragmentMainUsuarioSolicitar extends Fragment implements OnMapReadyCallback, GoogleMap.OnMapClickListener, GoogleMap.OnMyLocationButtonClickListener {
    private RecyclerView RVTipoResiduo;
    private Spinner spinner;
    private TextView TVFecha, TVHora, TVDireccion, TVLimpiarCampos;
    private Button BtnPublicarSolicitud;

    private FrameLayout FLMapa;
    private CardView CVCerrarMapa;
    private TextView TVDireccionMapa;

    private GoogleMap googleMap;
    private Marker marker;

    private TipoResiduoRVA tipoResiduoRVA;
    private ViewModelSolicitar viewModel;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_main_solicitar, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        inicializarComponentes(view);
        inicializarVM();
        inicializarTipoResiduoRA();
        inicializarSpinner();
        inicializarListeners();
    }

    private void inicializarComponentes(View view) {
        RVTipoResiduo = view.findViewById(R.id.RVTipoResiduoFMUS);
        spinner = view.findViewById(R.id.SpinnerFMUS);

        TVFecha = view.findViewById(R.id.TVFechaFMUS);
        TVHora = view.findViewById(R.id.TVHoraFMUS);
        TVDireccion = view.findViewById(R.id.TVDireccionFMUS);

        BtnPublicarSolicitud = view .findViewById(R.id.BtnPublicarSolicitudFMUS);
        TVLimpiarCampos = view.findViewById(R.id.TVLimpiarCamposFMUS);

        FLMapa = view.findViewById(R.id.FLMapaFMUS);
        CVCerrarMapa = view.findViewById(R.id.CVCerrarMapaFMUS);
        TVDireccionMapa = view.findViewById(R.id.TVDireccionMapaFMUS);
    }

    private void inicializarVM() {
        viewModel = new ViewModelProvider(this).get(ViewModelSolicitar.class);
        LifecycleOwner lifecycleOwner = getViewLifecycleOwner();

        viewModel.getError().observe(lifecycleOwner, new EventObserver<>(error ->
            Toast.makeText(requireContext(), error, Toast.LENGTH_SHORT).show()
        ));

        viewModel.getUbicacionSeleccionada().observe(lifecycleOwner, new EventObserver<>(latLng -> {
            if(marker == null) marker = googleMap.addMarker(new MarkerOptions().position(latLng));
            if(marker != null) marker.setPosition(latLng);

            googleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(latLng, 16));
        }));

        viewModel.getDireccion().observe(lifecycleOwner, new EventObserver<>(direccion -> {
            if(marker != null) marker.setTitle(direccion);

            TVDireccion.setText(direccion);
            TVDireccionMapa.setText(direccion);
        }));

        viewModel.getErrorFecha().observe(lifecycleOwner, new EventObserver<>(error -> {
            TVFecha.setError(error ? "" : null);
            TVHora.setEnabled(!error);
        }));

        viewModel.getErrorHora().observe(lifecycleOwner, new EventObserver<>(error ->
            TVHora.setError(error ? "" : null)
        ));

        viewModel.getErrorDireccion().observe(lifecycleOwner, new EventObserver<>(error ->
            TVDireccion.setError(error ? "" : null)
        ));
    }

    private void inicializarTipoResiduoRA() {
        TipoResiduo[] tipoResiduos = {
                new Plastico(),
                new Vidrio(),
                new Papel()
        };

        tipoResiduoRVA = new TipoResiduoRVA(tipoResiduos, tipoResiduo -> viewModel.updateTipoResiduo(tipoResiduo));
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false) {
            @Override
            public boolean canScrollHorizontally() { return false; }
        };

        RVTipoResiduo.setLayoutManager(linearLayoutManager);
        RVTipoResiduo.setAdapter(tipoResiduoRVA);
    }

    private void inicializarSpinner() {
        String[] tamanos = {"Extrapequeña", "Pequeña", "Mediana", "Grande", "Extragrande"};
        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<>(requireContext(), R.layout.spinner_selected, tamanos);
        arrayAdapter.setDropDownViewResource(R.layout.spinner_item);

        spinner.setAdapter(arrayAdapter);
    }

    private void inicializarListeners() {
        TVFecha.setOnClickListener(view -> mostrarSelectorFecha());

        TVHora.setOnClickListener(view -> {
            viewModel.updateFecha(TVFecha.getText().toString());
            if(TVFecha.getError() == null) mostrarSelectorHora();
        });

        TVDireccion.setOnClickListener(view -> FLMapa.setVisibility(View.VISIBLE));
        CVCerrarMapa.setOnClickListener(view -> FLMapa.setVisibility(View.GONE));

        BtnPublicarSolicitud.setOnClickListener(view -> publicarSolicitud());
        TVLimpiarCampos.setOnClickListener(view -> limpiarCampos());
    }

    private void mostrarSelectorFecha() {
        final Calendar now = Calendar.getInstance();
        new DatePickerDialog(
                requireContext(),
                (datePicker, year, month, dayOfMonth) -> {
                    String fecha = String.format(Locale.getDefault(), "%02d/%02d/%04d", dayOfMonth, month + 1, year);
                    TVFecha.setText(fecha);
                    viewModel.updateFecha(fecha);
                },
                now.get(Calendar.YEAR),
                now.get(Calendar.MONTH),
                now.get(Calendar.DAY_OF_MONTH)
        ).show();
    }

    private void mostrarSelectorHora() {
        final Calendar now = Calendar.getInstance();
        new TimePickerDialog(
                requireContext(),
                (timePicker, hourOfDay, minute) -> {
                    String hora = String.format(Locale.getDefault(), "%02d:%02d", hourOfDay, minute);
                    TVHora.setText(hora);
                    viewModel.updateHora(hora);
                },
                now.get(Calendar.HOUR_OF_DAY),
                now.get(Calendar.MINUTE),
                true
        ).show();
    }

    @Override
    public void onMapReady(@NonNull GoogleMap googleMap) {
        this.googleMap = googleMap;

        googleMap.setOnMapClickListener(this);
        googleMap.getUiSettings().setCompassEnabled(false);
        googleMap.getUiSettings().setMapToolbarEnabled(false);

        googleMap.setInfoWindowAdapter(new GoogleMap.InfoWindowAdapter() {
            @Override
            public View getInfoContents(@NonNull Marker marker) {
                View view = View.inflate(requireContext(), R.layout.custom_info_window_layout, null);
                TextView TVTitulo = view.findViewById(R.id.TVTituloCIWL);
                TVTitulo.setText(marker.getTitle());
                return view;
            }

            @Nullable
            @Override
            public View getInfoWindow(@NonNull Marker marker) { return null; }
        });

        if(ContextCompat.checkSelfPermission(requireContext(), Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED
                && ContextCompat.checkSelfPermission(requireContext(), Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            LocationServices.getFusedLocationProviderClient(requireActivity())
                    .getLastLocation()
                    .addOnSuccessListener(location -> {
                        if(location != null) {
                            googleMap.setMyLocationEnabled(true);
                            googleMap.setOnMyLocationButtonClickListener(this);
                        }
                    });
        }
    }

    @Override
    public void onMapClick(@NonNull LatLng latLng) { seleccionarUbicacion(latLng); }

    @Override
    public boolean onMyLocationButtonClick() {
        if(ContextCompat.checkSelfPermission(requireContext(), Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED
                && ContextCompat.checkSelfPermission(requireContext(), Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            LocationServices.getFusedLocationProviderClient(requireActivity())
                    .getLastLocation()
                    .addOnSuccessListener(location -> {
                        if(location != null)
                            seleccionarUbicacion(new LatLng(location.getLatitude(), location.getLongitude()));
                    });
        }
        return true;
    }

    private void seleccionarUbicacion(LatLng latLng) {
        Geocoder geocoder = new Geocoder(requireContext(), Locale.getDefault());
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            geocoder.getFromLocation(latLng.latitude, latLng.longitude, 1, new Geocoder.GeocodeListener() {
                @Override
                public void onGeocode(@NonNull List<Address> addresses) {
                    mostrarDireccion(addresses, latLng);
                }

                @Override
                public void onError(@Nullable String errorMessage) {
                    actualizarError("Error al obtener la ubicación");
                }
            });
        } else {
            try {
                List<Address> addresses = geocoder.getFromLocation(latLng.latitude, latLng.longitude, 1);
                mostrarDireccion(addresses, latLng);
            } catch (IOException e) { actualizarError("Error al obtener ubicación."); }
        }
    }

    private void mostrarDireccion(List<Address> addresses, LatLng latLng) {
        if(addresses == null || addresses.isEmpty()) {
            actualizarError("Error al obtener ubicación");
            return;
        }

        Address address = addresses.get(0);
        if(address.getPostalCode() == null) {
            actualizarError("Debe ingresar una zona válida.");
            return;
        }

        if(!address.getCountryCode().equals("PE")) {
            actualizarError("La ubicación debe encontrase en Perú.");
            return;
        }

        viewModel.updateUbicacionSeleccionada(latLng);
        viewModel.updateDireccion(address.getAddressLine(0));
    }

    private void actualizarError(String error) { viewModel.updateError(error); }

    private void publicarSolicitud() {
        String tamano = spinner.getSelectedItem().toString();
        viewModel.publicarSolicitud(tamano);
    }

    private void limpiarCampos() {
        tipoResiduoRVA.eliminarSeleccion();
        spinner.setSelection(0);

        TVFecha.setText("");
        TVHora.setText("");
        TVDireccion.setText("");
        TVDireccionMapa.setText("");

        viewModel.limpiarCampos();
    }
}