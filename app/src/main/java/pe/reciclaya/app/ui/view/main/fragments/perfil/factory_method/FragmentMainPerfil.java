package pe.reciclaya.app.ui.view.main.fragments.perfil.factory_method;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import pe.reciclaya.app.R;
import pe.reciclaya.app.domain.model.MiActividad;
import pe.reciclaya.app.ui.util.EventObserver;
import pe.reciclaya.app.ui.view.main.fragments.perfil.adapter.MiActividadRecyclerViewAdapter;
import pe.reciclaya.app.ui.view.splash.Splash;
import pe.reciclaya.app.ui.viewmodel.main.PerfilViewModel;

public abstract class FragmentMainPerfil extends Fragment {
    private TextView TVNombre, TVCorreo, TVRol;
    private LinearLayout LLCerrarSesion;
    private RecyclerView RV;
    protected MiActividad[] misActividades;

    private PerfilViewModel viewModel;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_main_perfil, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        inicializarComponentes(view);
        inicializarVM();
        inicializarMiActividadRA();
        inicializarListeners();
    }

    private void inicializarComponentes(View view) {
        TVNombre = view.findViewById(R.id.TVNombreFMP);
        TVCorreo = view.findViewById(R.id.TVCorreoFMP);
        TVRol = view.findViewById(R.id.TVRolFMP);

        LLCerrarSesion = view.findViewById(R.id.LLCerrarSesionFMP);
        RV = view.findViewById(R.id.RVFMP);
    }

    private void inicializarVM(){
        viewModel = new ViewModelProvider(this).get(PerfilViewModel.class);
        LifecycleOwner lifecycleOwner = getViewLifecycleOwner();

        viewModel.getFullName().observe(lifecycleOwner, fullName ->
                TVNombre.setText(fullName)
        );

        viewModel.getEmail().observe(lifecycleOwner, email ->
                TVCorreo.setText(email)
        );

        viewModel.getRole().observe(lifecycleOwner, role ->
                TVRol.setText(role)
        );

        viewModel.getCerrarSesion().observe(lifecycleOwner,
                new EventObserver<>(cerrarSesion -> {
                    if(cerrarSesion) {
                        startActivity(new Intent(requireActivity(), Splash.class));
                        requireActivity().finishAffinity();
                    }
                })
        );
    }

    private void inicializarMiActividadRA() {
        inicializarMisActividades();
        MiActividadRecyclerViewAdapter miActividadRecyclerViewAdapter = new MiActividadRecyclerViewAdapter(misActividades);
        RV.setLayoutManager(
                new LinearLayoutManager(getContext()) {
                    @Override
                    public boolean canScrollVertically() { return false; }
                }
        );

        RV.setAdapter(miActividadRecyclerViewAdapter);
    }

    protected abstract void inicializarMisActividades();

    private void inicializarListeners() {
        LLCerrarSesion.setOnClickListener(view -> viewModel.cerrarSesion() );
    }
}