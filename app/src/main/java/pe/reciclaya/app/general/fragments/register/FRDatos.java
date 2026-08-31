package pe.reciclaya.app.general.fragments.register;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

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
import pe.reciclaya.app.general.activities.LoginActivity;
import pe.reciclaya.app.general.activities.RegisterActivity;
import pe.reciclaya.app.general.activities.TyCActivity;
import pe.reciclaya.app.general.config.BackendClient;
import pe.reciclaya.app.general.requests.user.UserValidateEmail;
import pe.reciclaya.app.general.services.UserService;
import pe.reciclaya.app.general.util.Common;
import pe.reciclaya.app.general.util.Inicializaciones;
import pe.reciclaya.app.general.util.Singleton;
import pe.reciclaya.app.general.util.Validaciones;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class FRDatos extends Fragment implements Inicializaciones.IFragmentRegister {


    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private String mParam1;
    private String mParam2;

    public FRDatos() {}

    public static FRDatos newInstance(String param1, String param2) {
        FRDatos fragment = new FRDatos();
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

    private View view;

    private EditText ETFullName, ETEmail, ETPassword, ETConfirmPassword;
    private CheckBox checkBox;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_register_datos, container, false);

        ETFullName = view.findViewById(R.id.ETFullNameFRD);
        ETEmail = view.findViewById(R.id.ETEmailFRD);
        ETPassword = view.findViewById(R.id.ETPasswordFRD);
        ETConfirmPassword = view.findViewById(R.id.ETConfirmPasswordFRD);

        checkBox = view.findViewById(R.id.CBFRD);

        IVRegresarUnoAtras();

        Inicializaciones.campoGenerico(ETFullName);
        Inicializaciones.email(ETEmail);
        Inicializaciones.password(ETPassword, ETConfirmPassword);
        Inicializaciones.confirmPassword(ETPassword, ETConfirmPassword);

        ImageView IVOjo1 = view.findViewById(R.id.IVOjoUnoRegister);
        ImageView IVOjo2 = view.findViewById(R.id.IVOjoDosRegister);

        Common.mostrarPassword(ETPassword, IVOjo1);
        Common.mostrarPassword(ETConfirmPassword, IVOjo2);

        inicializarTVIrATyC();
        crearCuenta();
        inicializarTVIrALogin();

        return view;
    }

    @Override
    public void IVRegresarUnoAtras() {
        if(getActivity() == null) return;
        ImageView IVRegresarUnoAtras = getActivity().findViewById(R.id.IVRegresarRegister);
        IVRegresarUnoAtras.setOnClickListener(view1 ->
                startActivity(new Intent(getContext(), LoginActivity.class))
        );
    }

    private void inicializarTVIrATyC() {
        TextView TVIrATyC = view.findViewById(R.id.TVTyCFRD);
        TVIrATyC.setMovementMethod(LinkMovementMethod.getInstance());

        SpannableString content = new SpannableString("Acepto los Términos de Servicio y la Política de Privacidad.");
        content.setSpan(new UnderlineSpan(), 11, 31, 0);
        content.setSpan(new UnderlineSpan(), 37, 59, 0);

        content.setSpan(new ClickableSpan() {
            @Override
            public void onClick(@NonNull View view) {
                startActivity(new Intent(
                        getContext(),
                        TyCActivity.class
                ).putExtra("Tipo", 0));
            }
        }, 11, 31, 0);

        content.setSpan(new ClickableSpan() {
            @Override
            public void onClick(@NonNull View view) {
                startActivity(new Intent(
                        getContext(),
                        TyCActivity.class
                ).putExtra("Tipo", 1));
            }
        }, 37, 59, 0);

        content.setSpan(new ForegroundColorSpan(getResources().getColor(R.color.verde, null)), 11, 31, 0);
        content.setSpan(new ForegroundColorSpan(getResources().getColor(R.color.verde, null)), 37, 59, 0);

        TVIrATyC.setText(content);
    }

    private void crearCuenta() {
        Button btnCrearCuenta = view.findViewById(R.id.BtnCrearCuentaFRD);
        btnCrearCuenta.setOnClickListener(view1 -> {
            if(!checkBox.isChecked()) {
                if(getContext() != null)
                    Common.toastMakeText(getContext(), "Para seguir, debe de aceptar los términos y condiciones.");

                return;
            }

            if(!Validaciones.campoGenerico(ETFullName)
            || !Validaciones.email(ETEmail)
            || !Validaciones.password(ETPassword, ETConfirmPassword)[0]
            || !Validaciones.password(ETPassword, ETConfirmPassword)[1]
            || !Validaciones.confirmPassword(ETPassword, ETConfirmPassword)
            ) return;

            String fullName = ETFullName.getText().toString();
            String email = ETEmail.getText().toString();
            String password = ETPassword.getText().toString();

            UserService apiService = BackendClient.getUserService();
            UserValidateEmail body = new UserValidateEmail(email);

            Singleton.getMostrarLLLoadingRegister().postValue(true);
            apiService.validateEmail(body).enqueue(new Callback<>() {
                @Override
                public void onResponse(@NonNull Call<Boolean> call,@NonNull Response<Boolean> response) {
                    Singleton.getMostrarLLLoadingRegister().postValue(false);

                    if(response.isSuccessful() && response.body() != null) {
                        boolean existe = response.body();

                        if(existe) {
                            if(getContext() != null)
                                Common.toastMakeText(getContext(), "El correo ya se encuentra registrado.");

                            return;
                        }

                        Bundle bundle = new Bundle();
                        bundle.putString("fullName", fullName);
                        bundle.putString("email", email);
                        bundle.putString("password", password);

                        if(getActivity() instanceof RegisterActivity) {
                            ((RegisterActivity) getActivity())
                                    .cambiarFragment(
                                            new FRSeleccion(),
                                            bundle
                                    );
                        }
                    } else {
                        if(getContext() != null)
                            Common.toastMakeText(getContext(), "Error en el servidor, vuelva a intentar más tarde.");
                    }
                }

                @Override
                public void onFailure(@NonNull Call<Boolean> call,@NonNull Throwable t) {
                    Singleton.getMostrarLLLoadingRegister().postValue(false);
                    if(getContext() != null)
                        Common.toastMakeText(getContext(), "Error en la red, compruebe su conexión.");
                }
            });
        });
    }

    private void inicializarTVIrALogin() {
        TextView TVIrALogin = view.findViewById(R.id.TVIniciarSesionFRD);
        TVIrALogin.setOnClickListener(view1 ->
            startActivity(new Intent(getContext(), LoginActivity.class))
        );
    }
}