package pe.reciclaya.app.data.repository;

import android.content.Context;

import androidx.annotation.NonNull;

import pe.reciclaya.app.data.model.login.request.LoginRequest;
import pe.reciclaya.app.data.model.login.response.LoginResponse;
import pe.reciclaya.app.data.remote.BackendClient;
import pe.reciclaya.app.data.remote.UserService;
import pe.reciclaya.app.data.local.AppPreferencesManager;
import pe.reciclaya.app.domain.singleton.User;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginRepository {
    private final UserService apiService;
    private static AppPreferencesManager appPreferencesManager;
    private static User user;

    public LoginRepository(Context context) {
        apiService = BackendClient.getUserService();

        appPreferencesManager = AppPreferencesManager.getInstance(context);
        user = User.getInstance();
    }

    public void iniciarSesion(String email, String password, LoginCallback callback) {
        LoginRequest body = new LoginRequest(email, password);

        apiService.loginUser(body).enqueue(new Callback<>() {
            @Override
            public void onResponse(@NonNull Call<LoginResponse> call, @NonNull Response<LoginResponse> response) {
                if(response.isSuccessful() && response.body() != null) {
                    LoginResponse loginResponse = response.body();

                    if(loginResponse.getID() == -1) callback.onError("Usuario y/o contraseñas inválidos.");
                    else {
                        almacenarDatos(loginResponse);
                        callback.onSuccess(loginResponse.getRole());
                    }
                }
                else callback.onError("Error en el servidor, vuelva a intentarlo más tarde.");
            }

            @Override
            public void onFailure(@NonNull Call<LoginResponse> call, @NonNull Throwable t) {
                callback.onError("Error en la red, compruebe su conexión.");
            }
        });

    }

    public interface LoginCallback {
        void onSuccess(String role);
        void onError(String errorMessage);
    }

    private void almacenarDatos(LoginResponse response) {
        int id = response.getID();
        String fullName = response.getFullName();
        String email = response.getEmail();
        String role = response.getRole();

        user.setID(id);
        user.setFullName(fullName);
        user.setEmail(email);
        user.setRole(role);

        appPreferencesManager.putInt("id", id);
        appPreferencesManager.putString("fullName", fullName);
        appPreferencesManager.putString("email", email);
        appPreferencesManager.putString("role", role);
    }
}
