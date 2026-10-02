package pe.reciclaya.app.ui.main.usuario;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.Toast;

import java.util.ArrayList;

import pe.reciclaya.app.R;
import pe.reciclaya.app.ui.common.event.EventObserver;
import pe.reciclaya.app.ui.main.usuario.solicitud_list.Solicitud;
import pe.reciclaya.app.ui.main.usuario.solicitud_list.SolicitudRVA;

public class FragmentMainUsuarioHistorial extends Fragment {
    private RecyclerView RV;
    private LinearLayout LLLoading;

    private ViewModelHistorial viewModel;
    private SolicitudRVA solicitudRVA;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_main_historial_usuario, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        inicializarComponentes(view);
        inicializarSolicitudRA();
        inicializarVM();
    }

    private void inicializarComponentes(View view) {
        RV = view.findViewById(R.id.RVFMHU);
        LLLoading = view.findViewById(R.id.LLLoadingFMHU);
    }

    private void inicializarSolicitudRA() {
        solicitudRVA = new SolicitudRVA(new ArrayList<>());

        RV.setLayoutManager(new LinearLayoutManager(requireContext()));
        RV.setAdapter(solicitudRVA);
    }

    private void inicializarVM() {
        viewModel = new ViewModelProvider(this).get(ViewModelHistorial.class);
        LifecycleOwner lifecycleOwner = getViewLifecycleOwner();

        viewModel.getSolicitudes().observe(lifecycleOwner, solicitudes ->
            solicitudRVA.setSolicitudes(solicitudes)
        );

        viewModel.getError().observe(lifecycleOwner, new EventObserver<>(errorMessage ->
            Toast.makeText(requireContext(), errorMessage, Toast.LENGTH_SHORT).show()
        ));

        viewModel.getLoading().observe(lifecycleOwner, loading -> {
            if(loading) LLLoading.setVisibility(View.VISIBLE);
            else LLLoading.setVisibility(View.GONE);
        });
    }

    @Override
    public void onResume() {
        viewModel.updateSolicitudes();
        super.onResume();
    }
}