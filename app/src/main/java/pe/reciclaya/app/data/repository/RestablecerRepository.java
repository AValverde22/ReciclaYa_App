package pe.reciclaya.app.data.repository;

import android.content.Context;
import android.util.Log;

import androidx.annotation.NonNull;

import pe.reciclaya.app.data.local.AppPreferencesManager;
import pe.reciclaya.app.data.model.restablecer.request.RestablecerRequestCompare;
import pe.reciclaya.app.data.model.restablecer.request.RestablecerRequestRecover;
import pe.reciclaya.app.data.model.restablecer.request.RestablecerRequestReset;
import pe.reciclaya.app.data.model.restablecer.response.RestablecerResponse;
import pe.reciclaya.app.data.remote.BackendClient;
import pe.reciclaya.app.data.remote.UserService;
import pe.reciclaya.app.domain.User;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RestablecerRepository {
    private final UserService apiService;
    private static AppPreferencesManager appPreferencesManager;
    private static User user;

    public RestablecerRepository(Context context) {
        apiService = BackendClient.getUserService();

        appPreferencesManager = AppPreferencesManager.getInstance(context);
        user = User.getInstance();
    }

    public void enviarCodigo(String email, ResetCallback callback) {
        Log.i("xD", email);
        RestablecerRequestRecover body = new RestablecerRequestRecover(email);

        apiService.recoverUser(body).enqueue(new Callback<>() {
            @Override
            public void onResponse(@NonNull Call<Void> call, @NonNull Response<Void> response) {
                if(response.isSuccessful()) callback.onSuccess(null);
                else callback.onError("Error en el servidor, vuelva a intentarlo más tarde.");
            }

            @Override
            public void onFailure(@NonNull Call<Void> call, @NonNull Throwable t) {
                callback.onError("Error en la red, compruebe su conexión.");
            }
        });
    }

    public void compararCodigo(String email, int codigo, ResetCallback callback) {
        RestablecerRequestCompare body = new RestablecerRequestCompare(email, codigo);

        apiService.compareCode(body).enqueue(new Callback<>() {
            @Override
            public void onResponse(@NonNull Call<Boolean> call, @NonNull  Response<Boolean> response) {
                if(response.isSuccessful() && response.body() != null) {
                    boolean correcto = response.body();
                    if(correcto) callback.onSuccess(null);
                    else callback.onError("Código incorrecto");
                } else callback.onError("Error en el servidor, vuelva a intentarlo más tarde.");
            }

            @Override
            public void onFailure(@NonNull Call<Boolean> call, @NonNull Throwable t) {
                callback.onError("Error en la red, compruebe su conexión");
            }
        });
    }

    public void resetUser(String email, String password, ResetCallback callback) {
        RestablecerRequestReset body = new RestablecerRequestReset(email, password);

        apiService.resetUser(body).enqueue(new Callback<>() {
            @Override
            public void onResponse(@NonNull Call<RestablecerResponse> call, @NonNull  Response<RestablecerResponse> response) {
                if(response.isSuccessful() && response.body() != null) {
                    almacenarDatos(response.body());
                    callback.onSuccess(response.body().getRole());
                } else callback.onError("Error en el servidor, vuelva a intentarlo más tarde.");
            }

            @Override
            public void onFailure(@NonNull Call<RestablecerResponse> call, @NonNull Throwable t) {
                callback.onError("Error en la red, compruebe su conexión.");
            }
        });
    }

    public interface ResetCallback {
        void onSuccess(String role);
        void onError(String errorMessage);
    }

    private void almacenarDatos(RestablecerResponse response) {
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
