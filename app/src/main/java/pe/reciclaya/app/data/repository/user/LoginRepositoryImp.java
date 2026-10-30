package pe.reciclaya.app.data.repository.user;

import android.content.Context;

import androidx.annotation.NonNull;

import pe.reciclaya.app.data.local.SaveUser;
import pe.reciclaya.app.data.model.user.request.login.LoginRequest;
import pe.reciclaya.app.data.model.user.request.login.LoginResendRequest;
import pe.reciclaya.app.data.model.user.request.login.LoginVerifyRequest;
import pe.reciclaya.app.data.model.user.response.LoginResendResponse;
import pe.reciclaya.app.data.model.user.response.LoginVerifyResponse;
import pe.reciclaya.app.data.model.user.response.UserResponse;
import pe.reciclaya.app.data.remote.BackendClient;
import pe.reciclaya.app.data.remote.UserService;
import pe.reciclaya.app.data.local.AppPreferencesManager;
import pe.reciclaya.app.domain.model.user.LoginResult;
import pe.reciclaya.app.domain.model.user.User;
import pe.reciclaya.app.domain.model.user.UserRole;
import pe.reciclaya.app.domain.repository.user.LoginRepository;
import pe.reciclaya.app.domain.repository.RepositoryCallback;
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
    public void login(String email, String password, RepositoryCallback<LoginResult> callback) {
        LoginRequest body = new LoginRequest(email, password);

        apiService.loginUser(body).enqueue(new Callback<>() {
            @Override
            public void onResponse(@NonNull Call<UserResponse> call, @NonNull Response<UserResponse> response) {
                if(response.isSuccessful() && response.body() != null) {
                    UserResponse userResponse = response.body();

                    if(userResponse.requiresTwoFactor()) {
                        if(userResponse.isCodeSent() && userResponse.getChallenge() != null)
                            callback.onSuccess(LoginResult.codigoRequerido(email, userResponse.getChallenge()));
                        else callback.onError("No se pudo enviar el código de verificación. Inténtelo más tarde.");
                    }
                    else if(userResponse.getID() == -1) callback.onError("Usuario y/o contraseñas inválidos.");
                    else {
                        SaveUser.saveData(appPreferencesManager, user, userResponse);
                        callback.onSuccess(LoginResult.accesoConcedido(UserRole.fromString(userResponse.getRole())));
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

    @Override
    public void verifyCode(String email, String challenge, String code, RepositoryCallback<UserRole> callback) {
        LoginVerifyRequest body = new LoginVerifyRequest(email, challenge, code);

        apiService.verifyLogin(body).enqueue(new Callback<>() {
            @Override
            public void onResponse(@NonNull Call<LoginVerifyResponse> call, @NonNull Response<LoginVerifyResponse> response) {
                LoginVerifyResponse verifyResponse = response.body();
                if(!response.isSuccessful() || verifyResponse == null || verifyResponse.getResult() == null) {
                    callback.onError("Error en el servidor, vuelva a intentarlo más tarde.");
                    return;
                }

                switch(verifyResponse.getResult()) {
                    case LoginVerifyResponse.VALID:
                        UserResponse userResponse = verifyResponse.getUser();
                        if(userResponse == null) {
                            callback.onError("Error en el servidor, vuelva a intentarlo más tarde.");
                            return;
                        }
                        SaveUser.saveData(appPreferencesManager, user, userResponse);
                        callback.onSuccess(UserRole.fromString(userResponse.getRole()));
                        break;
                    case LoginVerifyResponse.INVALID:
                        callback.onError("Código inválido");
                        break;
                    case LoginVerifyResponse.EXPIRED:
                        callback.onError("Código expirado");
                        break;
                    case LoginVerifyResponse.BLOCKED:
                        callback.onError("Demasiados intentos. Solicite un nuevo código.");
                        break;
                    default:
                        callback.onError("La verificación venció. Inicie sesión nuevamente.");
                }
            }

            @Override
            public void onFailure(@NonNull Call<LoginVerifyResponse> call, @NonNull Throwable t) {
                callback.onError("Error en la red, compruebe su conexión.");
            }
        });
    }

    @Override
    public void resendCode(String email, String challenge, RepositoryCallback<Void> callback) {
        LoginResendRequest body = new LoginResendRequest(email, challenge);

        apiService.resendLoginCode(body).enqueue(new Callback<>() {
            @Override
            public void onResponse(@NonNull Call<LoginResendResponse> call, @NonNull Response<LoginResendResponse> response) {
                LoginResendResponse resendResponse = response.body();
                if(!response.isSuccessful() || resendResponse == null || resendResponse.getResult() == null) {
                    callback.onError("Error en el servidor, vuelva a intentarlo más tarde.");
                    return;
                }

                switch(resendResponse.getResult()) {
                    case LoginResendResponse.SENT:
                        callback.onSuccess(null);
                        break;
                    case LoginResendResponse.COOLDOWN:
                        callback.onError("Espere " + resendResponse.getRetryAfter() + " segundos para solicitar otro código.");
                        break;
                    case LoginResendResponse.EMAIL_ERROR:
                        callback.onError("No se pudo enviar el código. Inténtelo más tarde.");
                        break;
                    default:
                        callback.onError("La verificación venció. Inicie sesión nuevamente.");
                }
            }

            @Override
            public void onFailure(@NonNull Call<LoginResendResponse> call, @NonNull Throwable t) {
                callback.onError("Error en la red, compruebe su conexión.");
            }
        });
    }
}
