package pe.reciclaya.app.ui.view.restablecer;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import pe.reciclaya.app.R;
import pe.reciclaya.app.ui.viewmodel.RestablecerViewModel;


public class FragmentRestablecerEnviarCodigo extends Fragment {
    private ImageView IVRegresarUnoAtras;
    private EditText ETEmail;
    private Button btnEnviarCodigo;
    private TextView TVIrALogin;

    private RestablecerViewModel viewModel;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_restablecer_enviar_codigo, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        inicializarComponentes(view);
        inicializarVM();
        inicializarListeners();
    }

    private void inicializarComponentes(View view) {
        IVRegresarUnoAtras = requireActivity().findViewById(R.id.IVRegresarReset);

        ETEmail = view.findViewById(R.id.ETEmailREC);
        btnEnviarCodigo = view.findViewById(R.id.btnEnviarCodigoREC);
        TVIrALogin = view.findViewById(R.id.TVLoginREC);
    }

    private void inicializarVM() {
        viewModel = new ViewModelProvider(requireActivity()).get(RestablecerViewModel.class);

        viewModel.getLoading().observe(getViewLifecycleOwner(), isLoading ->
            btnEnviarCodigo.setEnabled(!isLoading)
        );

        viewModel.getEmailError().observe(getViewLifecycleOwner(), error ->
            ETEmail.setError(error)
        );
    }

    private void inicializarListeners() {
        IVRegresarUnoAtras.setOnClickListener(view -> viewModel.updateFinalizarActivity(true));
        TVIrALogin.setOnClickListener(view -> viewModel.updateFinalizarActivity(true));

        ETEmail.setOnFocusChangeListener((view, hasFocus) -> {
            if(!hasFocus) viewModel.updateEmail(ETEmail.getText().toString());
        });

        btnEnviarCodigo.setOnClickListener(view -> enviarCodigo());
    }

    private void enviarCodigo() {
        String email = ETEmail.getText().toString();
        viewModel.enviarCodigo(email);
    }
}