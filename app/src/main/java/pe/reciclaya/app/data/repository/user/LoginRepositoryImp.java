package pe.reciclaya.app.data.repository.user;

import android.content.Context;

import androidx.annotation.NonNull;

import pe.reciclaya.app.data.local.SaveUser;
import pe.reciclaya.app.data.model.user.request.login.LoginRequest;
import pe.reciclaya.app.data.model.user.response.UserResponse;
import pe.reciclaya.app.data.remote.BackendClient;
import pe.reciclaya.app.data.remote.UserService;
import pe.reciclaya.app.data.local.AppPreferencesManager;
import pe.reciclaya.app.domain.model.user.User;
import pe.reciclaya.app.domain.model.user.UserRole;
import pe.reciclaya.app.domain.repository.user.LoginRepository;
import pe.reciclaya.app.domain.repository.user.RepositoryCallback;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginRepositoryImp implements LoginRepository {
    private static UserService apiService;
    private static AppPreferencesManager appPreferencesManager;
    private static User user;

    public LoginRepositoryImp(Context context) {
        apiService = BackendClient.getUserService();
        appPreferencesManager = AppPreferencesManager.getInstance(context);
        user = User.getInstance();
    }

    @Override
    public void login(String email, String password, RepositoryCallback<UserRole> callback) {
        LoginRequest body = new LoginRequest(email, password);

        apiService.loginUser(body).enqueue(new Callback<>() {
            @Override
            public void onResponse(@NonNull Call<UserResponse> call, @NonNull Response<UserResponse> response) {
                if(response.isSuccessful() && response.body() != null) {
                    UserResponse userResponse = response.body();

                    if(userResponse.getID() == -1) callback.onError("Usuario y/o contraseñas inválidos.");
                    else {
                        SaveUser.saveData(appPreferencesManager, user, userResponse);
                        callback.onSuccess(UserRole.fromString(userResponse.getRole()));
                    }
                }
                else callback.onError("Error en el servidor, vuelva a intentarlo más tarde.");
            }

            @Override
            public void onFailure(@NonNull Call<UserResponse> call, @NonNull Throwable t) {
                callback.onError("Error en la red, compruebe su conexión.");
            }
        });
    }
}
