package pe.reciclaya.app.data.remote;

import pe.reciclaya.app.data.model.login.LoginResponse;
import pe.reciclaya.app.data.model.register.RegisterRequestEmail;
import pe.reciclaya.app.data.model.register.RegisterRequestUser;
import pe.reciclaya.app.data.model.login.LoginRequest;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface UserService {

    @POST("user/validate")
    Call<Boolean> validateEmail(@Body RegisterRequestEmail requestModel);

    @POST("user/register")
    Call<Integer> registerUser(@Body RegisterRequestUser requestModel);

    @POST("user/login")
    Call<LoginResponse> loginUser(@Body LoginRequest requestModel);
}
