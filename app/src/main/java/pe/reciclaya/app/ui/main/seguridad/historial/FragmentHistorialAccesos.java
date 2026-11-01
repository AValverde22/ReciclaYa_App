package pe.reciclaya.app.ui.main.seguridad.historial;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import android.content.Intent;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import pe.reciclaya.app.R;
import pe.reciclaya.app.ui.common.event.EventObserver;

public class FragmentHistorialAccesos extends Fragment {
    private RecyclerView RV;
    private LinearLayout LLLoading;
    private TextView TVVacio;
    private AccesoRVA accesoRVA;
    private ViewModelHistorialAccesos viewModel;
    private com.google.android.material.button.MaterialButton BTMisReportes;

    @Nullable
    @Override
    public View onCreateView(
            @NonNull LayoutInflater inflater,
            @Nullable ViewGroup container,
            @Nullable Bundle savedInstanceState
    ) {
        return inflater.inflate(
                R.layout.fragment_historial_accesos,
                container,
                false
        );
    }

    @Override
    public void onViewCreated(
            @NonNull View view,
            @Nullable Bundle savedInstanceState
    ) {
        super.onViewCreated(view, savedInstanceState);

        inicializarComponentes(view);
        BTMisReportes.setOnClickListener(v -> {
            Intent intent = new Intent(
                    requireContext(),
                    MisReportesActivity.class
            );

            startActivity(intent);
        });
        inicializarAdapter();
        inicializarViewModel();
        observarDatos();
    }

    private void inicializarComponentes(View view) {
        RV = view.findViewById(R.id.RVHistorialAccesos);
        LLLoading = view.findViewById(R.id.LLLoadingHistorialAccesos);
        TVVacio = view.findViewById(R.id.TVVacioHistorialAccesos);
        BTMisReportes = view.findViewById(R.id.BTMisReportesHA);
    }

    private void inicializarAdapter() {

        accesoRVA = new AccesoRVA(
                new ArrayList<>(),
                acceso -> abrirDetalleAcceso(acceso)
        );

        RV.setLayoutManager(
                new LinearLayoutManager(requireContext())
        );

        RV.setAdapter(accesoRVA);
    }

    private void inicializarViewModel() {

        viewModel = new ViewModelProvider(this)
                .get(ViewModelHistorialAccesos.class);
    }

    private void observarDatos() {

        viewModel.getAccesos().observe(
                getViewLifecycleOwner(),
                lista -> {

                    accesoRVA.setAccesos(lista);

                    boolean vacio = lista == null || lista.isEmpty();

                    TVVacio.setVisibility(
                            vacio ? View.VISIBLE : View.GONE
                    );

                    RV.setVisibility(
                            vacio ? View.GONE : View.VISIBLE
                    );
                }
        );

        viewModel.getLoading().observe(
                getViewLifecycleOwner(),
                loading -> LLLoading.setVisibility(
                        Boolean.TRUE.equals(loading)
                                ? View.VISIBLE
                                : View.GONE
                )
        );

        viewModel.getError().observe(
                getViewLifecycleOwner(),
                new EventObserver<>(errorMessage ->
                        Toast.makeText(
                                requireContext(),
                                errorMessage,
                                Toast.LENGTH_SHORT
                        ).show()
                )
        );
    }

    private void abrirDetalleAcceso(AccesoItem acceso) {

        Intent intent = new Intent(
                requireContext(),
                DetalleAccesoActivity.class
        );

        intent.putExtra("ACCESO_ID", acceso.getId());

        startActivity(intent);
    }

    @Override
    public void onResume() {
        super.onResume();

        if (viewModel != null) {
            viewModel.updateHistorial();
        }
    }
}