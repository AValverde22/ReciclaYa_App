package pe.reciclaya.app.data.remote;

import pe.reciclaya.app.data.model.login.LoginResponse;
import pe.reciclaya.app.antiguo.general.requests.user.UserValidateEmail;
import pe.reciclaya.app.antiguo.general.requests.user.UserRegister;
import pe.reciclaya.app.data.model.login.LoginRequest;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface UserService {

    @POST("user/validate")
    Call<Boolean> validateEmail(@Body UserValidateEmail requestModel);

    @POST("user/register")
    Call<Integer> registerUser(@Body UserRegister requestModel);

    @POST("user/login")
    Call<LoginResponse> loginUser(@Body LoginRequest requestModel);
}
