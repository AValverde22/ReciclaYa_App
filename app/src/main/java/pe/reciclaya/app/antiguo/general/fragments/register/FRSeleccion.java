package pe.reciclaya.app.antiguo.general.fragments.register;

import static android.content.Context.MODE_PRIVATE;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import pe.reciclaya.app.R;
import pe.reciclaya.app.antiguo.general.adapters.RolRA;
import pe.reciclaya.app.data.remote.BackendClient;
import pe.reciclaya.app.antiguo.general.items.Rol;
import pe.reciclaya.app.data.model.login.LoginResponse;
import pe.reciclaya.app.data.model.register.RegisterRequestUser;
import pe.reciclaya.app.data.remote.UserService;
import pe.reciclaya.app.ui.util.Common;
import pe.reciclaya.app.antiguo.general.util.Singleton;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class FRSeleccion extends Fragment {

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private String mParam1;
    private String mParam2;

    public FRSeleccion() {}

    public static FRSeleccion newInstance(String param1, String param2) {
        FRSeleccion fragment = new FRSeleccion();
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
    private RolRA rolRA;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_register_select, container, false);

        //IVRegresarUnoAtras();
        inicializarRolRA();
        confirmar();

        return view;
    }

    /*
    @Override
    public void IVRegresarUnoAtras() {
        if(getActivity() == null) return;
        ImageView IVRegresarUnoAtras = getActivity().findViewById(R.id.IVRegresarRegister);
        IVRegresarUnoAtras.setOnClickListener(view1 -> {
            if(getActivity() instanceof RegisterActivity)
                ((RegisterActivity) getActivity()).eliminarFragment();
        });
    }

     */

    private void inicializarRolRA () {
        RecyclerView RVRol = view.findViewById(R.id.RVRolFRS);

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

        rolRA = new RolRA(roles);
        RVRol.setLayoutManager(new LinearLayoutManager(getContext()) {
            @Override
            public boolean canScrollVertically() {
                return false;
            }
        });
        RVRol.setAdapter(rolRA);
    }

    private void confirmar() {
        Button btnConfimar = view.findViewById(R.id.BtnConfirmarFRS);
        btnConfimar.setOnClickListener(view1 -> {
            String rol = rolRA.getRol();

            if(rol == null) {
                if(getContext() != null)
                    Common.toastMakeText(getContext(), "Debe de seleccionar un rol.");
                return;
            }

            String fullName = requireArguments().getString("fullName");
            String email = requireArguments().getString("email");
            String password = requireArguments().getString("password");

            UserService apiService = BackendClient.getUserService();
            RegisterRequestUser body = new RegisterRequestUser(fullName, email, password, rol);

            Singleton.getMostrarLLLoadingRegister().postValue(true);
            apiService.registerUser(body).enqueue(new Callback<>() {
                @Override
                public void onResponse(@NonNull Call<Integer> call, @NonNull Response<Integer> response) {
                    Singleton.getMostrarLLLoadingRegister().postValue(false);
                    if(response.isSuccessful() && response.body() != null) {
                        int id = response.body();

                        if(getContext() != null)
                            getContext().getSharedPreferences("INICIAL", MODE_PRIVATE).edit()
                                    .putInt("id", id)
                                    .putString("fullName", fullName)
                                    .putString("email", email)
                                    .putString("role", rol)
                                    .apply();

                        Singleton.getUserLogged().postValue(new LoginResponse(id, fullName, email, rol));
                        if(getContext() != null) Common.irAMain(getContext(), rol);
                        if(getActivity() != null) getActivity().finishAffinity();
                    } else {
                        if(getContext() != null)
                            Common.toastMakeText(getContext(), "Error en el servidor, vuelva a intentarlo más tarde.");
                    }
                }

                @Override
                public void onFailure(@NonNull Call<Integer> call, @NonNull Throwable t) {
                    Singleton.getMostrarLLLoadingRegister().postValue(false);
                    if(getContext() != null)
                        Common.toastMakeText(getContext(), "Error en la red, compruebe su conexión.");
                }
            });


        });
    }

}