package pe.reciclaya.app.data.repository;

import android.content.Context;

import androidx.annotation.NonNull;

import pe.reciclaya.app.data.model.login.request.LoginRequest;
import pe.reciclaya.app.data.model.login.response.LoginResponse;
import pe.reciclaya.app.data.remote.BackendClient;
import pe.reciclaya.app.data.remote.UserService;
import pe.reciclaya.app.data.local.AppPreferencesManager;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginRepository {
    private final UserService apiService;
    private static AppPreferencesManager appPreferencesManager;

    public LoginRepository(Context context) {
        apiService = BackendClient.getUserService();
        appPreferencesManager = AppPreferencesManager.getInstance(context);
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
                        callback.onSuccess(loginResponse);
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
        void onSuccess(LoginResponse response);
        void onError(String errorMessage);
    }

    private void almacenarDatos(LoginResponse response) {
        appPreferencesManager.putInt("id", response.getID());
        appPreferencesManager.putString("fullName", response.getFullName());
        appPreferencesManager.putString("email", response.getEmail());
        appPreferencesManager.putString("role", response.getRole());
    }
}
