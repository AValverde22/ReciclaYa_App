package pe.reciclaya.app.ui.auth.restablecer.fragments;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import pe.reciclaya.app.R;
import pe.reciclaya.app.ui.auth.restablecer.RestablecerViewModel;
import pe.reciclaya.app.ui.common.navigation.BackNavigator;

public class FragmentRestablecerCompararCodigo extends Fragment {
    private ImageView IVRegresarUnoAtras;
    private EditText ETCod1, ETCod2, ETCod3, ETCod4, ETCod5, ETCod6;
    private TextView TVReenviarCodigo;
    private Button btnContinuar;

    private RestablecerViewModel viewModel;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_restablecer_comparar_codigo, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        inicializarComponentes(view);
        inicializarVM();
        inicializarListeners();
    }

    private void inicializarComponentes(View view) {
        IVRegresarUnoAtras = requireActivity().findViewById(R.id.IVRegresarRCC);

        ETCod1 = view.findViewById(R.id.ETCod1RCC);
        ETCod2 = view.findViewById(R.id.ETCod2RCC);
        ETCod3 = view.findViewById(R.id.ETCod3RCC);
        ETCod4 = view.findViewById(R.id.ETCod4RCC);
        ETCod5 = view.findViewById(R.id.ETCod5RCC);
        ETCod6 = view.findViewById(R.id.ETCod6RCC);

        TVReenviarCodigo = view.findViewById(R.id.TVReenviarCodigoRCC);
        btnContinuar = view.findViewById(R.id.btnContinuarRCC);
    }

    private void inicializarVM() {
        viewModel = new ViewModelProvider(requireActivity()).get(RestablecerViewModel.class);

        viewModel.getLoading().observe(getViewLifecycleOwner(), isLoading ->
            btnContinuar.setEnabled(!isLoading)
        );
    }

    private void inicializarListeners() {
        IVRegresarUnoAtras.setOnClickListener(view ->
            ((BackNavigator) requireActivity()).navigateBack()
        );

        ETCod1.addTextChangedListener(TextWatcherReutilizable());
        ETCod2.addTextChangedListener(TextWatcherReutilizable());
        ETCod3.addTextChangedListener(TextWatcherReutilizable());
        ETCod4.addTextChangedListener(TextWatcherReutilizable());
        ETCod5.addTextChangedListener(TextWatcherReutilizable());
        ETCod6.addTextChangedListener(TextWatcherReutilizable());

        TVReenviarCodigo.setOnClickListener(view -> viewModel.reenviarCodigo());
        btnContinuar.setOnClickListener(view -> compararCodigo());
    }

    private void compararCodigo() {
        String cod1 = ETCod1.getText().toString();
        String cod2 = ETCod2.getText().toString();
        String cod3 = ETCod3.getText().toString();
        String cod4 = ETCod4.getText().toString();
        String cod5 = ETCod5.getText().toString();
        String cod6 = ETCod6.getText().toString();

        viewModel.compararCodigo(cod1, cod2, cod3, cod4, cod5, cod6);
    }

    private TextWatcher TextWatcherReutilizable(){
        return new TextWatcher() {
            @Override
            public void afterTextChanged(Editable s) {}

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                EditText editText = (EditText) requireActivity().getCurrentFocus();

                if(editText != null) {
                    if(count == 0) {
                        View previous = editText.focusSearch(View.FOCUS_LEFT);
                        if(previous != null) previous.requestFocus();
                    } else if(count > 0 && !editText.getText().toString().isBlank()) {
                        View next = editText.focusSearch(View.FOCUS_RIGHT);
                        if(next != null) next.requestFocus();
                    }
                }
            }
        };
    }
}