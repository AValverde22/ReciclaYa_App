package pe.reciclaya.app.ui.register;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import android.text.SpannableString;
import android.text.method.LinkMovementMethod;
import android.text.style.ClickableSpan;
import android.text.style.ForegroundColorSpan;
import android.text.style.UnderlineSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import pe.reciclaya.app.R;
import pe.reciclaya.app.ui.util.Inicializaciones;

public class FragmentRegisterDatos extends Fragment {
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private String mParam1;
    private String mParam2;

    public FragmentRegisterDatos() {}

    public static FragmentRegisterDatos newInstance(String param1, String param2) {
        FragmentRegisterDatos fragment = new FragmentRegisterDatos();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    private ImageView IVRegresarUnoAtras;
    private EditText ETFullName, ETEmail, ETPassword, ETConfirmPassword;
    private TextView TVIrATyC, TVIrALogin;
    private CheckBox checkBox;
    private Button btnCrearCuenta;

    private RegisterViewModel viewModel;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_register_datos, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        inicializarComponentes(view);
        inicializarVM();
        personalizarTVIrATyC();
        inicializarListeners();
    }

    private void inicializarComponentes(View view) {
        IVRegresarUnoAtras = requireActivity().findViewById(R.id.IVRegresarRegister);

        ETFullName = view.findViewById(R.id.ETFullNameFRD);
        ETEmail = view.findViewById(R.id.ETEmailFRD);
        ETPassword = view.findViewById(R.id.ETPasswordFRD);
        ETConfirmPassword = view.findViewById(R.id.ETConfirmPasswordFRD);

        TVIrATyC = view.findViewById(R.id.TVTyCFRD);
        checkBox = view.findViewById(R.id.CBFRD);
        btnCrearCuenta = view.findViewById(R.id.BtnCrearCuentaFRD);
        TVIrALogin = view.findViewById(R.id.TVIniciarSesionFRD);

        ImageView IVOjo1 = view.findViewById(R.id.IVOjoUnoRegister);
        ImageView IVOjo2 = view.findViewById(R.id.IVOjoDosRegister);

        Inicializaciones.mostrarPassword(ETPassword, IVOjo1);
        Inicializaciones.mostrarPassword(ETConfirmPassword, IVOjo2);
    }

    private void inicializarVM() {
        viewModel = new ViewModelProvider(requireActivity()).get(RegisterViewModel.class);

        viewModel.getLoading().observe(getViewLifecycleOwner(), isLoading ->
            btnCrearCuenta.setEnabled(!isLoading)
        );

        viewModel.getFullNameError().observe(getViewLifecycleOwner(), error ->
            ETFullName.setError(error)
        );

        viewModel.getEmailError().observe(getViewLifecycleOwner(), error ->
            ETEmail.setError(error)
        );

        viewModel.getPasswordError().observe(getViewLifecycleOwner(), error ->
            ETPassword.setError(error)
        );

        viewModel.getConfirmPasswordError().observe(getViewLifecycleOwner(), error ->
            ETConfirmPassword.setError(error)
        );
    }

    private void inicializarListeners() {
        IVRegresarUnoAtras.setOnClickListener(view -> viewModel.updateFinalizarActivity(true));
        TVIrALogin.setOnClickListener(view -> viewModel.updateFinalizarActivity(true));

        ETFullName.setOnFocusChangeListener((view, hasFocus) -> {
            if(!hasFocus) viewModel.updateFullName(ETFullName.getText().toString());
        });

        ETEmail.setOnFocusChangeListener((view, hasFocus) -> {
            if(!hasFocus) viewModel.updateEmail(ETEmail.getText().toString());
        });

        ETPassword.setOnFocusChangeListener((view, hasFocus) -> {
            if(!hasFocus) viewModel.updatePassword(ETPassword.getText().toString());
        });

        ETConfirmPassword.setOnFocusChangeListener((view, hasFocus) -> {
            if(!hasFocus)
                viewModel.updateConfirmPassword(
                        ETPassword.getText().toString(),
                        ETConfirmPassword.getText().toString()
                );
        });

        checkBox.setOnCheckedChangeListener((compoundButton, isChecked) ->
                viewModel.updateCheckBox(isChecked)
        );

        btnCrearCuenta.setOnClickListener(view -> validarEmail());
    }

    private void personalizarTVIrATyC() {
        TVIrATyC.setMovementMethod(LinkMovementMethod.getInstance());

        SpannableString content = new SpannableString("Acepto los Términos de Servicio y la Política de Privacidad.");
        content.setSpan(new UnderlineSpan(), 11, 31, 0);
        content.setSpan(new UnderlineSpan(), 37, 59, 0);

        content.setSpan(new ClickableSpan() {
            @Override
            public void onClick(@NonNull View view) {
                viewModel.updateTipo(0);
            }
        }, 11, 31, 0);

        content.setSpan(new ClickableSpan() {
            @Override
            public void onClick(@NonNull View view) {
                viewModel.updateTipo(1);
            }
        }, 37, 59, 0);

        content.setSpan(new ForegroundColorSpan(getResources().getColor(R.color.verde, null)), 11, 31, 0);
        content.setSpan(new ForegroundColorSpan(getResources().getColor(R.color.verde, null)), 37, 59, 0);

        TVIrATyC.setText(content);
    }

    private void validarEmail() {
        String fullName = ETFullName.getText().toString();
        String email = ETEmail.getText().toString();
        String password = ETPassword.getText().toString();
        String confirmPassword = ETConfirmPassword.getText().toString();

        viewModel.validarEmail(fullName, email, password, confirmPassword);
    }
}