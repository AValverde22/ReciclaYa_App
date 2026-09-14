package pe.reciclaya.app.ui.view.register;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;

import pe.reciclaya.app.R;
import pe.reciclaya.app.domain.model.Rol;
import pe.reciclaya.app.ui.viewmodel.RegisterViewModel;

public class FragmentRegisterSeleccion extends Fragment {
    private ImageView IVRegresarUnoAtras;
    private RecyclerView RVRol;
    private Button btnConfirmar;

    private RegisterViewModel viewModel;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_register_select, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        inicializarComponentes(view);
        inicializarVM();
        inicializarRolRA();
        inicializarListeners();
    }

    private void inicializarComponentes(View view) {
        IVRegresarUnoAtras = requireActivity().findViewById(R.id.IVRegresarRegister);

        RVRol = view.findViewById(R.id.RVRolFRS);
        btnConfirmar = view.findViewById(R.id.BtnConfirmarFRS);
    }

    private void inicializarVM(){
        viewModel = new ViewModelProvider(requireActivity()).get(RegisterViewModel.class);
    }

    private void inicializarRolRA () {
        Rol[] roles = {
                new Rol(
                        "Usuario",
                        "Persona que recicla",
                        "Deseo publicar solicitudes de recolección para mis residuos",
                        R.drawable.persona_seleccionada,
                        R.drawable.persona_no_seleccionada
                        ),
                new Rol(
                        "Reciclador",
                        "Reciclador Local",
                        "Busco recolectar materiales y contribuir a la economía circular",
                        R.drawable.reciclador_seleccionado,
                        R.drawable.reciclador_no_seleccionado
                )
        };

        RolRA rolRA = new RolRA(roles, rol -> viewModel.updateRole(rol));
        RVRol.setLayoutManager(
                new LinearLayoutManager(getContext()) {
                    @Override
                    public boolean canScrollVertically() {
                        return false;
                    }
                }
        );

        RVRol.setAdapter(rolRA);
    }

    private void inicializarListeners() {
        IVRegresarUnoAtras.setOnClickListener(view -> viewModel.updateEliminarFragment(true));
        btnConfirmar.setOnClickListener(view -> viewModel.registerUser());
    }
}