package pe.reciclaya.app.ui.auth.register.fragments;

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
import pe.reciclaya.app.ui.auth.register.rol_list.Reciclador;
import pe.reciclaya.app.ui.auth.register.rol_list.Rol;
import pe.reciclaya.app.ui.auth.register.rol_list.Usuario;
import pe.reciclaya.app.ui.common.navigation.BackNavigator;
import pe.reciclaya.app.ui.auth.register.rol_list.RolRVA;
import pe.reciclaya.app.ui.auth.register.RegisterViewModel;

public class FragmentRegisterSeleccion extends Fragment {
    private ImageView IVRegresarUnoAtras;
    private RecyclerView RVRol;
    private Button btnConfirmar;

    private RegisterViewModel viewModel;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_register_seleccion, container, false);
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
        IVRegresarUnoAtras = requireActivity().findViewById(R.id.IVRegresarFRS);

        RVRol = view.findViewById(R.id.RVRolFRS);
        btnConfirmar = view.findViewById(R.id.BtnConfirmarFRS);
    }

    private void inicializarVM(){
        viewModel = new ViewModelProvider(requireActivity()).get(RegisterViewModel.class);
    }

    private void inicializarRolRA () {
        Rol[] roles = {
                new Usuario(),
                new Reciclador()
        };

        RolRVA rolRVA = new RolRVA(roles, rol -> viewModel.updateRole(rol));
        RVRol.setLayoutManager(
                new LinearLayoutManager(getContext()) {
                    @Override
                    public boolean canScrollVertically() {
                        return false;
                    }
                }
        );

        RVRol.setAdapter(rolRVA);
    }

    private void inicializarListeners() {
        IVRegresarUnoAtras.setOnClickListener(view ->
            ((BackNavigator) requireActivity()).navigateBack()
        );

        btnConfirmar.setOnClickListener(view -> viewModel.registerUser());
    }
}