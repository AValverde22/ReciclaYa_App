package pe.reciclaya.app.data.remote;

import pe.reciclaya.app.data.model.user.response.UserResponse;
import pe.reciclaya.app.data.model.user.request.register.RegisterRequestEmail;
import pe.reciclaya.app.data.model.user.request.register.RegisterRequestUser;
import pe.reciclaya.app.data.model.user.request.login.LoginRequest;
import pe.reciclaya.app.data.model.user.request.restablecer.RestablecerRequestCompare;
import pe.reciclaya.app.data.model.user.request.restablecer.RestablecerRequestRecover;
import pe.reciclaya.app.data.model.user.request.restablecer.RestablecerRequestReset;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.PATCH;
import retrofit2.http.POST;

public interface UserService {

    @POST("user/validate")
    Call<Boolean> validateEmail(@Body RegisterRequestEmail requestModel);

    @POST("user/register")
    Call<Integer> registerUser(@Body RegisterRequestUser requestModel);

    @POST("user/login")
    Call<UserResponse> loginUser(@Body LoginRequest requestModel);

    @POST("user/recover")
    Call<Void> recoverUser(@Body RestablecerRequestRecover requestModel);

    @POST("user/compare")
    Call<Boolean> compareCode(@Body RestablecerRequestCompare requestModel);

    @PATCH("user/reset")
    Call<UserResponse> resetUser(@Body RestablecerRequestReset requestModel);
}
