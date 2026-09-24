package pe.reciclaya.app.ui.auth.restablecer.fragments;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.ViewModelProvider;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import pe.reciclaya.app.R;
import pe.reciclaya.app.ui.auth.restablecer.RestablecerViewModel;
import pe.reciclaya.app.ui.common.navigation.BackNavigator;
import pe.reciclaya.app.ui.common.util.Inicializaciones;

public class FragmentRestablecerCambiarPassword extends Fragment {
    private EditText ETPassword, ETConfirmPassword;
    private ImageView IVRegresarUnoAtras;
    private Button btnConfirmar;

    private RestablecerViewModel viewModel;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_restablecer_cambiar_password, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        inicializarComponentes(view);
        inicializarVM();
        inicializarListeners();
    }

    private void inicializarComponentes(View view) {
        IVRegresarUnoAtras = requireActivity().findViewById(R.id.IVRegresarRCP);

        ETPassword = view.findViewById(R.id.ETPasswordRCP);
        ETConfirmPassword = view.findViewById(R.id.ETConfirmPasswordRCP);

        btnConfirmar = view.findViewById(R.id.btnConfirmarRCP);

        ImageView IVOjo1 = view.findViewById(R.id.IVOjo1RCP);
        ImageView IVOjo2 = view.findViewById(R.id.IVOjo2RCP);

        Inicializaciones.mostrarPassword(ETPassword, IVOjo1);
        Inicializaciones.mostrarPassword(ETConfirmPassword, IVOjo2);
    }

    private void inicializarVM() {
        viewModel = new ViewModelProvider(requireActivity()).get(RestablecerViewModel.class);
        LifecycleOwner lifecycleOwner = getViewLifecycleOwner();

        viewModel.getLoading().observe(lifecycleOwner, isLoading ->
            btnConfirmar.setEnabled(!isLoading)
        );

        viewModel.getPasswordError().observe(lifecycleOwner, error ->
            ETPassword.setError(error)
        );

        viewModel.getConfirmPasswordError().observe(lifecycleOwner, error ->
            ETConfirmPassword.setError(error)
        );
    }

    private void inicializarListeners() {
        IVRegresarUnoAtras.setOnClickListener(view ->
            ((BackNavigator) requireActivity()).navigateBack()
        );

        ETPassword.setOnFocusChangeListener((view, hasFocus) -> {
            if(!hasFocus) viewModel.updatePassword(ETPassword.getText().toString());
        });

        ETConfirmPassword.setOnFocusChangeListener((view, hasFocus) -> {
            if(!hasFocus) viewModel.updateConfirmPassword(
                    ETPassword.getText().toString(),
                    ETConfirmPassword.getText().toString()
            );
        });

        btnConfirmar.setOnClickListener(view -> resetUser());
    }

    private void resetUser() {
        String password = ETPassword.getText().toString();
        String confirmPassword = ETConfirmPassword.getText().toString();

        viewModel.resetUser(password, confirmPassword);
    }
}