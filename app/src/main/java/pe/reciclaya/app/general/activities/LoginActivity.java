package pe.reciclaya.app.general.activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import pe.reciclaya.app.general.models.User;
import pe.reciclaya.app.general.util.Common;
import pe.reciclaya.app.general.util.Inicializaciones;
import pe.reciclaya.app.general.util.Singleton;
import pe.reciclaya.app.general.util.Validaciones;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import pe.reciclaya.app.R;
import pe.reciclaya.app.general.config.BackendClient;
import pe.reciclaya.app.general.requests.user.UserLogin;
import pe.reciclaya.app.general.services.UserService;

public class LoginActivity extends AppCompatActivity {

    private EditText ETEmail, ETPassword;
    private LinearLayout LLLoading;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        ETEmail = findViewById(R.id.ETEmailLogin);
        ETPassword = findViewById(R.id.ETPasswordLogin);

        LLLoading = findViewById(R.id.LLLoadingLogin);

        ImageView IVOjo = findViewById(R.id.IVOjoLogin);
        Common.mostrarPassword(ETPassword, IVOjo);

        Inicializaciones.campoGenerico(ETEmail);
        Inicializaciones.campoGenerico(ETPassword);
    }

    public void irARegistro(View view) { startActivity(new Intent(this, RegisterActivity.class)); }

    public void iniciarSesion(View view) {
        if(!Validaciones.campoGenerico(ETEmail) || !Validaciones.campoGenerico(ETPassword)) return;

        String email = ETEmail.getText().toString();
        String password = ETPassword.getText().toString();

        UserService apiService = BackendClient.getUserService();
        UserLogin body = new UserLogin(email, password);

        LLLoading.setVisibility(View.VISIBLE);

        apiService.loginUser(body).enqueue(new Callback<>() {
            @Override
            public void onResponse(@NonNull Call<User> call, @NonNull Response<User> response) {
                LLLoading.setVisibility(View.GONE);

                if(response.isSuccessful() && response.body() != null) {
                    User user = response.body();
                    int id = user.getID();

                    if(id == -1) Common.toastMakeText(LoginActivity.this, "Usuario y/o contraseña inválidos.");
                    else {
                        String fullName = user.getFullName();
                        String email = user.getEmail();
                        String role = user.getRole();

                        getSharedPreferences("INICIAL", MODE_PRIVATE).edit()
                                .putInt("id", id)
                                .putString("fullName", fullName)
                                .putString("email", email)
                                .putString("role", role)
                                .apply();

                        Singleton.getUserLogged().postValue(user);
                        Common.irAMain(LoginActivity.this, role);
                        finishAffinity();
                    }
                } else Common.toastMakeText(LoginActivity.this, "Error en el servidor, vuelva a intentarlo más tarde.");
            }

            @Override
            public void onFailure(@NonNull Call<User> call, @NonNull Throwable t) {
                LLLoading.setVisibility(View.GONE);
                Common.toastMakeText(LoginActivity.this, "Error en la red, compruebe su conexión.");
            }
        });
    }
}