package pe.reciclaya.app.ui.main.reciclador;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

import pe.reciclaya.app.R;
import pe.reciclaya.app.ui.common.event.EventObserver;
import pe.reciclaya.app.ui.main.bottom_sheet_dialog.BSDFiltro;
import pe.reciclaya.app.ui.main.bottom_sheet_dialog.ViewModelFiltro;
import pe.reciclaya.app.ui.main.solicitud_list.SolicitudRVAReciclador;


public class FragmentMainRecicladorReciclar extends Fragment {
    private LinearLayout LLLoading;
    private TextView TVCantidadSolicitudes;
    private ImageView IVFiltro, IVRefresh;
    private Button BtnReset;
    private ProgressBar PB;
    private ConstraintLayout CLSinResultados;
    private RecyclerView RV;

    private ViewModelReciclar viewModel;
    private ViewModelFiltro viewModelFiltro;
    private SolicitudRVAReciclador solicitudRVAReciclador;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_main_reciclar, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        inicializarComponentes(view);
        inicializarSolicitudRA();
        inicializarVM();
        inicializarListeners();
    }

    private void inicializarComponentes(View view) {
        LLLoading = view.findViewById(R.id.LLLoadingFMR);
        TVCantidadSolicitudes = view.findViewById(R.id.TVCantidadSolicitudesFMR);
        IVFiltro = view.findViewById(R.id.IVFiltroFMR);
        IVRefresh = view.findViewById(R.id.IVRefreshFMR);
        PB = view.findViewById(R.id.PBFMR);
        BtnReset = view.findViewById(R.id.BtnResetFiltrosFMR);
        CLSinResultados = view.findViewById(R.id.CLSinResultadosFMR);
        RV = view.findViewById(R.id.RVFMR);
    }

    private void inicializarSolicitudRA() {
        solicitudRVAReciclador = new SolicitudRVAReciclador(
                new ArrayList<>(),
                id -> viewModel.acceptSolicitud(id)
        );

        RV.setLayoutManager(new LinearLayoutManager(requireContext()));
        RV.setAdapter(solicitudRVAReciclador);
    }

    private void inicializarVM() {
        viewModel = new ViewModelProvider(this).get(ViewModelReciclar.class);
        viewModelFiltro = new ViewModelProvider(this).get(ViewModelFiltro.class);
        LifecycleOwner lifecycleOwner = getViewLifecycleOwner();

        viewModelFiltro.getSolicitudFiltro().observe(lifecycleOwner, solicitudFiltros -> {
            if(solicitudFiltros != null) viewModel.getSolicitudesDisponibles(solicitudFiltros);
        });

        viewModel.getSolicitudesItem().observe(lifecycleOwner, solicitudesItem -> {
            solicitudRVAReciclador.setSolicitudes(solicitudesItem);
            String cantidad = solicitudesItem.size() + " solicitudes";
            TVCantidadSolicitudes.setText(cantidad);

            if(solicitudesItem.isEmpty()) {
                CLSinResultados.setVisibility(View.VISIBLE);
                RV.setVisibility(View.GONE);
            } else {
                CLSinResultados.setVisibility(View.GONE);
                RV.setVisibility(View.VISIBLE);
            }
        });

        viewModel.getError().observe(lifecycleOwner, new EventObserver<>(errorMessage ->
                Toast.makeText(requireContext(), errorMessage, Toast.LENGTH_SHORT).show()
        ));

        viewModel.getAccepted().observe(lifecycleOwner, new EventObserver<>(accepted -> {
            if(accepted) {
                Toast.makeText(requireContext(), "Solicitud aceptada correctamente", Toast.LENGTH_SHORT).show();
            }
        }));

        viewModel.getLoading().observe(lifecycleOwner, loading -> {
            LLLoading.setVisibility(loading ? View.VISIBLE : View.GONE);
            PB.setVisibility(loading ? View.VISIBLE : View.GONE);
            IVRefresh.setVisibility(loading ? View.GONE : View.VISIBLE);
        });
    }

    private void inicializarListeners() {
        IVFiltro.setOnClickListener(view -> {
            BSDFiltro bottomSheetDialog = new BSDFiltro();
            bottomSheetDialog.show(getChildFragmentManager(), "BSDFiltro");
        });

        IVRefresh.setOnClickListener(view -> viewModel.getSolicitudesDisponibles());
        BtnReset.setOnClickListener(view -> viewModelFiltro.resetFiltros());
    }

    @Override
    public void onResume() {
        viewModel.getSolicitudesDisponibles();
        super.onResume();
    }
}