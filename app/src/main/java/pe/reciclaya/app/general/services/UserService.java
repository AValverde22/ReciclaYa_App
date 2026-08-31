package pe.reciclaya.app.general.services;

import pe.reciclaya.app.general.models.User;
import pe.reciclaya.app.general.requests.user.UserValidateEmail;
import pe.reciclaya.app.general.requests.user.UserRegister;
import pe.reciclaya.app.general.requests.user.UserLogin;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface UserService {

    @POST("user/validate")
    Call<Boolean> validateEmail(@Body UserValidateEmail requestModel);

    @POST("user/register")
    Call<Integer> registerUser(@Body UserRegister requestModel);

    @POST("user/login")
    Call<User> loginUser(@Body UserLogin requestModel);
}
