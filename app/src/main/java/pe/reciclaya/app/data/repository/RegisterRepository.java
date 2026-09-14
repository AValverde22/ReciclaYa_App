package pe.reciclaya.app.data.repository;

import android.content.Context;

import androidx.annotation.NonNull;

import pe.reciclaya.app.data.local.AppPreferencesManager;
import pe.reciclaya.app.data.model.register.RegisterRequestEmail;
import pe.reciclaya.app.data.model.register.RegisterRequestUser;
import pe.reciclaya.app.data.remote.BackendClient;
import pe.reciclaya.app.data.remote.UserService;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RegisterRepository {
    private final UserService apiService;
    private final Context context;

    public RegisterRepository(Context context) {
        apiService = BackendClient.getUserService();
        this.context = context;
    }

    public void validarEmail(String email, RegisterCallback callback) {
        RegisterRequestEmail body = new RegisterRequestEmail(email);

        apiService.validateEmail(body).enqueue(new Callback<>() {
            @Override
            public void onResponse(@NonNull Call<Boolean> call, @NonNull Response<Boolean> response) {
                if(response.isSuccessful() && response.body() != null) {
                    boolean existe = response.body();
                    if(!existe) callback.onSuccess();
                    else callback.onError("El correo ya se encuentra registrado.");
                } else callback.onError("Error en el servidor, vuelva a intentarlo más tarde.");
            }

            @Override
            public void onFailure(@NonNull Call<Boolean> call, @NonNull Throwable t) {
                callback.onError("Error en la red, compruebe su conexión.");
            }
        });
    }

    public void registerUser(String fullName, String email, String password, String role, RegisterCallback callback) {
        RegisterRequestUser body = new RegisterRequestUser(fullName, email, password, role);

        apiService.registerUser(body).enqueue(new Callback<>() {
            @Override
            public void onResponse(@NonNull Call<Integer> call, @NonNull Response<Integer> response) {
                if(response.isSuccessful() && response.body() != null) {
                    int id = response.body();
                    almacenarDatos(id, fullName, email, role);
                    callback.onSuccess();
                } else callback.onError("Error en el servidor, vuelva a intentarlo más tarde.");
            }

            @Override
            public void onFailure(@NonNull Call<Integer> call, @NonNull Throwable t) {
                callback.onError("Error en la red, compruebe su conexión.");
            }
        });
    }

    public interface RegisterCallback {
        void onSuccess();
        void onError(String errorMessage);
    }

    private void almacenarDatos(int id, String fullName, String email, String role) {
        AppPreferencesManager.putInt("id", id, context);
        AppPreferencesManager.putString("fullName", fullName, context);
        AppPreferencesManager.putString("email", email, context);
        AppPreferencesManager.putString("role", role, context);
    }
}
