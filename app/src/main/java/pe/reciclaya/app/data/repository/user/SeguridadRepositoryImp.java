package pe.reciclaya.app.data.repository.user;

import android.content.Context;

import androidx.annotation.NonNull;

import pe.reciclaya.app.data.local.AppPreferencesManager;
import pe.reciclaya.app.data.local.SaveUser;
import pe.reciclaya.app.data.model.user.request.seguridad.TwoFactorRequest;
import pe.reciclaya.app.data.model.user.response.TwoFactorResponse;
import pe.reciclaya.app.data.remote.BackendClient;
import pe.reciclaya.app.data.remote.UserService;
import pe.reciclaya.app.domain.model.user.User;
import pe.reciclaya.app.domain.repository.RepositoryCallback;
import pe.reciclaya.app.domain.repository.user.SeguridadRepository;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SeguridadRepositoryImp implements SeguridadRepository {
    private final UserService apiService;
    private final User user;

    public SeguridadRepositoryImp(Context context) {
        apiService = BackendClient.getUserService();
        user = User.getInstance();
        if(user.getID() <= 0) SaveUser.loadData(AppPreferencesManager.getInstance(context), user);
    }

    @Override
    public String getEmail() { return user.getEmail(); }

    @Override
    public void getTwoFactor(RepositoryCallback<Boolean> callback) {
        apiService.getTwoFactor(user.getID()).enqueue(new Callback<>() {
            @Override
            public void onResponse(@NonNull Call<TwoFactorResponse> call, @NonNull Response<TwoFactorResponse> response) {
                if(response.isSuccessful() && response.body() != null) callback.onSuccess(response.body().isEnabled());
                else callback.onError("Error en el servidor, vuelva a intentarlo más tarde.");
            }

            @Override
            public void onFailure(@NonNull Call<TwoFactorResponse> call, @NonNull Throwable t) {
                callback.onError("Error en la red, compruebe su conexión.");
            }
        });
    }

    @Override
    public void updateTwoFactor(boolean enabled, String password, RepositoryCallback<Boolean> callback) {
        TwoFactorRequest body = new TwoFactorRequest(enabled, password);

        apiService.updateTwoFactor(user.getID(), body).enqueue(new Callback<>() {
            @Override
            public void onResponse(@NonNull Call<TwoFactorResponse> call, @NonNull Response<TwoFactorResponse> response) {
                TwoFactorResponse twoFactorResponse = response.body();
                if(!response.isSuccessful() || twoFactorResponse == null) {
                    callback.onError("Error en el servidor, vuelva a intentarlo más tarde.");
                    return;
                }

                if(TwoFactorResponse.INVALID_PASSWORD.equals(twoFactorResponse.getResult()))
                    callback.onError("Contraseña incorrecta.");
                else callback.onSuccess(twoFactorResponse.isEnabled());
            }

            @Override
            public void onFailure(@NonNull Call<TwoFactorResponse> call, @NonNull Throwable t) {
                callback.onError("Error en la red, compruebe su conexión.");
            }
        });
    }
}
