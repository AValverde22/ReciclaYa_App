package pe.reciclaya.app.data.repository.user;

import android.content.Context;

import androidx.annotation.NonNull;

import pe.reciclaya.app.data.local.AppPreferencesManager;
import pe.reciclaya.app.data.local.SaveUser;
import pe.reciclaya.app.data.model.user.response.UserResponse;
import pe.reciclaya.app.data.model.user.request.register.RegisterRequestEmail;
import pe.reciclaya.app.data.model.user.request.register.RegisterRequestUser;
import pe.reciclaya.app.data.remote.BackendClient;
import pe.reciclaya.app.data.remote.UserService;
import pe.reciclaya.app.domain.model.user.User;
import pe.reciclaya.app.domain.model.user.UserRole;
import pe.reciclaya.app.domain.repository.user.RegisterRepository;
import pe.reciclaya.app.domain.repository.RepositoryCallback;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RegisterRepositoryImp implements RegisterRepository {
    private final UserService apiService;
    private static AppPreferencesManager appPreferencesManager;
    private static User user;

    public RegisterRepositoryImp(Context context) {
        apiService = BackendClient.getUserService();
        appPreferencesManager = AppPreferencesManager.getInstance(context);
        user = User.getInstance();
    }

    @Override
    public void validateEmail(String email, RepositoryCallback<Void> callback) {
        RegisterRequestEmail body = new RegisterRequestEmail(email);

        apiService.validateEmail(body).enqueue(new Callback<>() {
            @Override
            public void onResponse(@NonNull Call<Boolean> call, @NonNull Response<Boolean> response) {
                if(response.isSuccessful() && response.body() != null) {
                    boolean existe = response.body();
                    if(!existe) callback.onSuccess(null);
                    else callback.onError("El correo ya se encuentra registrado.");
                } else callback.onError("Error en el servidor, vuelva a intentarlo más tarde.");
            }

            @Override
            public void onFailure(@NonNull Call<Boolean> call, @NonNull Throwable t) {
                callback.onError("Error en la red, compruebe su conexión.");
            }
        });
    }

    @Override
    public void registerUser(String fullName, String email, String password, UserRole role, RepositoryCallback<UserRole> callback) {
        RegisterRequestUser body = new RegisterRequestUser(fullName, email, password, role.getRole());

        apiService.registerUser(body).enqueue(new Callback<>() {
            @Override
            public void onResponse(@NonNull Call<Integer> call, @NonNull Response<Integer> response) {
                if(response.isSuccessful() && response.body() != null) {
                    int id = response.body();
                    SaveUser.saveData(
                            appPreferencesManager,
                            user,
                            new UserResponse(
                                id,
                                fullName,
                                email,
                                role.getRole(),
                                "",
                                5f
                            )
                    );
                    callback.onSuccess(role);
                } else callback.onError("Error en el servidor, vuelva a intentarlo más tarde.");
            }

            @Override
            public void onFailure(@NonNull Call<Integer> call, @NonNull Throwable t) {
                callback.onError("Error en la red, compruebe su conexión.");
            }
        });
    }
}
