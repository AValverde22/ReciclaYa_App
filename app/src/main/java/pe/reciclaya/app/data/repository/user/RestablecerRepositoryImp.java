package pe.reciclaya.app.data.repository.user;

import android.content.Context;

import androidx.annotation.NonNull;

import pe.reciclaya.app.data.local.AppPreferencesManager;
import pe.reciclaya.app.data.local.SaveUser;
import pe.reciclaya.app.data.model.user.response.UserResponse;
import pe.reciclaya.app.data.model.user.request.restablecer.RestablecerRequestCompare;
import pe.reciclaya.app.data.model.user.request.restablecer.RestablecerRequestRecover;
import pe.reciclaya.app.data.model.user.request.restablecer.RestablecerRequestReset;
import pe.reciclaya.app.data.remote.BackendClient;
import pe.reciclaya.app.data.remote.UserService;
import pe.reciclaya.app.domain.model.user.User;
import pe.reciclaya.app.domain.model.user.UserRole;
import pe.reciclaya.app.domain.repository.RepositoryCallback;
import pe.reciclaya.app.domain.repository.user.RestablecerRepository;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RestablecerRepositoryImp implements RestablecerRepository {
    private final UserService apiService;
    private static AppPreferencesManager appPreferencesManager;
    private static User user;

    public RestablecerRepositoryImp(Context context) {
        apiService = BackendClient.getUserService();
        appPreferencesManager = AppPreferencesManager.getInstance(context);
        user = User.getInstance();
    }

    @Override
    public void sendEmail(String email, RepositoryCallback<Void> callback) {
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

    @Override
    public void compareCode(String email, int codigo, RepositoryCallback<Void> callback) {
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

    @Override
    public void resetUser(String email, String password, RepositoryCallback<UserRole> callback) {
        RestablecerRequestReset body = new RestablecerRequestReset(email, password);

        apiService.resetUser(body).enqueue(new Callback<>() {
            @Override
            public void onResponse(@NonNull Call<UserResponse> call, @NonNull  Response<UserResponse> response) {
                if(response.isSuccessful() && response.body() != null) {
                    SaveUser.saveData(appPreferencesManager, user, response.body());
                    callback.onSuccess(UserRole.fromString(response.body().getRole()));
                } else callback.onError("Error en el servidor, vuelva a intentarlo más tarde.");
            }

            @Override
            public void onFailure(@NonNull Call<UserResponse> call, @NonNull Throwable t) {
                callback.onError("Error en la red, compruebe su conexión.");
            }
        });
    }
}
